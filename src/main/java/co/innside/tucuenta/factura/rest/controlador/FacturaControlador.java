package co.innside.tucuenta.factura.rest.controlador;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.innside.tucuenta.constante.ApiMensajeHttp;
import co.innside.tucuenta.excepcion.ComunicacionFallidaException;
import co.innside.tucuenta.factura.servicio.FacturaServicio;
import co.innside.tucuenta.rest.respuesta.dto.Error;
import co.innside.tucuenta.rest.respuesta.dto.Respuesta;
import co.innside.tucuenta.util.UtilControlador;
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
		
		@ApiOperation(value = "Enviar selfie a TOC", notes = "Enviar selfie a TOC", nickname = "enviarselfietoc", 
	    		produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	    @ApiResponses({ @ApiResponse(code = 201, message = ApiMensajeHttp.API_HTTP_STATUS_200, response = Respuesta.class),
	                    @ApiResponse(code = 502, message = ApiMensajeHttp.API_HTTP_STATUS_502, response = Respuesta.class),
	             	    @ApiResponse(code = 206, message = ApiMensajeHttp.API_HTTP_STATUS_206, response = Respuesta.class) })
	    @PostMapping(path = "${endpoint.tucuenta.v1.factura.procesar.path}", 
	    		produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
	    		consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public ResponseEntity<Respuesta<String>> procesarFactura(
	            @ApiParam(required = true, name = "archivo", value = "archivo")
	            @RequestParam(name = "archivo", required = true) MultipartFile archivo) {     
	    	return ResponseEntity.status(HttpStatus.OK).body(new Respuesta<>(facturaServicio.procesarFactura(archivo), new Error()));
	    }
	
	    @ExceptionHandler(ComunicacionFallidaException.class)
	    public ResponseEntity<Respuesta<Serializable>> handlerException(ComunicacionFallidaException e) {
	        return utilController.respuestaExcepcion(e, HttpStatus.BAD_GATEWAY,log);
	    }
}
