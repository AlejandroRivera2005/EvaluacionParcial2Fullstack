package com.trabajos.trabajosms.servicio;

import com.trabajos.trabajosms.dto.TrabajoDTOentrada;
import com.trabajos.trabajosms.dto.TrabajoDTOsalida;
import com.trabajos.trabajosms.modelo.TrabajoModelo;
import com.trabajos.trabajosms.repositorio.TrabajoRepositorio;
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
class TrabajoServicioTest {
    @Mock
    private TrabajoRepositorio trabajoRepositorio;

    @InjectMocks
    private TrabajoServicio trabajoServicio;

     // ── findAll ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findAll - debe retornar lista de trabajos cuando existen registros")
    void findAllDebeRetornarListaDeTrabajosCuandoExistenRegistros() {
        // Given
        List<TrabajoModelo> trabajos = List.of(
                new TrabajoModelo(1L, "Trabajo 1", "Full-time", "Presencial", "100.00", "Descripción 1"),
                new TrabajoModelo(2L, "Trabajo 2", "Part-time", "Remoto", "50.00", "Descripción 2")
        );
        when(trabajoRepositorio.findAll()).thenReturn(trabajos);

        // When
        List<TrabajoDTOsalida> resultado = trabajoServicio.findAllDto();

        // Then
        assertEquals(2, resultado.size());
        assertEquals("Trabajo 1", resultado.get(0).getCargo());
        assertEquals("Trabajo 2", resultado.get(1).getCargo());
        verify(trabajoRepositorio, times(1)).findAll();

    }

     // ── findById ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findById - debe retornar el DTO correcto cuando el trabajo existe")
    void findByIdDebeRetornarElDTOCorrectoCuandoElTrabajoExiste() {
        // Given
        TrabajoModelo trabajo = new TrabajoModelo(1L, "Trabajo 1", "Full-time", "Presencial", "100.00", "Descripción 1");
        when(trabajoRepositorio.findById(1L)).thenReturn(Optional.of(trabajo));

        // When
        TrabajoDTOsalida resultado = trabajoServicio.findDtoById(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Trabajo 1", resultado.getCargo());
    }

    @Test
    @DisplayName("findById - debe lanzar excepción cuando el trabajo no existe")
    void findByIdDebeLanzarExcepcionCuandoElTrabajoNoExiste() {
        // Given
        when(trabajoRepositorio.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> trabajoServicio.findDtoById(1L));
    }

     // ── crear ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("crear - debe persistir y retornar el trabajo con ID generado")
    void crearDebePersistirYRetornarElTrabajoConIDGenerado() {
        // Given
        TrabajoDTOentrada trabajoDTO = new TrabajoDTOentrada("Trabajo 1", "Full-time", "Presencial", "100.00", "Descripción 1");
        TrabajoModelo trabajo = new TrabajoModelo(1L, "Trabajo 1", "Full-time", "Presencial", "100.00", "Descripción 1");
        when(trabajoRepositorio.save(any(TrabajoModelo.class))).thenReturn(trabajo);

        // When
        TrabajoDTOsalida resultado = trabajoServicio.crearTrabajoDTOsalida(trabajoDTO);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Trabajo 1", resultado.getCargo());
        assertEquals("Full-time", resultado.getJornada());
        assertEquals("Presencial", resultado.getModalidad());
        assertEquals("100.00", resultado.getSalario());
        assertEquals("Descripción 1", resultado.getDescripcion());
        verify(trabajoRepositorio, times(1)).save(any(TrabajoModelo.class));


    }

     // ── eliminar ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("eliminar - debe lanzar excepción al intentar eliminar un ID inexistente")
    void eliminarDebeLanzarExcepcionAlIntentarEliminarUnIDInexistente() {
        // Given
        when(trabajoRepositorio.existsById(1L)).thenReturn(false);

        // When & Then
        assertThrows(RuntimeException.class, () -> trabajoServicio.eliminarTrabajo(1L));
    }

    @Test
    @DisplayName("eliminar - debe invocar deleteById cuando el producto existe")
    void eliminarDebeInvocarDeleteByIdCuandoElProductoExiste() {
        // Given
        when(trabajoRepositorio.existsById(1L)).thenReturn(true);

        // When
        trabajoServicio.eliminarTrabajo(1L);

        // Then
        verify(trabajoRepositorio, times(1)).deleteById(1L);
    }

}
