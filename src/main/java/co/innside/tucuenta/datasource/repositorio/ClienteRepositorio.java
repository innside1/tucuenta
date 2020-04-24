package co.innside.tucuenta.datasource.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.innside.tucuenta.modelo.Cliente;

@Repository
public interface ClienteRepositorio extends CrudRepository<Cliente, Long> {

}
