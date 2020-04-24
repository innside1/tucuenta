package co.innside.tucuenta.factura.negocio.servicio;

import org.springframework.web.multipart.MultipartFile;

public interface FacturaServicio {
	
	/**
	 * Servicio para lectura optica, almacenamiento y categorizacion de datos de factura
	 * @param file
	 * @return
	 */
	String procesarFactura(MultipartFile file);

}
