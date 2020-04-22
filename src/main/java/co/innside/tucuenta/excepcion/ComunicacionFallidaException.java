package co.innside.tucuenta.excepcion;

import javax.validation.constraints.NotBlank;

/**
 * Exception para cuando no exista comunicacion con un componente.
 * 
 * @author juan.hoyos
 */
public class ComunicacionFallidaException extends TuCuentaException {

    private static final long serialVersionUID = 0x4D9F49EC614DC330L;
    private final Integer codigo;

    public ComunicacionFallidaException(@NotBlank Integer codigo, String msg) {
      this(codigo, msg, null);
    }


    public ComunicacionFallidaException(@NotBlank Integer codigo, String msg, Throwable causa) {
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
