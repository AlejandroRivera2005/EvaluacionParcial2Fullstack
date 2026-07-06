package cl.duoc.backen_api_inventario.Controller;
import cl.duoc.backen_api_inventario.Dto.*;
import cl.duoc.backen_api_inventario.exception.*;
import cl.duoc.backen_api_inventario.Service.*;
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
public class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
    }
    
    // ── GET /api/productos ─────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/productos - debe retornar 200 con la lista de productos")
    void debeRetornar200CuandoSePidenProductos() throws Exception {
        // Arrange
      when(productoService.obtenerTodosDto()).thenReturn(List.of(
                new ProductoDto(1L, "Producto 1", "Categoría 1", 10, 100.0),
                new ProductoDto(2L, "Producto 2", "Categoría 2", 5, 200.0)
        ));

        // Act & Assert
       mockMvc.perform(get("/api/productos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].nombre").value("Producto 1"))
               .andExpect(jsonPath("$[0].precio").value(100));
    }

    @Test
    @DisplayName("GET /api/productos - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        // Arrange
        when(productoService.obtenerTodosDto()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/api/productos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
    // ── GET /api/productos/{id} ────────────────────────────────────────────────

 @Test
    @DisplayName("GET /api/productos/{id} - debe retornar 404 cuando el producto no existe")
    void debeRetornar404CuandoProductoNoExiste() throws Exception {
        // Given
        when(productoService.findById(999L))
            .thenThrow(new RecursoNoEncontradoException("Producto no encontrado: 999"));

        // When & Then
        mockMvc.perform(get("/api/productos/999")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound())
    
               .andExpect(jsonPath("$.message").value("Producto no encontrado: 999"));
    }
     // ── POST /api/productos ────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/productos - debe retornar 201 al crear un producto válido")
    void debeRetornar201AlCrearProducto() throws Exception {
        // Given
        String json = """
                {
                    "nombre": "Producto Test",
                    "categoria": "Categoría Test",
                    "stock": 10,
                    "precio": 100.0
                }
                """;
                when(productoService.crear(any())).thenReturn(
                        new ProductoDto(1L, "Producto Test", "Categoría Test", 10, 100.0)
                );
                
        // When & Then
        mockMvc.perform(post("/api/productos/registrar")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.nombre").value("Producto Test"));
    }
   @Test
    @DisplayName("POST /api/productos - debe retornar 400 cuando el nombre está en blanco")
    void debeRetornar400CuandoNombreEstaVacio() throws Exception {
        // Given — nombre vacío y precio negativo
        String json = """
                {
                    "nombre": "",
                    "categoria": "Categoría Test",
                    "stock": 10,
                    "precio": 100.0
                }
                """;
        // When & Then
        mockMvc.perform(post("/api/productos/registrar")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());}
}


