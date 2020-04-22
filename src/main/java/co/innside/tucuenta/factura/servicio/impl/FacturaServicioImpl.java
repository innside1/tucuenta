package co.innside.tucuenta.factura.servicio.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.innside.tucuenta.factura.servicio.FacturaServicio;

/**
 * Capa de operaciones de negocio
 * @author juan.hoyos
 *
 */
@Service
public class FacturaServicioImpl implements FacturaServicio {

	@Override
	public String procesarFactura(MultipartFile file) {
		return "";
	}

}
