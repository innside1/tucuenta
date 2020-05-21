package co.adwork.stive.constante;

public class ApiMensajeHttp {

    private ApiMensajeHttp() {
    }

    public static final String API_HTTP_STATUS_200 = "La solicitud fue completada";
    public static final String API_HTTP_STATUS_201 = "La solicitud fue creada";
    public static final String API_HTTP_STATUS_206 = "La solicitud fue creada presenta errores";
    public static final String API_HTTP_STATUS_204 = "Sin contenido";
    public static final String API_HTTP_STATUS_400 = "Solicitud mal formada";
    public static final String API_HTTP_STATUS_502 = "Error de integracion";
    public static final String API_HTTP_STATUS_500 = "Error Interno del servidor";
    
	public static final String HTTP_PETICION = "Peticion servicio: {}";
	public static final String HTTP_RESPUESTA = "Respuesta servicio: {}";
	public static final String HTTP_ERROR = "Error al ejecutar el servicio: {}";
	public static final String HTTP_ERROR_CONEXION = "Error de conexion";
	public static final String HTTP_ERROR_TIMEOUT = "Timeout excedido: {}";
	public static final String HTTP_ERROR_TIMEOUT_HIT_COMPLETO = "Timeout de hit completo excedido: {}";
	
	public static final String FACTURA = "No existe una factura con ese numero";
}
