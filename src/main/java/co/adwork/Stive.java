package co.adwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase principal de Stive
 * @author juan.hoyos
 *
 */
@SpringBootApplication
@Slf4j
public class Stive {

	public static void main(String[] args) {
		SpringApplication.run(Stive.class, args);
        log.info("Aplicacion Stive iniciada...");
	}

}
