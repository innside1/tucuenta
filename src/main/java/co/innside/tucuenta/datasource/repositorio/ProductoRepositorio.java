package co.innside.tucuenta.datasource.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.innside.tucuenta.datasource.modelo.Producto;

@Repository
public interface ProductoRepositorio extends CrudRepository<Producto, Long> {
	
	List<Producto> findAllByFacturaId(@Param("idfactura") Long idFactura);

}
