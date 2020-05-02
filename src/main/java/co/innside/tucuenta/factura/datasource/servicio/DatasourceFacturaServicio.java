package co.innside.tucuenta.factura.datasource.servicio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.innside.tucuenta.datasource.modelo.Factura;
import co.innside.tucuenta.datasource.modelo.Producto;

public interface DatasourceFacturaServicio {
	
	/**
	 * Almacena la factura, los productos, el comercio y el cliente de una compra
	 * @param factura
	 */
	void almacenarCompra(Factura factura);
	
	/**
	 * Consulta los productos asociados a una factura
	 * @param idFactura
	 * @return
	 */
	List<Producto> consultarProductosPorFacturaId(Long idFactura);
	
	/**
	 * Consulta las facturas en un rango de fechas
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	List<Factura> consultarFacturasPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
	
	/**
	 * Lee un archivo .csv
	 * @param archivo
	 * @return
	 */
	List<Factura> leerArchivoCsv(MultipartFile archivo);

}
