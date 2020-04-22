package co.innside.tucuenta.http.datasource;

import java.io.IOException;

import org.springframework.stereotype.Component;

import co.innside.tucuenta.constante.ApiMensajeHttp;
import co.innside.tucuenta.constante.CodigoError;
import co.innside.tucuenta.excepcion.ComunicacionFallidaException;
import co.innside.tucuenta.http.respuesta.Respuesta;
import co.innside.tucuenta.util.ProxyDirector;
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
	
	private OkHttpClient httpClient;
	
	private OkHttpClient httpClientProxy;
	
	private final ProxyDirector proxyDirector;
	
	public ServicioHttp(ProxyDirector proxyDirector) {
		super();
		this.proxyDirector = proxyDirector;
	}
	
	/**
	 * Crea una peticion con proxy
	 * @param url
	 * @param host
	 * @param puerto
	 * @param usuario
	 * @param password
	 * @return
	 */
	protected Respuesta pollProxy(Request request, String host, int puerto, String usuario, String password) {
		crearClienteHttpConProxy(host, puerto, usuario, password);
		log.info(ApiMensajeHttp.PETICION_TOC , request.url().toString());
		return ejecutarPolling(httpClientProxy.newCall(request));
	}
	
	/**
	 * Crea una peticion sin proxy
	 * @param url
	 * @return
	 */
	protected Respuesta poll(Request request) {
		crearClienteHttpSinProxy();
		log.info(ApiMensajeHttp.PETICION_TOC , request.url().toString());
		return ejecutarPolling(httpClient.newCall(request));
	}
	
	/**
	 * Ejecuta un polling
	 * @param call
	 * @return
	 */
	private Respuesta ejecutarPolling(Call call) {
		int statusCode;
		String body = "";
		try (Response httpRespuesta = call.execute()) {
			body = httpRespuesta.body().string();
			statusCode = httpRespuesta.code();
			log.info(ApiMensajeHttp.RESPUESTA_TOC , body);
		} catch (IOException e) {
			log.error(ApiMensajeHttp.ERROR_TOC , e.getMessage());
			throw new ComunicacionFallidaException(CodigoError.CODIGO_ERROR_CONEXION_PROXY, ApiMensajeHttp.ERROR_CONEXION_TOC, e);
		}
    	return new Respuesta(body, statusCode);
	}
	
	/**
	 * Crea un cliente Http con proxy
	 * @param host
	 * @param puerto
	 * @param usuario
	 * @param password
	 */
	private void crearClienteHttpConProxy(String host, int puerto, String usuario, String password) {
		OkHttpClient.Builder httpClientBuilderProxy = new OkHttpClient.Builder()
				.proxy(proxyDirector.obtenerProxy(host, puerto, usuario, password));
		httpClientProxy = httpClientBuilderProxy.build();
	}
	
	/**
	 * Crea un cliente Http sin proxy
	 */
	private void crearClienteHttpSinProxy() {
		httpClient =  new OkHttpClient();
	}

}
