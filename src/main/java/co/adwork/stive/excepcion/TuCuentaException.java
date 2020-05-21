package co.adwork.stive.excepcion;

import javax.validation.constraints.NotBlank;

/**
 * Excepcion padre
 * @author juan.hoyos
 *
 */
public class TuCuentaException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final Integer codigo;

    public TuCuentaException(String msg) {
        super(msg);
        this.codigo = null;
    }

    public TuCuentaException(Throwable throwable) {
        super(throwable);
        this.codigo = null;
    }

    public TuCuentaException(String mensaje, Throwable throwable) {
        super(mensaje, throwable);
        this.codigo = null;
    }

    public TuCuentaException(@NotBlank Integer codigo, String msg) {
        super(msg);
        this.codigo = codigo;
    }

    public TuCuentaException(Integer codigo, String mensaje, Throwable e) {
        super(mensaje, e);
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }
}
