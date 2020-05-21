package co.adwork.stive.datasource.repositorio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.adwork.stive.datasource.modelo.Factura;

@Repository
public interface FacturaRepositorio extends CrudRepository<Factura, Long> {
	
	List<Factura> findAllByFechaCompraBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

}
