package co.innside.tucuenta.datasource.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.innside.tucuenta.modelo.Producto;

@Repository
public interface ProductoRepositorio extends CrudRepository<Producto, Long> {

}
