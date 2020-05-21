package co.adwork.stive.datasource.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.adwork.stive.datasource.modelo.Producto;

@Repository
public interface ProductoRepositorio extends CrudRepository<Producto, Long> {
	
	List<Producto> findAllByFacturaId(@Param("idfactura") Long idFactura);

}
