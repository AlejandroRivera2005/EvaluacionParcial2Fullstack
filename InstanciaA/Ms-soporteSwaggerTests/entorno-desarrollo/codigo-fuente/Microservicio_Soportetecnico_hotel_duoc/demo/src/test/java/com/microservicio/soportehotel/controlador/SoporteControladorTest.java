package com.microservicio.soportehotel.controlador;

import com.microservicio.soportehotel.dto.SoporteDTOsalida;
import com.microservicio.soportehotel.servicio.SoporteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.microservicio.soportehotel.excepcion.RecursoNoEncontradoException;
import com.microservicio.soportehotel.excepcion.GlobalExceptionHandler;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class SoporteControladorTest {

@Mock
    private SoporteService service;

    @InjectMocks
    private SoporteControlador controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // ── GET /api/soporte ─────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/soporte - debe retornar 200 con la lista de productos")
    void debeRetornar200CuandoSePidenProductos() throws Exception {
        // Given
        when(service.findAllDTOsalida()).thenReturn(List.of(
            new SoporteDTOsalida(1L, "Juan Pérez", "juanperez1@gmail.com", "123456789", "Problema con la página web"),
            new SoporteDTOsalida(2L, "María García", "mariagarcia1@gmail.com", "987654321", "Problema con la página web")
        ));

        // When & Then
        mockMvc.perform(get("/api/soporte"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].id").value(1))
               .andExpect(jsonPath("$[0].nombreCompleto").value("Juan Pérez"))
               .andExpect(jsonPath("$[0].correo").value("juanperez1@gmail.com"))
               .andExpect(jsonPath("$[0].telefono").value("123456789"))
               .andExpect(jsonPath("$[0].descripcion").value("Problema con la página web"))
               .andExpect(jsonPath("$[1].id").value(2))
               .andExpect(jsonPath("$[1].nombreCompleto").value("María García"))
               .andExpect(jsonPath("$[1].correo").value("mariagarcia1@gmail.com"))
               .andExpect(jsonPath("$[1].telefono").value("987654321"))
               .andExpect(jsonPath("$[1].descripcion").value("Problema con la página web"));
    }

    @Test
    @DisplayName("GET /api/soporte - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        // Given
        when(service.findAllDTOsalida()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/soporte"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }

    // ── GET /api/soporte/{id} ────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/soporte/{id} - debe retornar 404 cuando el soporte no existe")
    void debeRetornar404CuandoSoporteNoExiste() throws Exception {
        // Given
        when(service.findDtoById(999L))
            .thenThrow(new RecursoNoEncontradoException("Soporte no encontrado: 999"));

        // When & Then
        mockMvc.perform(get("/api/soporte/999"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value("Soporte no encontrado: 999"));
    }

    // ── POST /api/soporte ────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/soporte - debe retornar 201 al crear un soporte válido")
    void debeRetornar201AlCrearSoporte() throws Exception {
        // Given
        String json = """
            {
                "nombreCompleto": "Juan Pérez",
                "correo": "juanperez1@gmail.com",
                "telefono": "123456789",
                "descripcion": "Problema con la página web"
            }
            """;
        when(service.crearSoporte(any())).thenReturn(
            new SoporteDTOsalida(7L, "Juan Pérez", "juanperez1@gmail.com", "123456789", "Problema con la página web")
        );

        // When & Then
        mockMvc.perform(post("/api/soporte")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(7))
               .andExpect(jsonPath("$.nombreCompleto").value("Juan Pérez"))
               .andExpect(jsonPath("$.correo").value("juanperez1@gmail.com"))
               .andExpect(jsonPath("$.telefono").value("123456789"))
               .andExpect(jsonPath("$.descripcion").value("Problema con la página web"));
    }

    @Test
    @DisplayName("POST /api/soporte - debe retornar 400 cuando el nombre está en blanco")
    void debeRetornar400CuandoNombreEstaVacio() throws Exception {
        // Given — nombre vacío y precio negativo
        String json = """
            {
                "nombreCompleto": "",
                "correo": "juanperez1@gmail.com",
                "telefono": "123456789",
                "descripcion": "Problema con la página web"
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/soporte")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());
    }
}
