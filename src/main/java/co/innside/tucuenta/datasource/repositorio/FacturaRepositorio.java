package co.innside.tucuenta.datasource.repositorio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.innside.tucuenta.datasource.modelo.Factura;

@Repository
public interface FacturaRepositorio extends CrudRepository<Factura, Long> {
	
	List<Factura> findAllByFechaCompraBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

}
