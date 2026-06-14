package cl.duoc.backend_check_in_out.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Imports de Swagger 
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import cl.duoc.backend_check_in_out.model.Datos;
import cl.duoc.backend_check_in_out.service.CheckService;

import java.util.List;
import java.util.Optional;

/**
 * @RestController indica que esta clase responderá peticiones web y devolverá datos (como JSON), no pantallas HTML.
 * @RequestMapping("/api/libros") define la URL base para todos los métodos de esta clase.
 */
@Tag(name = "check", description = "Operaciones check in/out")
@RestController
@RequestMapping("/api/habitaciones")
public class CheckControl {

    private final CheckService libroService;

    // Inyección de dependencias: el controlador necesita del servicio para funcionar
    public CheckControl(CheckService libroService) {
        this.libroService = libroService;
    }

    /**
     * CREATE - POST: Se usa para enviar y crear nuevos datos.
     * @RequestBody indica que los datos del libro vendrán en el cuerpo de la petición (en formato JSON).
     */
    @Operation(summary = "Registrar nueva habitacion")
    @ApiResponse(responseCode = "201", description = "Habitacion creada exitosamente")
    @PostMapping
    public Datos crearLibro(@RequestBody Datos libro) {
        return libroService.guardarLibro(libro);
    }

    /**
     * READ ALL - GET: Se usa para solicitar información.
     */
    @Operation(summary = "Listar todas las habitaciones",
               description = "Retorna la lista de todas las habitaciones en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")    
    @GetMapping
    public List<Datos> listarTodos() {
        return libroService.obtenerTodos();
    }

    /**
     * READ BY ID - GET: Solicita información de un elemento específico.
     * @PathVariable captura el número que venga en la URL (ejemplo: /api/libros/1 captura el 1).
     * ResponseEntity permite controlar el código de estado HTTP (200 OK, 404 Not Found, etc.).
     */
    @Operation(summary = "Buscar habitacion por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Habitacion encontrada"),
        @ApiResponse(responseCode = "404", description = "Habitacion no encontrada")
    })    
    @GetMapping("/{id}")
    public ResponseEntity<Datos> obtenerLibroPorId(
        @Parameter(description = "ID unico de habitacion", required = true)
        @PathVariable Long id) {
        Optional<Datos> libro = libroService.obtenerPorId(id);
        if (libro.isPresent()) {
            return ResponseEntity.ok(libro.get()); // Retorna HTTP 200 con el libro
        } else {
            return ResponseEntity.notFound().build(); // Retorna HTTP 404 si no existe
        }
    }

    /**
     * UPDATE - PUT: Se usa para actualizar un registro completo.
     */
    @Operation(summary = "Actualizar habitacion existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "404", description = "Habitacion no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Datos> actualizarLibro(
        @Parameter(description = "ID de la habitacion a actualizar")
        @PathVariable Long id,
        @Valid @RequestBody Datos detallesLibro) {
            Datos libroActualizado = libroService.actualizarLibro(id, detallesLibro);
            if (libroActualizado != null) {
                return ResponseEntity.ok(libroActualizado); // Retorna HTTP 200 con los nuevos datos
            } else {
                return ResponseEntity.notFound().build(); // Retorna HTTP 404
            }
    }

    /**
     * DELETE - DELETE: Se usa para eliminar un registro.
     */
    @Operation(summary = "Eliminar habitacion")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Habitacion no encontrada")
    })    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(
        @Parameter(description = "ID de la habitacion a eliminar")
        @PathVariable Long id) {
            boolean eliminado = libroService.eliminarLibro(id);
            if (eliminado) {
                return ResponseEntity.noContent().build(); // Retorna HTTP 204 (Éxito sin contenido)
            } else {
                return ResponseEntity.notFound().build(); // Retorna HTTP 404
            }
    }
}