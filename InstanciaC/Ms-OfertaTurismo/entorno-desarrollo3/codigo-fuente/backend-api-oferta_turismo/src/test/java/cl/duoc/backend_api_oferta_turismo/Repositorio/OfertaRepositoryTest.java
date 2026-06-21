package cl.duoc.backend_api_oferta_turismo.Repositorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import cl.duoc.backend_api_oferta_turismo.Modelo.Oferta;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OfertaRepositoryTest {

     @Autowired
    private OfertaRepository repository;

    @Test
    @DisplayName("save - debe persistir la oferta y asignar un ID generado automáticamente")
    void debePersistirOfertaYAsignarIdGenerado() {
// Given
        Oferta oferta = new Oferta();
        oferta.setNombreOferta("Oferta de prueba");
        oferta.setDescripcion("Descripción de prueba");
        oferta.setPrecio(100.0);

// When
        Oferta savedOferta = repository.save(oferta);

// Then
        assertNotNull(savedOferta.getId());
        assertTrue(savedOferta.getId() > 0);
        assertEquals("Oferta de prueba", savedOferta.getNombreOferta());
        assertEquals("Descripción de prueba", savedOferta.getDescripcion());
        assertEquals(100.0, savedOferta.getPrecio());}
    
        @Test
    @DisplayName("findAll - debe retornar todos las ofertas guardadas en la BD")
    void debeRetornarTodosLasOfertasGuardadas() {
        // Given
        Oferta oferta1 = new Oferta(null,"Oferta 1", "Descripción 1", 50.0);
        Oferta oferta2 = new Oferta(null,"Oferta 2", "Descripción 2", 150.0);
        repository.save(oferta1);
        repository.save(oferta2);
        // When
        List<Oferta> ofertas = repository.findAll();
        // Then
        assertEquals(2, ofertas.size());
        assertNotNull(ofertas);
 
    }
    @Test
    @DisplayName("findById - debe retornar la oferta correcta cuando el ID existe")
    void debeEncontrarOfertaPorIdExistente() {
        // Given
        Oferta oferta = new Oferta(null,"Oferta de prueba", "Descripción de prueba", 100.0);
        repository.save(oferta);
        // When
        Optional<Oferta> foundOferta = repository.findById(oferta.getId());
        // Then
        assertTrue(foundOferta.isPresent());
        assertEquals(oferta.getId(), foundOferta.get().getId());
    }
    
    @Test
    @DisplayName("findById - debe retornar Optional vacío cuando el ID no existe")
    void debeRetornarOptionalVacioCuandoIdNoExiste() {
        // When
        Optional<Oferta> foundOferta = repository.findById(999L);
        // Then
        assertFalse(foundOferta.isPresent());
    }
    @Test
    @DisplayName("deleteById - debe eliminar la oferta de la base de datos")
    void debeEliminarOfertaPorId() {
        // Given
        Oferta oferta = new Oferta(null,"Oferta de prueba", "Descripción de prueba", 100.0);
        repository.save(oferta);
        Long id = oferta.getId();
        // When
        repository.deleteById(id);
        assertFalse(repository.findById(id).isPresent());
    }


}

