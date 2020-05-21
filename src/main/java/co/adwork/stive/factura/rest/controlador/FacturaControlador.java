package co.adwork.stive.factura.rest.controlador;

import java.io.Serializable;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.adwork.stive.constante.ApiMensajeHttp;
import co.adwork.stive.excepcion.ComunicacionFallidaException;
import co.adwork.stive.excepcion.InformacionNoEncontradaException;
import co.adwork.stive.factura.negocio.servicio.FacturaServicio;
import co.adwork.stive.factura.rest.dto.ConsultarFacturaSolicitud;
import co.adwork.stive.rest.respuesta.dto.Error;
import co.adwork.stive.rest.respuesta.dto.Respuesta;
import co.adwork.stive.util.UtilControlador;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * API REST de servicios de factura
 * @author juan.hoyos
 *
 */
@Slf4j
@Api(value = "factura", tags = "factura")
@RestController
public class FacturaControlador {
	
		private final FacturaServicio facturaServicio;
		private final UtilControlador utilController;

		public FacturaControlador(FacturaServicio facturaServicio, UtilControlador utilController) {
			super();
			this.facturaServicio = facturaServicio;
			this.utilController = utilController;
		}
		
		@ApiOperation(value = "Procesar factura", notes = "Procesar factura", nickname = "procesarfactura", 
	    		produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	    @ApiResponses({ @ApiResponse(code = 201, message = ApiMensajeHttp.API_HTTP_STATUS_200, response = Respuesta.class),
	                    @ApiResponse(code = 502, message = ApiMensajeHttp.API_HTTP_STATUS_502, response = Respuesta.class),
	             	    @ApiResponse(code = 206, message = ApiMensajeHttp.API_HTTP_STATUS_206, response = Respuesta.class) })
	    @PostMapping(path = "${endpoint.stive.v1.factura.procesar.path}", 
	    		produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
	    		consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public ResponseEntity<Respuesta<String>> procesarFactura(
	            @ApiParam(required = true, name = "archivo", value = "archivo")
	            @RequestParam(name = "archivo", required = true) MultipartFile archivo) {     
	    	return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>(facturaServicio.procesarFactura(archivo), new Error()));
	    }
		
		@ApiOperation(value = "Consultar factura", notes = "Consultar factura", nickname = "consultarfactura", 
	    		produces = MediaType.APPLICATION_JSON_VALUE,
	    		consumes = MediaType.APPLICATION_JSON_VALUE)
	    @ApiResponses({ @ApiResponse(code = 201, message = ApiMensajeHttp.API_HTTP_STATUS_200, response = Respuesta.class),
	                    @ApiResponse(code = 502, message = ApiMensajeHttp.API_HTTP_STATUS_502, response = Respuesta.class),
	             	    @ApiResponse(code = 206, message = ApiMensajeHttp.API_HTTP_STATUS_206, response = Respuesta.class) })
	    @PostMapping(path = "${endpoint.stive.v1.factura.consultar.path}", 
	    		produces = MediaType.APPLICATION_JSON_VALUE,
	    		consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Respuesta<Serializable>> consultarFacturaExito(
				 @Valid @RequestBody ConsultarFacturaSolicitud consultarFacturaSolicitud) {     
	    	return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>(facturaServicio.consultarFactura(consultarFacturaSolicitud), new Error()));
	    }
	
	    @ExceptionHandler(ComunicacionFallidaException.class)
	    public ResponseEntity<Respuesta<Serializable>> handlerException(ComunicacionFallidaException e) {
	        return utilController.respuestaExcepcion(e, HttpStatus.BAD_GATEWAY,log);
	    }
	    
	    @ExceptionHandler(InformacionNoEncontradaException.class)
	    public ResponseEntity<Respuesta<Serializable>> handlerException(InformacionNoEncontradaException e) {
	        return utilController.respuestaExcepcion(e, HttpStatus.PARTIAL_CONTENT,log);
	    }
}
