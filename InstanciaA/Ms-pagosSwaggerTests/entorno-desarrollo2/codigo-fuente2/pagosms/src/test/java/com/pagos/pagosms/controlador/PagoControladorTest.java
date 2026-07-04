package com.pagos.pagosms.controlador;

import com.pagos.pagosms.servicio.PagoServicio;
import com.pagos.pagosms.dto.PagoDTOsalida;
import com.pagos.pagosms.excepcion.RecursoNoEncontradoException;
import com.pagos.pagosms.excepcion.GlobalExceptionHandler;


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
public class PagoControladorTest {

     @Mock
    private PagoServicio service;

    @InjectMocks
    private PagoControlador controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // ── GET /api/pagos ─────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/pagos - debe retornar 200 con la lista de pagos")
    void debeRetornar200CuandoSePidenPagos() throws Exception {
        // Given
        when(service.findAll()).thenReturn(List.of(
            new PagoDTOsalida(Long.valueOf(1), "Tarjeta de Crédito", "**** **** **** 3456"),
            new PagoDTOsalida(Long.valueOf(2), "Tarjeta de Débito", "**** **** **** 7654")
        ));

        // When & Then
        mockMvc.perform(get("/api/pagos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].metodo_pago").value("Tarjeta de Crédito"))
               .andExpect(jsonPath("$[0].tarjeta_enmascarada").value("**** **** **** 3456"));
    }

    @Test
    @DisplayName("GET /api/pagos - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        // Given
        when(service.findAll()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/pagos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }

    // ── GET /api/pagos/{id} ────────────────────────────────────────────────

   @Test
    @DisplayName("GET /api/pagos/{id} - debe retornar 404 cuando el pago no existe")
    void debeRetornar404CuandoPagoNoExiste() throws Exception {
        // Given
        when(service.findDtoById(999L))
            .thenThrow(new RecursoNoEncontradoException("Pago no encontrado: 999"));

        // When & Then
        mockMvc.perform(get("/api/pagos/999"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value("Pago no encontrado: 999"));
    }

    // ── POST /api/pagos ────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/pagos - debe retornar 201 al crear un pago válido")
    void debeRetornar201AlCrearPago() throws Exception {
        // Given
        String json = """
            {
                "metodo_pago": "Tarjeta de Crédito",
                "numero_tarjeta": "1234 5678 9012 3456",
                "fecha_vencimiento": "12/25",
                "cvv": "123"
            }
            """;
        when(service.crearPago(any())).thenReturn(
            new PagoDTOsalida(Long.valueOf(7), "Tarjeta de Crédito", "**** **** **** 3456")
        );

        // When & Then
        mockMvc.perform(post("/api/pagos")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(7))
               .andExpect(jsonPath("$.metodo_pago").value("Tarjeta de Crédito"))
               .andExpect(jsonPath("$.tarjeta_enmascarada").value("**** **** **** 3456"));
    }

    @Test
    @DisplayName("POST /api/pagos - debe retornar 400 cuando el método de pago es inválido")
    void debeRetornar400CuandoMetodoPagoEsInvalido() throws Exception {
        // Given — método de pago inválido
        String json = """
            {
                "metodo_pago": "",
                "numero_tarjeta": "1234 5678 9012 3456",
                "fecha_vencimiento": "12/25",
                "cvv": "123"
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/pagos")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/pagos - debe retornar 400 cuando el número de tarjeta es inválido")
    void debeRetornar400CuandoNumeroTarjetaEsInvalido() throws Exception {
        // Given — número de tarjeta inválido
        String json = """
            {
                "metodo_pago": "Tarjeta de Crédito",
                "numero_tarjeta": "1234 5678 9012 34567",
                "fecha_vencimiento": "12/25",
                "cvv": "123"
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/pagos")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());
    }

}
