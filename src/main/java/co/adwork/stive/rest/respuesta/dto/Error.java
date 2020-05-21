package co.adwork.stive.rest.respuesta.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import co.adwork.stive.constante.ApiSalidaMensajeUsuario;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * Error generico del response del API Rest
 * @author juan.hoyos
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="Error",description="Descripcion del error")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error implements Serializable {

    private static final long serialVersionUID = 0x1D72BEE56BFC98B2L;
    
    @ApiModelProperty(position = 1, value = ApiSalidaMensajeUsuario.API_RESPUESTA_ERROR_CODIGO)
    private Integer codigo;
    
    @ApiModelProperty(position = 2, value = ApiSalidaMensajeUsuario.API_RESPUESTA_ERROR_MENSAJE)
    private String mensaje;
}
