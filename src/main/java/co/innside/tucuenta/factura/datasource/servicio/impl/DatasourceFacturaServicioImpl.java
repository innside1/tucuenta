package co.innside.tucuenta.factura.datasource.servicio.impl;

import org.springframework.stereotype.Service;

import co.innside.tucuenta.datasource.repositorio.ClienteRepositorio;
import co.innside.tucuenta.datasource.repositorio.ComercioRepositorio;
import co.innside.tucuenta.datasource.repositorio.FacturaRepositorio;
import co.innside.tucuenta.datasource.repositorio.ProductoRepositorio;
import co.innside.tucuenta.factura.datasource.servicio.DatasourceFacturaServicio;
import co.innside.tucuenta.modelo.Factura;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatasourceFacturaServicioImpl implements DatasourceFacturaServicio {
	
	private final FacturaRepositorio facturaRepositorio;
	private final ClienteRepositorio clienteRepositorio;
	private final ComercioRepositorio comercioRepositorio;
	private final ProductoRepositorio productoRepositorio;

	public DatasourceFacturaServicioImpl(FacturaRepositorio facturaRepositorio, ClienteRepositorio clienteRepositorio,
			ComercioRepositorio comercioRepositorio, ProductoRepositorio productoRepositorio) {
		super();
		this.facturaRepositorio = facturaRepositorio;
		this.clienteRepositorio = clienteRepositorio;
		this.comercioRepositorio = comercioRepositorio;
		this.productoRepositorio = productoRepositorio;
	}




	@Override
	public void almacenarCompra(Factura factura) {
		try {
			comercioRepositorio.save(factura.getComercio());
			clienteRepositorio.save(factura.getCliente());
			productoRepositorio.saveAll(factura.getProductos());
			facturaRepositorio.save(factura);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

}
