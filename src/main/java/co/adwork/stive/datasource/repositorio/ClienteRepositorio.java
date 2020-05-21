package co.adwork.stive.datasource.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.adwork.stive.datasource.modelo.Cliente;

@Repository
public interface ClienteRepositorio extends CrudRepository<Cliente, Long> {

}
