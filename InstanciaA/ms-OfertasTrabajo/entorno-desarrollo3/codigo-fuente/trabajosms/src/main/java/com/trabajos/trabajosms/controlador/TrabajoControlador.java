package com.trabajos.trabajosms.controlador;
import com.trabajos.trabajosms.servicio.TrabajoServicio;
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
import com.trabajos.trabajosms.dto.TrabajoDTOentrada;
import com.trabajos.trabajosms.dto.TrabajoDTOsalida;
import java.util.List;




@RestController
@RequestMapping("/api/trabajos")
public class TrabajoControlador {

    private final TrabajoServicio trabajoservicio;

    public TrabajoControlador(TrabajoServicio trabajoservicio) {
        this.trabajoservicio = trabajoservicio;
    }

    @PostMapping
    public ResponseEntity<TrabajoDTOsalida> crearTrabajo(@Valid @RequestBody TrabajoDTOentrada dto) {
        TrabajoDTOsalida nuevoTrabajo = trabajoservicio.crearTrabajoDTOsalida(dto);
        return ResponseEntity.ok(nuevoTrabajo);
    }

    @GetMapping
    public List<TrabajoDTOsalida> obtenerTodosLosTrabajos() {
        return trabajoservicio.findAllDto();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrabajoDTOsalida> obtenerTrabajoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(trabajoservicio.findDtoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrabajoDTOsalida> actualizarTrabajo(@PathVariable Long id, @Valid @RequestBody TrabajoDTOentrada dto) {
        try {
            TrabajoDTOsalida trabajoActualizado = trabajoservicio.actualizarTrabajoDTOsalida(id, dto);
            return ResponseEntity.ok(trabajoActualizado);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al actualizar el cargo con id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTrabajo(@PathVariable Long id) {
        boolean eliminado = trabajoservicio.eliminarTrabajo(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            throw new RuntimeException("Error al eliminar el cargo con id: " + id);
        }
    }
        

    
}
