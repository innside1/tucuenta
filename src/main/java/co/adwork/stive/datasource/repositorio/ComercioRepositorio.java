package co.adwork.stive.datasource.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.adwork.stive.datasource.modelo.Comercio;

@Repository
public interface ComercioRepositorio extends CrudRepository<Comercio, Long> {

}
