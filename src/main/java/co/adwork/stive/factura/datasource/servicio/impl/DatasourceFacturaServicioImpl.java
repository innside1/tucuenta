package co.adwork.stive.factura.datasource.servicio.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import co.adwork.stive.datasource.modelo.ArchivoCsv;
import co.adwork.stive.datasource.modelo.Cliente;
import co.adwork.stive.datasource.modelo.Comercio;
import co.adwork.stive.datasource.modelo.Factura;
import co.adwork.stive.datasource.modelo.Producto;
import co.adwork.stive.datasource.repositorio.ClienteRepositorio;
import co.adwork.stive.datasource.repositorio.ComercioRepositorio;
import co.adwork.stive.datasource.repositorio.FacturaRepositorio;
import co.adwork.stive.datasource.repositorio.ProductoRepositorio;
import co.adwork.stive.factura.datasource.servicio.DatasourceFacturaServicio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatasourceFacturaServicioImpl implements DatasourceFacturaServicio {
	
	private final FacturaRepositorio facturaRepositorio;
	private final ClienteRepositorio clienteRepositorio;
	private final ComercioRepositorio comercioRepositorio;
	private final ProductoRepositorio productoRepositorio;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
			facturaRepositorio.save(factura);
			productoRepositorio.saveAll(factura.getProductos());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public List<Producto> consultarProductosPorFacturaId(Long idFactura) {
		return productoRepositorio.findAllByFacturaId(idFactura);
	}

	@Override
	public List<Factura> consultarFacturasPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		return facturaRepositorio.findAllByFechaCompraBetween(fechaInicio, fechaFin);
	}

	@Override
	public List<Factura> leerArchivoCsv(MultipartFile archivo) {
		List<Factura> facturas = null;
		try (Reader reader = new BufferedReader(new InputStreamReader(archivo.getInputStream()))) {
            CsvToBean<ArchivoCsv> csvToBean = new CsvToBeanBuilder<ArchivoCsv>(reader)
                    .withType(ArchivoCsv.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<ArchivoCsv> datos = csvToBean.parse();            
            if(CollectionUtils.isNotEmpty(datos)) {
           	 facturas = datos.stream().skip(1).map(
               		 dato -> Factura
               		 .builder()
               		 .fechaCompra(LocalDateTime.parse(dato.getFechaFinRegistro(), formatter))
               		 .url(dato.getUrlFactura())
               		 .idCsv(dato.getId())
               		 .comercio(Comercio.builder().barrio(dato.getBarrio()).build())
               		 .cliente(Cliente.builder().correo(dato.getCorreoCliente()).build())
               		 .build())
               		 .collect(Collectors.toList());
            }
		} catch (IOException ex) {
       	 log.error(ex.getMessage());
        }
		return facturas;
	}
}
