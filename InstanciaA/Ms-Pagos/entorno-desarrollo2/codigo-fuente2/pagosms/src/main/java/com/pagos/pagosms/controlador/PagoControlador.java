package com.pagos.pagosms.controlador;
import com.pagos.pagosms.dto.PagoDTOentrada;
import com.pagos.pagosms.dto.PagoDTOsalida;
import com.pagos.pagosms.servicio.PagoServicio;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Pagos", description = "Operaciones de gestión de pagos")
@RestController
@RequestMapping("/api/pagos")
public class PagoControlador {
    private final PagoServicio pagoServicio;

    public PagoControlador(PagoServicio pagoServicio) {
        this.pagoServicio = pagoServicio;
    }

    @Operation(summary = "Registrar nuevo método de pago")
    @ApiResponse(responseCode = "201", description = "Método de pago creado exitosamente")
    @PostMapping
    public ResponseEntity<PagoDTOsalida> crearPago(@Valid @RequestBody PagoDTOentrada dto){
        PagoDTOsalida creado = pagoServicio.crearPago(dto);
        return ResponseEntity.ok(creado);
    }

    @Operation(summary = "Listar todos los métodos de pago",
               description = "Retorna la lista completa de métodos de pago registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public List<PagoDTOsalida> obtenerPagos() {
        return pagoServicio.findAll();
    }
    
     @Operation(summary = "Buscar método de pago por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Método de pago encontrado"),
        @ApiResponse(responseCode = "404", description = "Método de pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoDTOsalida> obtenerPagoPorID(@PathVariable Long id) {
        return ResponseEntity.ok(pagoServicio.findDtoById(id));
    }


     @Operation(summary = "Actualizar método de pago existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "404", description = "Método de pago no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PagoDTOsalida> actualizarPago(@PathVariable Long id, @Valid @RequestBody PagoDTOentrada dto){
        try {
            PagoDTOsalida actualizado = pagoServicio.actualizarPago(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al actualizar el método de pago");
        }

    }

     @Operation(summary = "Eliminar método de pago")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Método de pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        boolean eliminado = pagoServicio.eliminarPago(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            throw new RuntimeException("Error al eliminar el método de pago");
        }
    }
    

    

}


