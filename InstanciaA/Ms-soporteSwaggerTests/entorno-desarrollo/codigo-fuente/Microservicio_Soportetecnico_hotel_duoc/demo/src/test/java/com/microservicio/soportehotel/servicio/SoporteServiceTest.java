package com.microservicio.soportehotel.servicio;

import com.microservicio.soportehotel.dto.SoporteDTOentrada;
import com.microservicio.soportehotel.dto.SoporteDTOsalida;
import com.microservicio.soportehotel.soporte_modelo.Soporte;
import com.microservicio.soportehotel.repositorio.SoporteRepository;

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
class SoporteServiceTest {
     @Mock
    private SoporteRepository repository;

    @InjectMocks
    private SoporteService soporteService;

    // ── findAll ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findAll - debe retornar lista de productos cuando existen registros")
    void debeRetornarListaDeProductos() {
        // Given
        List<Soporte> productosSimulados = List.of(
            new Soporte(1L, "Juan Pérez", "juanperez1@gmail.com", "123456789", "Problema con la página web"),
            new Soporte(2L, "Juan Pérez", "juanperez1@gmail.com", "987654321", "Problema con la página web")
        );
        when(repository.findAll()).thenReturn(productosSimulados);

        // When
        List<SoporteDTOsalida> resultado = soporteService.findAllDTOsalida();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombreCompleto());
        assertEquals("juanperez1@gmail.com", resultado.get(0).getCorreo());
        assertEquals("123456789", resultado.get(0).getTelefono());
        assertEquals("Problema con la página web", resultado.get(0).getDescripcion());
    }

    @Test
    @DisplayName("findAll - debe retornar lista vacía cuando no hay productos")
    void debeRetornarListaVaciaSiNoHayProductos() {
        // Given
        when(repository.findAll()).thenReturn(List.of());

        // When
        List<SoporteDTOsalida> resultado = soporteService.findAllDTOsalida();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ── findById ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findById - debe retornar el DTO correcto cuando el soporte existe")
    void debeRetornarProductoPorId() {
        // Given
        Soporte soporte = new Soporte(1L, "Juan Pérez", "juanperez1@gmail.com", "123456789", "Problema con la conexión Wi-Fi");
        when(repository.findById(1L)).thenReturn(Optional.of(soporte));

        // When
        SoporteDTOsalida resultado = soporteService.findDtoById(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombreCompleto());
        assertEquals("juanperez1@gmail.com", resultado.getCorreo());
        assertEquals("123456789", resultado.getTelefono());
        assertEquals("Problema con la conexión Wi-Fi", resultado.getDescripcion());
    }

    @Test
    @DisplayName("findById - debe lanzar excepción cuando el trabajo no existe")
    void findByIdDebeLanzarExcepcionCuandoElTrabajoNoExiste() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> soporteService.findDtoById(1L));
    }

    // ── crear ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("crear - debe persistir y retornar el soporte con ID generado")
    void debeCrearSoporteCorrectamente() {
        // Given
        SoporteDTOentrada dto = new SoporteDTOentrada(
            "Juan Pérez", "juanperez1@gmail.com", "123456789", "Problema con la página web"
        );
        Soporte guardado = new Soporte(3L, "Juan Pérez", "juanperez1@gmail.com", "123456789", "Problema con la página web");
        when(repository.save(any(Soporte.class))).thenReturn(guardado);

        // When
        SoporteDTOsalida resultado = soporteService.crearSoporte(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombreCompleto());
        assertEquals("juanperez1@gmail.com", resultado.getCorreo());
        assertEquals("123456789", resultado.getTelefono());
        assertEquals("Problema con la página web", resultado.getDescripcion());
    }

     // ── eliminar ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("eliminar - debe lanzar excepción al intentar eliminar un ID inexistente")
    void debeLanzarExcepcionAlEliminarProductoInexistente() {
        // Given
        when(repository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(RuntimeException.class, () -> soporteService.eliminarSolicitud(999L));
    }

    @Test
    @DisplayName("eliminar - debe invocar deleteById cuando el soporte existe")
    void debeEliminarProductoExistente() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);

        // When
        soporteService.eliminarSolicitud(1L);

        // Then
        verify(repository, times(1)).deleteById(1L);
    }
   
}


