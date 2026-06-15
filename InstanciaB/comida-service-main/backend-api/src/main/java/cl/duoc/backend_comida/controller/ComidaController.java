package cl.duoc.backend_comida.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.backend_comida.model.Comida;
import cl.duoc.backend_comida.service.ComidaService;

import java.util.List;
import java.util.Optional;

// Imports de Swagger (agregar estos)
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * @RestController indica que esta clase responderá peticiones web y devolverá datos (como JSON), no pantallas HTML.
 * @RequestMapping("/api/libros") define la URL base para todos los métodos de esta clase.
 */
@RestController
@RequestMapping("/api/comida")
public class ComidaController {

    private final ComidaService libroService;

    // Inyección de dependencias: el controlador necesita del servicio para funcionar
    public ComidaController(ComidaService libroService) {
        this.libroService = libroService;
    }

    /**
     * CREATE - POST: Se usa para enviar y crear nuevos datos.
     * @RequestBody indica que los datos del libro vendrán en el cuerpo de la petición (en formato JSON).
     */
    @Operation(summary = "Registrar nueva comida")
    @ApiResponse(responseCode = "201", description = "Comida registrada exitosamente")    
    @PostMapping
    public Comida crearLibro(@RequestBody Comida libro) {
        return libroService.guardarLibro(libro);
    }

    /**
     * READ ALL - GET: Se usa para solicitar información.
     */
    @Operation(summary = "Listar todas las comidas",
               description = "Retorna la lista completa de comidas registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public List<Comida> listarTodos() {
        return libroService.obtenerTodos();
    }

    /**
     * READ BY ID - GET: Solicita información de un elemento específico.
     * @PathVariable captura el número que venga en la URL (ejemplo: /api/libros/1 captura el 1).
     * ResponseEntity permite controlar el código de estado HTTP (200 OK, 404 Not Found, etc.).
     */
    @Operation(summary = "Buscar comida por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Comida encontrada"),
        @ApiResponse(responseCode = "404", description = "Comida no encontrada")
    })    
    @GetMapping("/{id}")
    public ResponseEntity<Comida> obtenerLibroPorId(
            @Parameter(description = "ID único de comida", required = true)
            @PathVariable Long id) {
        Optional<Comida> libro = libroService.obtenerPorId(id);
        if (libro.isPresent()) {
            return ResponseEntity.ok(libro.get()); // Retorna HTTP 200 con el libro
        } else {
            return ResponseEntity.notFound().build(); // Retorna HTTP 404 si no existe
        }
    }

    /**
     * UPDATE - PUT: Se usa para actualizar un registro completo.
     */
    @Operation(summary = "Actualizar comida")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "404", description = "Comida no encontrada")
    }) 
    @PutMapping("/{id}")
    public ResponseEntity<Comida> actualizarLibro(
            @Parameter(description = "ID de la mascota a actualizar")
            @PathVariable Long id, 
            @Valid @RequestBody Comida detallesLibro) {
        Comida libroActualizado = libroService.actualizarLibro(id, detallesLibro);
        if (libroActualizado != null) {
            return ResponseEntity.ok(libroActualizado); // Retorna HTTP 200 con los nuevos datos
        } else {
            return ResponseEntity.notFound().build(); // Retorna HTTP 404
        }
    }

    /**
     * DELETE - DELETE: Se usa para eliminar un registro.
     */
    @Operation(summary = "Eliminar comida")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Comida no encontrada")
    })    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(
            @Parameter(description = "ID de la comida a eliminar")
            @PathVariable Long id) {
        boolean eliminado = libroService.eliminarLibro(id);
        if (eliminado) {
            return ResponseEntity.noContent().build(); // Retorna HTTP 204 (Éxito sin contenido)
        } else {
            return ResponseEntity.notFound().build(); // Retorna HTTP 404
        }
    }
}