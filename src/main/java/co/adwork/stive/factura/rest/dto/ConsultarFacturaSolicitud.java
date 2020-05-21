package co.adwork.stive.factura.rest.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.Range;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConsultarFacturaSolicitud {
	    
    @ApiModelProperty(value = "Nit de la empresa", required = true, position = 1)
    @NotBlank(message = "El Nit de la empresa no debe ser vacío")
    String nit;

	@ApiModelProperty(value = "Numero factura", required = true, position = 2)
    @NotNull(message = "Debe ingresar un valor para la factura")
    @Range(message = "Los numeros de factura van desde 1 hasta 1000", min = 1, max = 1000)
    Integer factura;
}
