package com.pagos.pagosms.repositorio;

import com.pagos.pagosms.modelo.PagoModelo;

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
public class PagoRepositorioTest {
    
    
    @Autowired
    private PagoRepositorio repository;

    @Test
    @DisplayName("save - debe persistir el pago y asignar un ID generado automáticamente")
    void debePersistirPagoYAsignarIdGenerado() {
        // Given
        PagoModelo pago = new PagoModelo(null, "Tarjeta de Crédito", "1234 5678 9012 3456", "12/25", "123");

        // When
        PagoModelo guardado = repository.save(pago);

        // Then
        assertNotNull(guardado.getId());
        assertTrue(guardado.getId() > 0);
        assertEquals("Tarjeta de Crédito", guardado.getMetodo_pago());
        assertEquals("1234 5678 9012 3456", guardado.getNumero_tarjeta());
        assertEquals("12/25", guardado.getFecha_vencimiento());
        assertEquals("123", guardado.getCvv());
    }

    @Test
    @DisplayName("findAll - debe retornar todos los pagos guardados en la BD")
    void debeRetornarTodosLosPagosGuardados() {
        // Given
        repository.save(new PagoModelo(null, "Tarjeta de Crédito", "1234 5678 9012 3456", "12/25", "123"));
        repository.save(new PagoModelo(null, "Tarjeta de Débito", "9876 5432 1098 7654", "12/26", "456"));

        // When
        List<PagoModelo> pagos = repository.findAll();

        // Then
        assertNotNull(pagos);
        assertEquals(2, pagos.size());
    }

    @Test
    @DisplayName("findById - debe retornar el pago correcto cuando el ID existe")
    void debeEncontrarPagoPorIdExistente() {
        // Given
        PagoModelo guardado = repository.save(
            new PagoModelo(null, "Tarjeta de Crédito", "1234 5678 9012 3456", "12/25", "123")
        );

        // When
        Optional<PagoModelo> resultado = repository.findById(guardado.getId());

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Tarjeta de Crédito", resultado.get().getMetodo_pago());
        assertEquals("1234 5678 9012 3456", resultado.get().getNumero_tarjeta());
        assertEquals("12/25", resultado.get().getFecha_vencimiento());
        assertEquals("123", resultado.get().getCvv());
    }

    @Test
    @DisplayName("findById - debe retornar Optional vacío cuando el ID no existe")
    void debeRetornarOptionalVacioCuandoIdNoExiste() {
        // When
        Optional<PagoModelo> resultado = repository.findById(999L);

        // Then
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("deleteById - debe eliminar el pago de la base de datos")
    void debeEliminarPagoPorId() {
        // Given
        PagoModelo guardado = repository.save(
            new PagoModelo(null, "Tarjeta de Crédito", "1234 5678 9012 3456", "12/25", "123")
        );
        Long id = guardado.getId();

        // When
        repository.deleteById(id);

        // Then
        assertFalse(repository.findById(id).isPresent());
    }

}
