package co.adwork.stive.factura.negocio.servicio.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.adwork.stive.datasource.modelo.Cliente;
import co.adwork.stive.datasource.modelo.Comercio;
import co.adwork.stive.datasource.modelo.Factura;
import co.adwork.stive.datasource.modelo.Producto;
import co.adwork.stive.factura.datasource.servicio.DatasourceFacturaServicio;
import co.adwork.stive.factura.negocio.servicio.FacturaServicio;
import co.adwork.stive.util.ocr.OcrUtilidad;
import lombok.extern.slf4j.Slf4j;

/**
 * Capa de operaciones de negocio
 * @author juan.hoyos
 *
 */
@Slf4j
@Service
public class FacturaServicioImpl implements FacturaServicio {
	
	private final DatasourceFacturaServicio datasourceFacturaServicio;
	
	private final OcrUtilidad ocrUtilidad;
	
	public FacturaServicioImpl(DatasourceFacturaServicio datasourceFacturaServicio, OcrUtilidad ocrUtilidad) {
		super();
		this.datasourceFacturaServicio = datasourceFacturaServicio;
		this.ocrUtilidad = ocrUtilidad;
	}

	@Override
	public String procesarFactura(MultipartFile archivo) {   
        List<Factura> facturas = datasourceFacturaServicio.leerArchivoCsv(archivo);
        lecturaAmazon(facturas);
		return "listo";	
	}

	/**
	 * @param facturas
	 */
	private void lecturaGoogle(List<Factura> facturas) {
		for (Factura factura: facturas) {
        	 almacenarFactura(factura, ocrUtilidad.lecturaGoogle(factura.getUrl()));
        }
	}
	
	/**
	 * @param facturas
	 */
	private void lecturaAmazon(List<Factura> facturas) {
		for (Factura factura: facturas) {
			String nombreArchivo = extraerNombreArchivo(factura);
        	descargarArchivo(nombreArchivo, crearUrlFactura(factura));
        	almacenarFactura(factura, ocrUtilidad.lecturaAmazon(nombreArchivo));
        	eliminarArchivo(nombreArchivo);
        }
	}

	/**
	 * Elimina un archivo
	 * @param nombreArchivo
	 */
	private void eliminarArchivo(String nombreArchivo) {
		try {
			Files.delete(Paths.get(nombreArchivo));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * @param factura
	 */
	private String extraerNombreArchivo(Factura factura) {
		String urlArchivo = factura.getUrl();
		String[] rutaArchivo =  urlArchivo.split("/");
		return rutaArchivo[rutaArchivo.length - 1];
	}
	
	private URL crearUrlFactura(Factura factura) {
        URL url = null;
		try {
			url = new URL(factura.getUrl());
		} catch (MalformedURLException e1) {
			log.error(e1.getMessage());
		}
		return url;
	}

	/**
	 * @param nombreArchivo
	 * @param url
	 * @throws IOException
	 */
	private void descargarArchivo(String nombreArchivo, URL url) {
		try {
				FileUtils.copyURLToFile(url, new File(nombreArchivo));
			} catch (IOException e) {
				log.error(e.getMessage());
			}
	}

	/**
	 * @param nombreArchivo
	 * @param url
	 */
	private void descargarArchivoGrande(String nombreArchivo, URL url) {
		try (FileOutputStream fileOutputStream = new FileOutputStream(nombreArchivo);
        	 ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream())){
			fileOutputStream.getChannel()
			  .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	
	private void almacenarFactura(Factura objectoFactura, String lectura) {
		Factura factura = new Factura();
		factura.setTotal(null);
		factura.setFechaCompra(objectoFactura.getFechaCompra());
		factura.setIdCsv(objectoFactura.getIdCsv());
		factura.setUrl(objectoFactura.getUrl());
		
		Comercio comercio = new Comercio();
		comercio.setBarrio(objectoFactura.getComercio().getBarrio());
		comercio.setDireccion(null);
		comercio.setNit(null);
		comercio.setNombre(null);
		comercio.setTelefono(null);
		factura.setComercio(comercio);
		
		Cliente cliente = new Cliente();
		cliente.setCorreo(objectoFactura.getCliente().getCorreo());
		factura.setCliente(cliente);
		
		List<Producto> productos = new ArrayList<>();
		Producto producto1 = new Producto();
		producto1.setCantidad(null);
		producto1.setPrecio(null);
		producto1.setNombre(lectura);
		producto1.setFactura(factura);
		
		productos.add(producto1);
		
		factura.setProductos(productos);
		try {
			datasourceFacturaServicio.almacenarCompra(factura);
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Registro no almacenado. " + "id: " + objectoFactura.getIdCsv() + ", url: " + objectoFactura.getUrl());
		}
	}

}
