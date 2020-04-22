package co.innside.tucuenta.rest.respuesta.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

import co.innside.tucuenta.constante.ApiSalidaMensajeUsuario;

/**
 * Respuesta generica
 * @author juan.hoyos
 *
 * @param <T>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "Respuesta", description = "Valor de respuesta a peticion http")
public class Respuesta <T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -0x38AD107975E28DECL;

    @ApiModelProperty(position = 1, value = ApiSalidaMensajeUsuario.API_RESPUESTA_DATOS)
    private T datos;

    @ApiModelProperty(position = 2, value = ApiSalidaMensajeUsuario.API_RESPUESTA_ERROR)
    private Error error;

    public Respuesta(T datos) {
        this.datos = datos;
    }
}
