package com.microservicio.soportehotel.repositorio;

import com.microservicio.soportehotel.soporte_modelo.Soporte;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SoporteRepositorioTest {
    
    @Autowired
    private SoporteRepository soporteRepository;

     @Test
    @DisplayName("save - debe persistir el soporte y asignar un ID generado automáticamente")
    void debePersistirSoporteYAsignarIdGenerado() {
        // Given
        Soporte soporte = new Soporte(null, "Juan Pérez", "juanperez1@gmail.com", "123456789", "Problema con la página web");

        // When
        Soporte guardado = soporteRepository.save(soporte);

        // Then
        assertNotNull(guardado.getId());
        assertTrue(guardado.getId() > 0);
        assertEquals("Juan Pérez", guardado.getNombreCompleto());
        assertEquals("juanperez1@gmail.com", guardado.getCorreo());
        assertEquals("123456789", guardado.getTelefono());
        assertEquals("Problema con la página web", guardado.getDescripcion());
    }

    @Test
    @DisplayName("findAll - debe retornar todos los soportes guardados en la BD")
    void debeRetornarTodosLosSoportesGuardados() {
        // Given
        soporteRepository.save(new Soporte(null, "Juan Pérez", "juanperez1@gmail.com", "123456789", "Problema con la página web"));
        soporteRepository.save(new Soporte(null, "María García", "mariagarcia1@gmail.com", "987654321", "Problema con el servicio de limpieza"));

        // When
        List<Soporte> soportes = soporteRepository.findAll();

        // Then
        assertNotNull(soportes);
        assertEquals(2, soportes.size());
    }

       
    @Test
    @DisplayName("findById - debe retornar el soporte correcto cuando el ID existe")
    void debeEncontrarSoportePorIdExistente() {
        // Given
        Soporte guardado = soporteRepository.save(
            new Soporte(null, "Juan Pérez", "juanperez1@gmail.com", "123456789", "Problema con la página web")
        );

        // When
        Optional<Soporte> resultado = soporteRepository.findById(guardado.getId());

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Juan Pérez", resultado.get().getNombreCompleto());
        assertEquals("juanperez1@gmail.com", resultado.get().getCorreo());
        assertEquals("123456789", resultado.get().getTelefono());
        assertEquals("Problema con la página web", resultado.get().getDescripcion());
    }

    @Test
    @DisplayName("findById - debe retornar Optional vacío cuando el ID no existe")
    void debeRetornarOptionalVacioCuandoIdNoExiste() {
        // When
        Optional<Soporte> resultado = soporteRepository.findById(999L);

        // Then
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("deleteById - debe eliminar el soporte de la base de datos")
    void debeEliminarSoportePorId() {
        // Given
        Soporte guardado = soporteRepository.save(
            new Soporte(null, "Juan Pérez", "juanperez1@gmail.com", "123456789", "Problema con la página web")
        );
        Long id = guardado.getId();

        // When
        soporteRepository.deleteById(id);

        // Then
        assertFalse(soporteRepository.findById(id).isPresent());
    }

}
