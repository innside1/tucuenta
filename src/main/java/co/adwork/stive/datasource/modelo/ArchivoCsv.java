package co.adwork.stive.datasource.modelo;

import com.opencsv.bean.CsvBindByPosition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo que representa un archivo .csv generado por TypeForm
 * @author juan.hoyos
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArchivoCsv {
	
	@CsvBindByPosition(position = 0)
	private String id;
	
	@CsvBindByPosition(position = 1)
	private String urlFactura;
	
	@CsvBindByPosition(position = 2)
	private String barrio;
	
	@CsvBindByPosition(position = 3)
	private String correoCliente;
	
	@CsvBindByPosition(position = 4)
	private String fechaInicioRegistro;
	
	@CsvBindByPosition(position = 5)
	private String fechaFinRegistro;
	
	@CsvBindByPosition(position = 6)
	private String red;

}
