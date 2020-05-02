package co.innside.tucuenta.util.ocr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import com.amazonaws.services.textract.model.DetectDocumentTextRequest;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.amazonaws.services.textract.model.Document;
import com.amazonaws.util.IOUtils;
import com.asprise.ocr.Ocr;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * Componente de lectura optica OCR
 * @author juan.hoyos
 *
 */
@Slf4j
@Component
public class OcrUtilidad {
	
	private static final String ESPANOL = "spa";
	private static final String PATH_TESSDATA = "src/main/resources/tessdata";
	private static final AmazonTextract amazonClient = AmazonTextractClientBuilder.defaultClient();
	
	private CloudVisionTemplate cloudVisionTemplate;
	private ResourceLoader resourceLoader;
	
	public OcrUtilidad(CloudVisionTemplate cloudVisionTemplate, ResourceLoader resourceLoader) {
		super();
		this.cloudVisionTemplate = cloudVisionTemplate;
		this.resourceLoader = resourceLoader;
	}

	/**
	 * Lectura de imagenes jpg, bmp, png y documentos PDF 
	 * @param nombreArchivo
	 * @return
	 */
	public String lecturaTesseract(String nombreArchivo) {
		String resultado = null;
		try {
			BufferedImage image = ImageIO.read(new File(nombreArchivo));
			Tesseract tesseract = new Tesseract();
			tesseract.setDatapath(PATH_TESSDATA);
			tesseract.setLanguage(ESPANOL);
			resultado = tesseract.doOCR(image);
			log.info(resultado);
		} catch (IOException | TesseractException e) {
			log.error(e.getMessage());
		}
		return resultado;
	}
	
	/**
	 * Lectura de imagenes jpg, bmp, png, documentos pdf y codigos de barra
	 * @param nombreArchivo
	 * @return
	 */
	public String lecturaAsprise(String nombreArchivo) {
		Ocr.setUp(); // one time setup
		Ocr ocr = new Ocr(); // create a new OCR engine
		ocr.startEngine(ESPANOL, Ocr.SPEED_SLOW); // English
		String resultado = ocr.recognize(new File[] {new File(nombreArchivo)},
				Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
		log.info(resultado);
		ocr.stopEngine();
		return resultado;
	}
	
	/**
	 * Lectura optica de imagenes por Google
	 * @param urlImage
	 * @return
	 */
	public String  lecturaGoogle(String urlImage) {
		String resultado = this.cloudVisionTemplate
				.extractTextFromImage(this.resourceLoader.getResource(urlImage));
		log.info(resultado);
		return resultado;
	}
	
	/**
	 * Lectura optica de imagenes por Amazon
	 * @param nombreArchivo
	 * @return
	 */
	public String lecturaAmazon(String nombreArchivo) {
		ByteBuffer imageBytes = null;
		try (InputStream inputStream = new FileInputStream(new File(nombreArchivo))) {
		    imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		DetectDocumentTextRequest request = new DetectDocumentTextRequest()
		        .withDocument(new Document()
		                .withBytes(imageBytes));
		DetectDocumentTextResult result = amazonClient.detectDocumentText(request);
		log.info(result.getDetectDocumentTextModelVersion());
		result.getBlocks().parallelStream().forEach(elemento -> log.info(elemento.getText()));
		return result.getDetectDocumentTextModelVersion();
	}

}
