package co.adwork.stive.excepcion;

import javax.validation.ConstraintViolation;
import org.springframework.validation.FieldError;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationError {
    
    @ApiModelProperty(position=1)
    private String object;
    
    @ApiModelProperty(position=2)
    private String rejectedValue;
    
    @ApiModelProperty(position=3)
    private String reason;
    
    public ValidationError(FieldError fieldError){
        object =fieldError.getField();
        rejectedValue= fieldError.getRejectedValue()!=null ? fieldError.getRejectedValue().toString(): "";
        reason = fieldError.getDefaultMessage();
    }
    
    public ValidationError(ConstraintViolation<?> constraintViolation){
        reason  =constraintViolation.getMessage();
        rejectedValue= constraintViolation.getInvalidValue().toString(); 
        object = constraintViolation.getPropertyPath().toString();
    }
}
