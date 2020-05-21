package co.adwork.stive.util;

import java.io.Serializable;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import co.adwork.stive.excepcion.TuCuentaException;
import co.adwork.stive.rest.respuesta.dto.Error;
import co.adwork.stive.rest.respuesta.dto.Respuesta;


@Component
public class UtilControlador {

    public ResponseEntity<Respuesta<Serializable>> respuestaExcepcion(TuCuentaException excepcion, HttpStatus h, Logger logger) {
        logger.error(excepcion.getMessage(), excepcion);
        return ResponseEntity.status(h).body(generarMensajeError(excepcion.getCodigo(),excepcion.getMessage()));
    }

    private static Respuesta<Serializable> generarMensajeError(Integer codigo, String mensaje) {
        return Respuesta.builder().error(Error.builder().codigo(codigo).mensaje(mensaje).build()
                ).build();
    }
   
}
