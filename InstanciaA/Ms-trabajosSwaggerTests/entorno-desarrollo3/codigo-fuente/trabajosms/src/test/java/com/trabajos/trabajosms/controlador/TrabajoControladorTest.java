package com.trabajos.trabajosms.controlador;


import com.trabajos.trabajosms.dto.TrabajoDTOsalida;
import com.trabajos.trabajosms.servicio.TrabajoServicio;
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


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TrabajoControladorTest {
    

    @Mock
    private TrabajoServicio trabajoServicio;

    @InjectMocks
    private TrabajoControlador trabajoControlador;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(trabajoControlador)
                .build();
    }

    // GET -- /api/trabajos
    @Test
    @DisplayName("obtenerTrabajos - debe retornar la lista de trabajos")
    void obtenerTrabajosDebeRetornarListaDeTrabajos() throws Exception {
        // Given
        TrabajoDTOsalida dtoSalida1 = new TrabajoDTOsalida(Long.valueOf(1), "Desarrollador Java", "Full-time", "Presencial", "5000.00", "Desarrollo de aplicaciones Java");
        TrabajoDTOsalida dtoSalida2 = new TrabajoDTOsalida(Long.valueOf(2), "Diseñador UX", "Part-time", "Remoto", "3000.00", "Diseño de interfaces de usuario");

        when(trabajoServicio.findAllDto()).thenReturn(List.of(dtoSalida1, dtoSalida2));

        // When & Then
        mockMvc.perform(get("/api/trabajos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].id").value(1))
               .andExpect(jsonPath("$[0].cargo").value("Desarrollador Java"))
               .andExpect(jsonPath("$[1].id").value(2))
               .andExpect(jsonPath("$[1].cargo").value("Diseñador UX"));
    }

    @Test
    @DisplayName("GET /api/trabajos - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        // Given
        when(trabajoServicio.findAllDto()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/trabajos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }

     // ── GET /api/productos/{id} ────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/trabajos/{id} - debe retornar 404 cuando el trabajo no existe")
    void debeRetornar404CuandoTrabajoNoExiste() throws Exception {
        // Given
        when(trabajoServicio.findDtoById(999L))
            .thenThrow(new RuntimeException("Trabajo no encontrado: 999"));

        // When & Then
        mockMvc.perform(get("/api/trabajos/999"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value("Trabajo no encontrado: 999"));
    }

     // ── POST /api/trabajos ────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/trabajos - debe retornar 201 al crear un trabajo válido")
    void debeRetornar201AlCrearTrabajo() throws Exception {
        // Given
        String json = """
            {
                "cargo": "Desarrollador Java",
                "jornada": "Full-time",
                "modalidad": "Presencial",
                "salario": "5000.00",
                "descripcion": "Desarrollo de aplicaciones Java"
            }
            """;
        when(trabajoServicio.crearTrabajoDTOsalida(any())).thenReturn(
            new TrabajoDTOsalida(Long.valueOf(7), "Desarrollador Java", "Full-time", "Presencial", "5000.00", "Desarrollo de aplicaciones Java")
        );

        // When & Then
        mockMvc.perform(post("/api/trabajos")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(7))
               .andExpect(jsonPath("$.cargo").value("Desarrollador Java"))
               .andExpect(jsonPath("$.jornada").value("Full-time"))
               .andExpect(jsonPath("$.modalidad").value("Presencial"))
               .andExpect(jsonPath("$.salario").value("5000.00"))
               .andExpect(jsonPath("$.descripcion").value("Desarrollo de aplicaciones Java"));
    }

     @Test
    @DisplayName("POST /api/trabajos - debe retornar 400 cuando el cargo está en blanco")
    void debeRetornar400CuandoCargoEstaVacio() throws Exception {
        // Given — cargo vacío y jornada inválida
        String json = """
            {
                "cargo": "",
                "jornada": "Full-time",
                "modalidad": "Presencial",
                "salario": "5000.00",
                "descripcion": "Desarrollo de aplicaciones Java"
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/trabajos")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());
    }

     @Test
    @DisplayName("POST /api/trabajos - debe retornar 400 cuando la jornada es inválida")
    void debeRetornar400CuandoJornadaEsInvalida() throws Exception {
        // Given — cargo válido y jornada inválida
        String json = """
            {
                "cargo": "Desarrollador Java",
                "jornada": "",
                "modalidad": "Presencial",
                "salario": "5000.00",
                "descripcion": "Desarrollo de aplicaciones Java"
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/trabajos")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());
    }
    
}
