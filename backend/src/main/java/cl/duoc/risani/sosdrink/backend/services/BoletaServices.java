package cl.duoc.risani.sosdrink.backend.services;

import java.util.List;
import java.util.Optional;

import cl.duoc.risani.sosdrink.backend.entities.Boleta;
import cl.duoc.risani.sosdrink.backend.entities.Usuario;

public interface BoletaServices {

    Optional<Boleta> findByFolioAndClienteRUN(String folio, String clienteRUN);
    List<Boleta> findByClienteRUN(String clienteRUN);
    Boolean boletaExists(String folio);
    Boleta saveUsuarioBoleta(Usuario usuario);
    
}
