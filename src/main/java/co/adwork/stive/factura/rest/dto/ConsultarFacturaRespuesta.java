package co.adwork.stive.factura.rest.dto;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultarFacturaRespuesta implements Serializable {
	    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5886879445712192858L;
	
    private String nit;
    private Integer total;
    private LocalDate fechaCompra;
}
