package co.adwork.stive.factura.negocio.servicio;

import org.springframework.web.multipart.MultipartFile;

import co.adwork.stive.factura.rest.dto.ConsultarFacturaRespuesta;
import co.adwork.stive.factura.rest.dto.ConsultarFacturaSolicitud;

public interface FacturaServicio {
	
	/**
	 * Servicio para lectura optica, almacenamiento y categorizacion de datos de factura
	 * @param file
	 * @return
	 */
	String procesarFactura(MultipartFile file);
	
	/**
	 * Consulta una factura
	 * @param consultarFacturaSolicitud
	 * @return
	 */
	ConsultarFacturaRespuesta consultarFactura(ConsultarFacturaSolicitud consultarFacturaSolicitud);

}
