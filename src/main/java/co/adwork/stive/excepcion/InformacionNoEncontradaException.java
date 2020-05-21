package co.adwork.stive.excepcion;

import javax.validation.constraints.NotBlank;

/**
 * Excepcion de errores genericos de data
 * 
 * @author juan.hoyos
 */
public class InformacionNoEncontradaException extends TuCuentaException {

    private static final long serialVersionUID = 0x4D9F49EC614DC330L;
    private final Integer codigo;

    public InformacionNoEncontradaException(@NotBlank Integer codigo, String msg) {
      this(codigo, msg, null);
    }


    public InformacionNoEncontradaException(@NotBlank Integer codigo, String msg, Throwable causa) {
		super(msg, causa);
		this.codigo = codigo;
	}

    /**
     * @return the codigo
     */
    @Override
    public Integer getCodigo() {
        return codigo;
    }

}
