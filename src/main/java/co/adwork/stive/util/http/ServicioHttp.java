package co.adwork.stive.util.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;

import org.springframework.stereotype.Component;

import co.adwork.stive.constante.ApiMensajeHttp;
import co.adwork.stive.constante.CodigoError;
import co.adwork.stive.excepcion.ComunicacionFallidaException;
import co.adwork.stive.util.http.respuesta.Respuesta;
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
		log.info(ApiMensajeHttp.HTTP_PETICION , request.url().toString());
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
			log.info(ApiMensajeHttp.HTTP_RESPUESTA , body);
		} catch (SocketTimeoutException e) {
			log.error(ApiMensajeHttp.HTTP_ERROR_TIMEOUT , e.getMessage());
			throw new ComunicacionFallidaException(CodigoError.CODIGO_ERROR_CONEXION, ApiMensajeHttp.HTTP_ERROR_CONEXION, e);
		} catch (InterruptedIOException e) {
			log.error(ApiMensajeHttp.HTTP_ERROR_TIMEOUT_HIT_COMPLETO , e.getMessage());
			throw new ComunicacionFallidaException(CodigoError.CODIGO_ERROR_CONEXION, ApiMensajeHttp.HTTP_ERROR_CONEXION, e);
		} catch (IOException e) {
			log.error(ApiMensajeHttp .HTTP_ERROR , e.getMessage());
			throw new ComunicacionFallidaException(CodigoError.CODIGO_ERROR_CONEXION, ApiMensajeHttp.HTTP_ERROR_CONEXION, e);
		}
    	return new Respuesta(body, statusCode);
	}

}
