package cl.duoc.risani.sosdrink.backend.services;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.risani.sosdrink.backend.entities.Boleta;
import cl.duoc.risani.sosdrink.backend.entities.FolioGenerator;
import cl.duoc.risani.sosdrink.backend.entities.ItemCarrito;
import cl.duoc.risani.sosdrink.backend.entities.LineaBoleta;
import cl.duoc.risani.sosdrink.backend.entities.Producto;
import cl.duoc.risani.sosdrink.backend.entities.Usuario;
import cl.duoc.risani.sosdrink.backend.repository.BoletaRepository;
import cl.duoc.risani.sosdrink.backend.repository.FolioGeneratorRepository;
import jakarta.transaction.Transactional;

@Service
public class BoletaServicesImpl implements BoletaServices {

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private FolioGeneratorRepository folioGeneratorRepository;

    @Override
    public Optional<Boleta> findByFolioAndClienteRUN(String folio, String clienteRUN) {
        return boletaRepository.findByFolioAndClienteRun(folio, clienteRUN);
    }

    @Override
    public List<Boleta> findByClienteRUN(String clienteRUN) {
        return boletaRepository.findByClienteRun(clienteRUN);
    }

    @Override
    public Boolean boletaExists(String folio) {
        return boletaRepository.existsByFolio(folio);
    }

    @Override
    @Transactional
    public Boleta saveUsuarioBoleta(Usuario usuario) {
        List<ItemCarrito> carrito = usuario.getCarrito();
        if (carrito.size() == 0) throw new IllegalArgumentException("Carrito vacio.");

        List<LineaBoleta> lineasBoleta = new ArrayList<>();
        Integer subtotal = 0;
        Integer iva = 0;

        for (ItemCarrito item : carrito) {
            Producto producto = item.getProducto();
            subtotal += producto.getValor() * item.getCantidad();
            iva += producto.getIva() * item.getCantidad();

            LineaBoleta linea = new LineaBoleta();
            linea.setCantidad(item.getCantidad());
            linea.setDetalle(producto.getDetalle());
            linea.setValorUnitario(producto.getValor());
            linea.setIvaUnitario(producto.getIva());

            lineasBoleta.add(linea);
        }

        Boleta boleta = new Boleta();

        boleta.setFolio(obtenerNuevoFolio());
        boleta.setFechaEmision(LocalDateTime.now());
        boleta.setClienteRun(usuario.getRun());
        boleta.setClienteNombre(usuario.getNombre() + " " + usuario.getApellidos());
        boleta.setSubtotal(subtotal);
        boleta.setIva(iva);
        boleta.setLineasBoleta(lineasBoleta);

        return boletaRepository.save(boleta);
        
    }
    
    @Transactional
    private String obtenerNuevoFolio() {
        Integer year = Year.now().getValue();
        Optional<FolioGenerator> generatorOptional = folioGeneratorRepository.findByYear(year);
        FolioGenerator generator = generatorOptional.orElse(new FolioGenerator(year, 1));

        Integer currentNumber = generator.getCurrentNumber();
        generator.setCurrentNumber(currentNumber + 1);

        folioGeneratorRepository.save(generator);
        return year + "-" + String.format("%05d", currentNumber);
    }

}
