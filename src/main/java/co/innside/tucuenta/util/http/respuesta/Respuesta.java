package co.innside.tucuenta.util.http.respuesta;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de respuesta peticiones Http
 * @author juan.hoyos
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class Respuesta implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2754454647298881391L;

	private String body;
	
	private int statusCode;
	
}
