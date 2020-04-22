package co.innside;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase principal de tucuenta
 * @author juan.hoyos
 *
 */
@SpringBootApplication
@Slf4j
public class TuCuenta {

	public static void main(String[] args) {
		SpringApplication.run(TuCuenta.class, args);
        log.info("Aplicacion TuCuenta iniciada...");
	}

}
