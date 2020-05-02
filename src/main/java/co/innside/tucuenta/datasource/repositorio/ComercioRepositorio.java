package co.innside.tucuenta.datasource.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.innside.tucuenta.datasource.modelo.Comercio;

@Repository
public interface ComercioRepositorio extends CrudRepository<Comercio, Long> {

}
