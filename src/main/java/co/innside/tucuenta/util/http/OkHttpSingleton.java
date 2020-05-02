package co.innside.tucuenta.util.http;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mysql.cj.util.StringUtils;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * Generador de la instancia unica okhttp y okhttp con proxy
 * @author juan.hoyos
 *
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class OkHttpSingleton {
	
    @Value("${api.tucuenta.util.okhttp.config.callTimeout}")
	private Integer hitTotalTimeOut;
	
	private static Integer hitTotalTimeOutStatic;
	
	@Value("${api.tucuenta.util.okhttp.config.callTimeout}")
    public void setHitTotalTimeOutStatic(Integer hitTotalTimeOut){
		OkHttpSingleton.hitTotalTimeOutStatic = hitTotalTimeOut;
    }
    
    @Value("${api.tucuenta.util.okhttp.config.writeTimeout}")
	private Integer escrituraTimeOut;
	
	private static Integer escrituraTimeOutStatic;
	
	@Value("${api.tucuenta.util.okhttp.config.writeTimeout}")
    public void setEscrituraTimeOutStatic(Integer escrituraTimeOut){
		OkHttpSingleton.escrituraTimeOutStatic = escrituraTimeOut;
    }
    
    @Value("${api.tucuenta.util.okhttp.config.readTimeout}")
	private Integer lecturaTimeOut;
	
	private static Integer lecturaTimeOutStatic;
	
	@Value("${api.tucuenta.util.okhttp.config.readTimeout}")
    public void setLecturaTimeOutStatic(Integer lecturaTimeOut){
		OkHttpSingleton.lecturaTimeOutStatic = lecturaTimeOut;
    }
    
    @Value("${api.tucuenta.util.okhttp.config.connectTimeout}")
	private Integer conexionTimeOut;
	
	private static Integer conexionTimeOutStatic;
	
	@Value("${api.tucuenta.util.okhttp.config.connectTimeout}")
    public void setConexionTimeOutStatic(Integer conexionTimeOut){
		OkHttpSingleton.conexionTimeOutStatic = conexionTimeOut;
    }
    
    @Value("${api.tucuenta.util.okhttp.config.pool.maxIdleConnections}")
	private Integer conexionesMaximasOciosas;
	
	private static Integer conexionesMaximasOciosasStatic;
	
	@Value("${api.tucuenta.util.okhttp.config.pool.maxIdleConnections}")
    public void setConexionesMaximasOciosasStatic(Integer conexionesMaximasOciosas){
		OkHttpSingleton.conexionesMaximasOciosasStatic = conexionesMaximasOciosas;
    }
    
    @Value("${api.tucuenta.util.okhttp.config.pool.keepAliveDuration}")
	private Integer tiempoMaximoConexionesOciosas;
	
	private static Integer tiempoMaximoConexionesOciosasStatic;
	
	@Value("${api.tucuenta.util.okhttp.config.pool.keepAliveDuration}")
    public void setTiempoMaximoConexionesOciosasStatic(Integer tiempoMaximoConexionesOciosas){
		OkHttpSingleton.tiempoMaximoConexionesOciosasStatic = tiempoMaximoConexionesOciosas;
    }
	
	@Value("${api.tucuenta.util.proxy.config.hostname}") 
	private String proxyHost;
	
	private static String proxyHostStatic;
	
	@Value("${api.tucuenta.util.proxy.config.hostname}")
    public void setProxyHostStatic(String proxyHost){
		OkHttpSingleton.proxyHostStatic = proxyHost;
    }
	
	@Value("${api.tucuenta.util.proxy.config.port}") 
	private Integer proxyPort;
	
	private static Integer proxyPortStatic;
	
	@Value("${api.tucuenta.util.proxy.config.port}")
    public void setProxyPortStatic(Integer proxyPort){
		OkHttpSingleton.proxyPortStatic = proxyPort;
    }
	
	private static OkHttpClient okHttpClient;
	
	public static OkHttpClient obtenerInstanciaOkHttp() {
		if (okHttpClient == null) {
			okHttpClient = crearOkHttpSingleton();
		}
		return okHttpClient;
	}
	
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	private static OkHttpClient crearOkHttpSingleton() {
		OkHttpClient client = new OkHttpClient.Builder()
			       .build();
		return StringUtils.isNullOrEmpty(proxyHostStatic)?
				client.newBuilder()
				   .connectionPool(new ConnectionPool(conexionesMaximasOciosasStatic, 
							tiempoMaximoConexionesOciosasStatic, TimeUnit.MINUTES))
				   .callTimeout(hitTotalTimeOutStatic, TimeUnit.SECONDS)
				   .writeTimeout(escrituraTimeOutStatic, TimeUnit.SECONDS)
				   .connectTimeout(conexionTimeOutStatic, TimeUnit.SECONDS)
			       .readTimeout(lecturaTimeOutStatic, TimeUnit.SECONDS)
			       .build() :
		    	   client.newBuilder()
				   .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHostStatic, proxyPortStatic)))
				   .connectionPool(new ConnectionPool(conexionesMaximasOciosasStatic, 
							tiempoMaximoConexionesOciosasStatic, TimeUnit.MINUTES))
				   .callTimeout(hitTotalTimeOutStatic, TimeUnit.SECONDS)
				   .writeTimeout(escrituraTimeOutStatic, TimeUnit.SECONDS)
				   .connectTimeout(conexionTimeOutStatic, TimeUnit.SECONDS)
			       .readTimeout(lecturaTimeOutStatic, TimeUnit.SECONDS)
			       .build();
	}
}
