package co.innside.tucuenta.factura.negocio.servicio.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.innside.tucuenta.datasource.modelo.Factura;
import co.innside.tucuenta.factura.datasource.servicio.DatasourceFacturaServicio;
import co.innside.tucuenta.factura.negocio.servicio.FacturaServicio;
import co.innside.tucuenta.util.ocr.OcrUtilidad;
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
        facturas.isEmpty();
        

        String urlArchivo = facturas.get(55).getUrl();
        String[] rutaArchivo =  urlArchivo.split("/");
        String nombreArchivo = rutaArchivo[rutaArchivo.length - 1];
        
        URL url = null;
		try {
			url = new URL(facturas.get(55).getUrl());
		} catch (MalformedURLException e1) {
			log.error(e1.getMessage());
		}
		
		descargarArchivo(nombreArchivo, url);
//		ocrUtilidad.lecturaAsprise(nombreArchivo);
//		ocrUtilidad.lecturaTesseract(nombreArchivo);
//		ocrUtilidad.lecturaGoogle(facturas.get(55).getUrl());	
		ocrUtilidad.lecturaAmazon(nombreArchivo);
		
		return "";
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

}
