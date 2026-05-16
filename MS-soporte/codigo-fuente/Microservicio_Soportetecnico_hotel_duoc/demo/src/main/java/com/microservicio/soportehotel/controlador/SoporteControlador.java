package com.microservicio.soportehotel.controlador;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.soportehotel.dto.SoporteDTOentrada;
import com.microservicio.soportehotel.dto.SoporteDTOsalida;
import com.microservicio.soportehotel.servicio.SoporteService;

import java.util.List;




@RestController
@RequestMapping("/api/soporte")
public class SoporteControlador {

    private final SoporteService soporteService;

    public SoporteControlador(SoporteService soporteService) {
        this.soporteService = soporteService;
    }

    @PostMapping
     public ResponseEntity<SoporteDTOsalida> crearSoporte(
            @Valid @RequestBody SoporteDTOentrada dto) {
        SoporteDTOsalida creado = soporteService.crearSoporte(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public List<SoporteDTOsalida> listadeSolicitudes() {
        return soporteService.findAllDTOsalida();
    }

    @GetMapping("/{id}")
     public ResponseEntity<SoporteDTOsalida> getSoporte(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(soporteService.findDtoById(id));
    }

    
    

    @PutMapping("/{id}")
    public ResponseEntity<SoporteDTOsalida> actualizarDTOsolicitud(@PathVariable long id, @Valid @RequestBody SoporteDTOentrada dto) {
        try {
            SoporteDTOsalida actualizado = soporteService.actualizarDTOsolicitud(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al actualizar la solicitud: ");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSolicitud(@PathVariable @NonNull Long id) {
        boolean eliminado = soporteService.eliminarSolicitud(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    

}
