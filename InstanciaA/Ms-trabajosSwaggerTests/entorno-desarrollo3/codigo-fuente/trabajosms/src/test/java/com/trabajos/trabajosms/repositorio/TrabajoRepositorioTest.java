package com.trabajos.trabajosms.repositorio;


import com.trabajos.trabajosms.modelo.TrabajoModelo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TrabajoRepositorioTest {

    @Autowired
    private TrabajoRepositorio repository;

    @Test
    @DisplayName("save - debe persistir el trabajo y asignar un ID generado automáticamente")
    void debePersistirTrabajoYAsignarIdGenerado() {
        // Given
        TrabajoModelo trabajo = new TrabajoModelo(null, "Trabajo de ejemplo", "Full-time", "Presencial", "100.00", "Descripción del trabajo");

        // When
        TrabajoModelo guardado = repository.save(trabajo);

        // Then
        assertNotNull(guardado.getId());
        assertTrue(guardado.getId() > 0);
        assertEquals("Trabajo de ejemplo", guardado.getCargo());
        assertEquals("Full-time", guardado.getJornada());
    }

    @Test
    @DisplayName("findAll - debe retornar todos los productos guardados en la BD")
    void debeRetornarTodosLosProductosGuardados() {
        // Given
        repository.save(new TrabajoModelo(null, "Trabajo de ejemplo", "Full-time", "Presencial", "100.00", "Descripción del trabajo"));
        repository.save(new TrabajoModelo(null, "Trabajo de ejemplo 2", "Part-time", "Remoto", "150.00", "Descripción del trabajo 2"));

        // When
        List<TrabajoModelo> trabajos = repository.findAll();

        // Then
        assertNotNull(trabajos);
        assertEquals(2, trabajos.size());
    }

    @Test
    @DisplayName("findById - debe retornar el producto correcto cuando el ID existe")
    void debeEncontrarProductoPorIdExistente() {
        // Given
        TrabajoModelo guardado = repository.save(
            new TrabajoModelo(null, "Trabajo de ejemplo", "Full-time", "Presencial", "100.00", "Descripción del trabajo")
        );

        // When
        Optional<TrabajoModelo> resultado = repository.findById(guardado.getId());

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Trabajo de ejemplo", resultado.get().getCargo());
        assertEquals("Full-time", resultado.get().getJornada());
    }

    @Test
    @DisplayName("findById - debe retornar Optional vacío cuando el ID no existe")
    void debeRetornarOptionalVacioCuandoIdNoExiste() {
        // When
        Optional<TrabajoModelo> resultado = repository.findById(999L);

        // Then
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("deleteById - debe eliminar el trabajo de la base de datos")
    void debeEliminarTrabajoPorId() {
        // Given
        TrabajoModelo guardado = repository.save(
            new TrabajoModelo(null, "Trabajo de ejemplo", "Full-time", "Presencial", "100.00", "Descripción del trabajo")
        );
        Long id = guardado.getId();

        // When
        repository.deleteById(id);

        // Then
        assertFalse(repository.findById(id).isPresent());
    }
}