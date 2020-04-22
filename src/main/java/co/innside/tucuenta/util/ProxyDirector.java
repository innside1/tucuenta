package co.innside.tucuenta.util;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;

import org.assertj.core.util.Strings;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

/**
 * Componente que genera un proxy
 * @author juan.hoyos
 *
 */
@Component
@NoArgsConstructor
public class ProxyDirector {
	
	/**
	 * Genera un proxy
	 * @param host
	 * @param puerto
	 * @param usuario
	 * @param password
	 * @return
	 */
	public Proxy obtenerProxy(String host, int puerto, String usuario, String password) {
		return Strings.isNullOrEmpty(usuario) || Strings.isNullOrEmpty(password) ? 
				generarProxySinAutenticacion(host, puerto) :
				generarProxyConAutenticacion(host, puerto, usuario, password);
    }
	
	/**
	 * Genera un proxy sin autenticacion
	 * @param host
	 * @param puerto
	 * @return
	 */
	private Proxy generarProxySinAutenticacion(String host, int puerto) {
		return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, puerto));
    }
	
	/**
	 * Genera un proxy con autenticacion
	 * @param host
	 * @param puerto
	 * @return
	 */
	private Proxy generarProxyConAutenticacion(String host, int puerto, String usuario, String password) {
		Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(host, puerto));
	    Authenticator authenticator = new Authenticator() {
	    	@Override
	        public PasswordAuthentication getPasswordAuthentication() {
	        return (new PasswordAuthentication(usuario, password.toCharArray()));
	        }
	    };
	    Authenticator.setDefault(authenticator);
	    return proxy;
    }

}
