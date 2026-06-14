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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;



@Tag(name = "Trabajos", description = "Operaciones de gestión de ofertas de trabajo")
@RestController
@RequestMapping("/api/trabajos")
public class TrabajoControlador {

    private final TrabajoServicio trabajoservicio;

    public TrabajoControlador(TrabajoServicio trabajoservicio) {
        this.trabajoservicio = trabajoservicio;
    }

     @Operation(summary = "Registrar nuevo trabajo")
    @ApiResponse(responseCode = "201", description = "Trabajo creado exitosamente")
    @PostMapping
    public ResponseEntity<TrabajoDTOsalida> crearTrabajo(@Valid @RequestBody TrabajoDTOentrada dto) {
        TrabajoDTOsalida nuevoTrabajo = trabajoservicio.crearTrabajoDTOsalida(dto);
        return ResponseEntity.ok(nuevoTrabajo);
    }

     @Operation(summary = "Listar todos los trabajos",
               description = "Retorna la lista completa de ofertas de trabajo registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public List<TrabajoDTOsalida> obtenerTodosLosTrabajos() {
        return trabajoservicio.findAllDto();
    }


     @Operation(summary = "Buscar trabajo por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Trabajo encontrado"),
        @ApiResponse(responseCode = "404", description = "Trabajo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TrabajoDTOsalida> obtenerTrabajoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(trabajoservicio.findDtoById(id));
    }


     @Operation(summary = "Actualizar trabajo existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "404", description = "Trabajo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TrabajoDTOsalida> actualizarTrabajo(@PathVariable Long id, @Valid @RequestBody TrabajoDTOentrada dto) {
        try {
            TrabajoDTOsalida trabajoActualizado = trabajoservicio.actualizarTrabajoDTOsalida(id, dto);
            return ResponseEntity.ok(trabajoActualizado);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al actualizar el cargo con id: " + id);
        }
    }

     @Operation(summary = "Eliminar trabajo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Trabajo no encontrado")
    })
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
