package co.adwork.stive.rest.respuesta.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
/**
 * DTO vacio para cuando no se envio dato pero se debe enviar un objeto vacio al frontend.
 * @author juan.hoyos
 *
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DatoDefault  implements Serializable{

    private static final long serialVersionUID = -0x2AC938ED943E8043L;
    private String defaultvalue;
    public DatoDefault (){
        defaultvalue = null;
    }
}
