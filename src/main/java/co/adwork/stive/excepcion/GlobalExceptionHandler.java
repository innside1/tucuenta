package co.adwork.stive.excepcion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import co.adwork.stive.constante.CodigoError;
import co.adwork.stive.rest.respuesta.dto.DatoDefault;
import co.adwork.stive.rest.respuesta.dto.Error;
import co.adwork.stive.rest.respuesta.dto.Respuesta;
import co.adwork.stive.util.MensajeResource;

/**
 * Clase general de manejo de errores
 * @author juan.hoyos
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @Autowired
    private MensajeResource mensajeResource;

    /**
     * Genera una respuesta para los errores de validacion
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Respuesta<Serializable>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        
        BindingResult bindingResult = e.getBindingResult();
        List<ValidationError> validationErrors = bindingResult.getFieldErrors().stream().map(ValidationError::new).collect(Collectors.toList());       
        return crearRespuestaError(validationErrors);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Respuesta<Serializable>> handleConstraintViolationException(ConstraintViolationException constraintViolationException){
        
        List<ValidationError> validationErrors = constraintViolationException.getConstraintViolations().stream().map(ValidationError::new).collect(Collectors.toList());
        return crearRespuestaError(validationErrors);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<List<ValidationError>> handleMultipartException(MultipartException multipartException){
        
        List<ValidationError> validationErrors = new ArrayList<>();        
        ValidationError validationError = new ValidationError("Error upload file", multipartException.getMessage(), multipartException.getCause().getMessage());
        validationErrors.add(validationError);        
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<List<ValidationError>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException uploadSizeExceededException){
        
        List<ValidationError> validationErrors = new ArrayList<>(); 
        ValidationError validationError = new ValidationError();
        validationError.setObject(uploadSizeExceededException.getClass().getSimpleName());
        validationError.setReason("File exceded " + uploadSizeExceededException.getMaxUploadSize() +" MB");
        validationError.setRejectedValue(uploadSizeExceededException.getMostSpecificCause().getMessage());
        validationErrors.add(validationError);
        
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
        
    }
    
    /**
     * Genera una excepcion cuando valida el metodo HTTP que est&aacute; permitido
     * y se customiza el mensaje de respuesta.
     * @param httpRequestMethodNotSupportedException
     * @return @see {@link ValidationError}
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ValidationError> request405(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        
        ValidationError validationError = new ValidationError();
        validationError.setObject(httpRequestMethodNotSupportedException.getMethod());
        validationError.setReason(mensajeResource.get("api.mensaje.globalexceptionhandler.metodo.no.soportado"));
        validationError.setRejectedValue(httpRequestMethodNotSupportedException.getMethod());        
        
        return new ResponseEntity<>(validationError, HttpStatus.METHOD_NOT_ALLOWED);
       
    }
    
    /**
     * Genera una excepcion cuando el mensaje enviado en body
     * no se encuentra bien formando y se customiza el mensaje de respuesta.
     * @return @see {@link ValidationError}
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Respuesta<Serializable>> exceptionMensajeNoLeible(HttpMessageNotReadableException httpMessageNotReadableException){
        LOGGER.info("error {}", httpMessageNotReadableException);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Respuesta.builder()
                        .datos(new DatoDefault())
                        .error(Error.builder()
                                .codigo(CodigoError.CODIGO_GENERAL_JSON_MAL_FORMADO)
                                .mensaje(mensajeResource.get("api.mensaje.globalexceptionhandler.mensaje.no.leible"))
                                .build())
                        .build());
    }
        
    /**
     * Genera una excepcion al validar el content type del contrato del endpoint 
     * y se customiza el mensaje de respuesta. 
     * @param httpMediaTypeNotSupportedException
     * @return @see {@link ValidationError}
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<List<ValidationError>> exceptionMediaTypeNoSoportado(HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException) {
              
        List<ValidationError> validationErrors = new ArrayList<>();
        ValidationError validationError = new ValidationError();
        
		validationError.setObject(httpMediaTypeNotSupportedException.getContentType() == null ? "content-type null"
				: httpMediaTypeNotSupportedException.getContentType().toString());
        validationError.setReason(mensajeResource.get("api.mensaje.globalexceptionhandler.mediatype.no.soportado"));
        validationError.setRejectedValue(httpMediaTypeNotSupportedException.getContentType() == null ? "content-type null"
				: httpMediaTypeNotSupportedException.getContentType().toString());        
        validationErrors.add(validationError);
        LOGGER.warn(httpMediaTypeNotSupportedException.toString());
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Respuesta<Serializable>> crearRespuestaError(List<ValidationError> validationErrors) {
        StringBuilder builder = new StringBuilder();
        validationErrors.stream()
                .findFirst().ifPresent(validationError -> {
                    builder.append(validationError.getObject());
                    builder.append(": ");
                    builder.append(validationError.getReason());
        });
        return ResponseEntity
                .status(HttpStatus.PARTIAL_CONTENT)
                .body(Respuesta.builder()
                        .error(Error.builder()
                                .codigo(99000)
                                .mensaje(builder.toString())
                                .build())
                        .build());
    }
}
