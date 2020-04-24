package co.innside.tucuenta.factura.negocio.servicio.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.innside.tucuenta.factura.datasource.servicio.DatasourceFacturaServicio;
import co.innside.tucuenta.factura.negocio.servicio.FacturaServicio;
import co.innside.tucuenta.modelo.Cliente;
import co.innside.tucuenta.modelo.Comercio;
import co.innside.tucuenta.modelo.Factura;
import co.innside.tucuenta.modelo.Producto;

/**
 * Capa de operaciones de negocio
 * @author juan.hoyos
 *
 */
@Service
public class FacturaServicioImpl implements FacturaServicio {
	
	private final DatasourceFacturaServicio datasourceFacturaServicio;
	
	public FacturaServicioImpl(DatasourceFacturaServicio datasourceFacturaServicio) {
		super();
		this.datasourceFacturaServicio = datasourceFacturaServicio;
	}

	@Override
	public String procesarFactura(MultipartFile file) {
		Factura factura = new Factura();
		
		factura.setTotal(235.21);
		factura.setFechaCompra(LocalDateTime.now());
		factura.setUrl("http://localhost:8100/swagger-ui.html#/");
		
		Comercio comercio = new Comercio();
		comercio.setBarrio("Los Libertadores");
		comercio.setDireccion("Cr 65 N 12 - 65");
		comercio.setNit("2354862-5");
		comercio.setNombre("Abarrotes la 80");
		comercio.setTelefono("3125474");
		factura.setComercio(comercio);
		
		Cliente cliente = new Cliente();
		cliente.setCorreo("jhoyos@innside.co");
		factura.setCliente(cliente);
		
		List<Producto> productos = new ArrayList<>();
		Producto producto1 = new Producto();
		producto1.setCantidad(5);
		producto1.setPrecio(12500.0);
		producto1.setNombre("Jabon");
		producto1.setFactura(factura);
		
		Producto producto2 = new Producto();
		producto2.setCantidad(3);
		producto2.setPrecio(7200.0);
		producto2.setNombre("Papel Higienico");
		producto2.setFactura(factura);
		
		productos.add(producto1);
		productos.add(producto2);
		
		factura.setProductos(productos);
		
		datasourceFacturaServicio.almacenarCompra(factura);
		return "";
	}

}
