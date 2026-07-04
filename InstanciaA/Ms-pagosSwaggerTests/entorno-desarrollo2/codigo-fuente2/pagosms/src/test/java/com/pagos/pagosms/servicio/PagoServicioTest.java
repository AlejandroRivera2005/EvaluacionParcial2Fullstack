package com.pagos.pagosms.servicio;

import com.pagos.pagosms.modelo.PagoModelo;
import com.pagos.pagosms.repositorio.PagoRepositorio;
import com.pagos.pagosms.dto.PagoDTOentrada;
import com.pagos.pagosms.dto.PagoDTOsalida;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagoServicioTest {

      @Mock
    private PagoRepositorio repository;

    @InjectMocks
    private PagoServicio pagoService;

    // ── findAll ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findAll - debe retornar lista de pagos cuando existen registros")
    void debeRetornarListaDeProductos() {
        // Given
        List<PagoModelo> pagosSimulados = List.of(
            new PagoModelo(1L, "Tarjeta de Crédito", "1234 5678 9012 3456", "12/25", "123"),
            new PagoModelo(2L, "Tarjeta de Débito", "9876 5432 1098 7654", "12/26", "456")
        );
        when(repository.findAll()).thenReturn(pagosSimulados);

        // When
        List<PagoDTOsalida> resultado = pagoService.findAll();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Tarjeta de Crédito", resultado.get(0).getMetodo_pago());
        assertEquals("**** **** **** 3456", resultado.get(0).getTarjeta_enmascarada());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - debe retornar lista vacía cuando no hay pagos")
    void debeRetornarListaVaciaSiNoHayPagos() {
        // Given
        when(repository.findAll()).thenReturn(List.of());

        // When
        List<PagoDTOsalida> resultado = pagoService.findAll();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ── findById ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findById - debe retornar el DTO correcto cuando el producto existe")
    void debeRetornarProductoPorId() {
        // Given
        PagoModelo producto = new PagoModelo(1L, "Tarjeta de Crédito", "1234 5678 9012 3456", "12/25", "123");
        when(repository.findById(1L)).thenReturn(Optional.of(producto));

        // When
        PagoDTOsalida resultado = pagoService.findDtoById(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Tarjeta de Crédito", resultado.getMetodo_pago());
        assertEquals("**** **** **** 3456", resultado.getTarjeta_enmascarada());
    }

    @Test
    @DisplayName("findById - debe lanzar RecursoNoEncontradoException cuando el ID no existe")
    void debeLanzarExcepcionCuandoProductoNoExiste() {
        // Given
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () ->
            pagoService.findDtoById(999L)
        );
    }

    // ── crear ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("crear - debe persistir y retornar el producto con ID generado")
    void debeCrearProductoCorrectamente() {
        // Given
        PagoDTOentrada dto = new PagoDTOentrada(
            "Tarjeta de Crédito", "1234 5678 9012 3456", "12/25", "123"
        );
        PagoModelo guardado = new PagoModelo(3L, "Tarjeta de Crédito", "1234 5678 9012 3456", "12/25", "123");
        when(repository.save(any(PagoModelo.class))).thenReturn(guardado);

        // When
        PagoDTOsalida resultado = pagoService.crearPago(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Tarjeta de Crédito", resultado.getMetodo_pago());
        assertEquals("**** **** **** 3456", resultado.getTarjeta_enmascarada());
        verify(repository, times(1)).save(any(PagoModelo.class));
    }

    // ── eliminar ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("eliminar - debe lanzar excepción al intentar eliminar un ID inexistente")
    void debeLanzarExcepcionAlEliminarProductoInexistente() {
        // Given
        when(repository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(RuntimeException.class, () ->
            pagoService.eliminarPago(999L)
        );
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("eliminar - debe invocar deleteById cuando el producto existe")
    void debeEliminarProductoExistente() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);

        // When
        pagoService.eliminarPago(1L);

        // Then
        verify(repository, times(1)).deleteById(1L);
    }

}
