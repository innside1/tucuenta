package co.innside.tucuenta.util.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;

import org.springframework.stereotype.Component;

import co.innside.tucuenta.constante.ApiMensajeHttp;
import co.innside.tucuenta.constante.CodigoError;
import co.innside.tucuenta.excepcion.ComunicacionFallidaException;
import co.innside.tucuenta.util.http.respuesta.Respuesta;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Componente encargado de ejecutar peticiones Http con o sin proxy
 * @author juan.hoyos
 *
 */
@Slf4j
@Component
public class ServicioHttp {

	private static OkHttpClient httpClient;
	
	public ServicioHttp() {
		super();
	}
	
	/**
	 * Crea una peticion 
	 * @param url
	 * @return
	 */
	protected static Respuesta poll(Request request) {
		httpClient =  OkHttpSingleton.obtenerInstanciaOkHttp();
		log.info(ApiMensajeHttp.PETICION_TOC , request.url().toString());
		return ejecutarPolling(httpClient.newCall(request));
	}
	
	/**
	 * Ejecuta un polling
	 * @param call
	 * @return
	 */
	private static Respuesta ejecutarPolling(Call call) {
		int statusCode;
		String body = "";
		try (Response httpRespuesta = call.execute()) {
			body = httpRespuesta.body().string();
			statusCode = httpRespuesta.code();
			log.info(ApiMensajeHttp.RESPUESTA_TOC , body);
		} catch (SocketTimeoutException e) {
			log.error(ApiMensajeHttp.ERROR_TIMEOUT_TOC , e.getMessage());
			throw new ComunicacionFallidaException(CodigoError.CODIGO_ERROR_CONEXION, ApiMensajeHttp.ERROR_CONEXION_TOC, e);
		} catch (InterruptedIOException e) {
			log.error(ApiMensajeHttp.ERROR_TIMEOUT_TOC_HIT_COMPLETO , e.getMessage());
			throw new ComunicacionFallidaException(CodigoError.CODIGO_ERROR_CONEXION, ApiMensajeHttp.ERROR_CONEXION_TOC, e);
		} catch (IOException e) {
			log.error(ApiMensajeHttp .ERROR_TOC , e.getMessage());
			throw new ComunicacionFallidaException(CodigoError.CODIGO_ERROR_CONEXION, ApiMensajeHttp.ERROR_CONEXION_TOC, e);
		}
    	return new Respuesta(body, statusCode);
	}

}
