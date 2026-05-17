package cl.duoc.demo.controller;

import cl.duoc.demo.model.Notificaciones;
import cl.duoc.demo.service.NotificacionesService;
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
import java.util.Optional;

/**
 * @RestController indica que esta clase responderá peticiones web y devolverá datos (como JSON), no pantallas HTML.
 * @RequestMapping("/api/libros") define la URL base para todos los métodos de esta clase.
 */
@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionesController {

    private final NotificacionesService libroService;

    // Inyección de dependencias: el controlador necesita del servicio para funcionar
    public NotificacionesController(NotificacionesService libroService) {
        this.libroService = libroService;
    }

    /**
     * CREATE - POST: Se usa para enviar y crear nuevos datos.
     * @RequestBody indica que los datos del libro vendrán en el cuerpo de la petición (en formato JSON).
     */
    @PostMapping
    public Notificaciones crearLibro(@RequestBody Notificaciones libro) {
        return libroService.guardarLibro(libro);
    }

    /**
     * READ ALL - GET: Se usa para solicitar información.
     */
    @GetMapping
    public List<Notificaciones> listarTodos() {
        return libroService.obtenerTodos();
    }

    /**
     * READ BY ID - GET: Solicita información de un elemento específico.
     * @PathVariable captura el número que venga en la URL (ejemplo: /api/libros/1 captura el 1).
     * ResponseEntity permite controlar el código de estado HTTP (200 OK, 404 Not Found, etc.).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Notificaciones> obtenerLibroPorId(@PathVariable Long id) {
        Optional<Notificaciones> libro = libroService.obtenerPorId(id);
        if (libro.isPresent()) {
            return ResponseEntity.ok(libro.get()); // Retorna HTTP 200 con el libro
        } else {
            return ResponseEntity.notFound().build(); // Retorna HTTP 404 si no existe
        }
    }

    /**
     * UPDATE - PUT: Se usa para actualizar un registro completo.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Notificaciones> actualizarLibro(@PathVariable Long id, @RequestBody Notificaciones detallesLibro) {
        Notificaciones libroActualizado = libroService.actualizarLibro(id, detallesLibro);
        if (libroActualizado != null) {
            return ResponseEntity.ok(libroActualizado); // Retorna HTTP 200 con los nuevos datos
        } else {
            return ResponseEntity.notFound().build(); // Retorna HTTP 404
        }
    }

    /**
     * DELETE - DELETE: Se usa para eliminar un registro.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        boolean eliminado = libroService.eliminarLibro(id);
        if (eliminado) {
            return ResponseEntity.noContent().build(); // Retorna HTTP 204 (Éxito sin contenido)
        } else {
            return ResponseEntity.notFound().build(); // Retorna HTTP 404
        }
    }
}