package co.innside.tucuenta.constante;

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
    
	public static final String PETICION_TOC = "Peticion servicio TOC: {}";
	public static final String RESPUESTA_TOC = "Respuesta servicio TOC: {}";
	public static final String ERROR_TOC = "Error al ejecutar el servicio TOC: {}";
	public static final String ERROR_CONEXION_TOC = "Error de conexion con TOC";
}
