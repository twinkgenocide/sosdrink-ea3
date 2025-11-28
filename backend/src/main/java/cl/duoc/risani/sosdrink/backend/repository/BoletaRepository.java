package cl.duoc.risani.sosdrink.backend.repository;

import org.springframework.data.repository.CrudRepository;
import cl.duoc.risani.sosdrink.backend.entities.Boleta;
import java.util.Optional;
import java.util.List;

public interface BoletaRepository extends CrudRepository<Boleta, Long> {
    
    Optional<Boleta> findByFolioAndClienteRun(String folio, String clienteRun);
    boolean existsByFolio(String folio);
    List<Boleta> findByClienteRun(String clienteRun);

}
