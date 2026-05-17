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




@RestController
@RequestMapping("/api/pagos")
public class PagoControlador {
    private final PagoServicio pagoServicio;

    public PagoControlador(PagoServicio pagoServicio) {
        this.pagoServicio = pagoServicio;
    }

    @PostMapping
    public ResponseEntity<PagoDTOsalida> crearPago(@Valid @RequestBody PagoDTOentrada dto){
        PagoDTOsalida creado = pagoServicio.crearPago(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping
    public List<PagoDTOsalida> obtenerPagos() {
        return pagoServicio.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PagoDTOsalida> obtenerPagoPorID(@PathVariable Long id) {
        return ResponseEntity.ok(pagoServicio.findDtoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoDTOsalida> actualizarPago(@PathVariable Long id, @Valid @RequestBody PagoDTOentrada dto){
        try {
            PagoDTOsalida actualizado = pagoServicio.actualizarPago(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al actualizar el método de pago");
        }

    }

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


