package co.innside.tucuenta.datasource.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.innside.tucuenta.modelo.Factura;

@Repository
public interface FacturaRepositorio extends CrudRepository<Factura, Long> {

}
