package cl.duoc.risani.sosdrink.backend.repository;

import org.springframework.data.repository.CrudRepository;

import cl.duoc.risani.sosdrink.backend.entities.FolioGenerator;
import java.util.Optional;


public interface FolioGeneratorRepository extends CrudRepository<FolioGenerator, Integer> {
    public Optional<FolioGenerator> findByYear(Integer year);
}
