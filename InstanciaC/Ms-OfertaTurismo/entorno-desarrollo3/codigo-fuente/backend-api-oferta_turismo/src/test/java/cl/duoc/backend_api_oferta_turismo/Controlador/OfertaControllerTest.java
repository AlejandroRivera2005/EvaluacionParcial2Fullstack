package cl.duoc.backend_api_oferta_turismo.Controlador;
import cl.duoc.backend_api_oferta_turismo.Dto.*;
import cl.duoc.backend_api_oferta_turismo.Exception.*;
import cl.duoc.backend_api_oferta_turismo.Servicio.*;
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
public class OfertaControllerTest {

    @Mock
    private OfertaService ofertaService;

    @InjectMocks
    private OfertaController ofertaController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ofertaController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    
    @Test
    @DisplayName("GET /api/ofertas - debe retornar 200 con la lista de ofertas")
    void debeRetornar200CuandoSePidenOfertas() throws Exception {
        // Given
        OfertaDto oferta1 = new OfertaDto(1L, "Oferta 1", "Descripción 1", 50.0);
        OfertaDto oferta2 = new OfertaDto(2L, "Oferta 2", "Descripción 2", 150.0);
        when(ofertaService.findAll()).thenReturn(List.of(oferta1, oferta2));

        // When & Then
        mockMvc.perform(get("/api/ofertas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombreOferta").value("Oferta 1"))
                .andExpect(jsonPath("$[1].nombreOferta").value("Oferta 2"));

    }
    @Test
    @DisplayName("GET /api/ofertas - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        // Given
        when(ofertaService.findAll()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/ofertas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
    @Test
    @DisplayName("GET /api/ofertas - debe retornar 404 cuando la oferta no existe")
    void debeRetornar404CuandoOfertaNoExiste() throws Exception {
        // Given
        when(ofertaService.findById(999L)).thenThrow(new RecursoNoEncontradoException("Oferta no encontrada: 999"));
         // When & Then
          // When & Then
        mockMvc.perform(get("/api/ofertas/999"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value("Oferta no encontrada: 999"));
    }

    @Test
    @DisplayName("POST /api/ofertas - debe retornar 201 al crear una oferta válida")
    void debeRetornar201AlCrearOferta() throws Exception {
        // Given
        String json = """
                {
                    "nombreOferta": "Nueva Oferta",
                    "descripcion": "Descripción de la nueva oferta",
                    "precio": 200.0
                }
                """;
                when(ofertaService.registrarNuevaOferta(any(OfertaCreateDto.class))).thenReturn
                (new OfertaDto(1L, "Nueva Oferta", "Descripción de la nueva oferta"
                , 200.0));
                // When & Then
                  mockMvc.perform(post("/api/ofertas")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.nombreOferta").value("Nueva Oferta"));

    }

    @Test
    @DisplayName("POST /api/ofertas - debe retornar 400 cuando el nombre está en blanco")
    void debeRetornar400CuandoNombreEstaVacio() throws Exception {
        // Given — nombre vacío y precio negativo
        String json = """
                {
                    "nombreOferta": "",
                    "descripcion": "Descripción de la oferta",
                    "precio": -50.0
                }
                """;
        // When & Then
    mockMvc.perform(post("/api/ofertas")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());
    }
}