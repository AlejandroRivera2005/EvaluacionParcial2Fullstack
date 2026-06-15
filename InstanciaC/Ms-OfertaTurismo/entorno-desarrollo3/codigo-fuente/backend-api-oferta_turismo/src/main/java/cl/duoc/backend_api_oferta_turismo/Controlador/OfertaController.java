package cl.duoc.backend_api_oferta_turismo.Controlador;
import cl.duoc.backend_api_oferta_turismo.Dto.OfertaCreateDto;
import cl.duoc.backend_api_oferta_turismo.Dto.OfertaDto;
import cl.duoc.backend_api_oferta_turismo.Modelo.Oferta;
import cl.duoc.backend_api_oferta_turismo.Servicio.OfertaService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
@Tag(name = "Ofertas", description = "Operaciones relacionadas con las ofertas turísticas")
@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

private final OfertaService ofertaService;

    public OfertaController(OfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }

   
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una oferta por ID", description = "Recupera los detalles de una oferta turística específica por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Oferta encontrada"),
            @ApiResponse(responseCode = "404", description = "Oferta no encontrada")
    })
    public ResponseEntity<?> getOferta(@PathVariable Long id) {
        OfertaDto dto = ofertaService.findDtoById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return new ResponseEntity<>("Oferta no encontrada", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Listar todas las ofertas", description = "Recupera una lista de todas las ofertas turísticas disponibles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de ofertas obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<Oferta>> listarOfertas() {
        return new ResponseEntity<>(ofertaService.obtenerTodas(), HttpStatus.OK);
    }

    @Operation(summary = "Crear una nueva oferta", description = "Registra una nueva oferta turística en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Oferta creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la oferta inválidos")
    })
    @PostMapping("/registrar")
    public ResponseEntity<OfertaDto> crearOferta(@Valid @RequestBody OfertaCreateDto dto) {
        OfertaDto creada = ofertaService.registrarNuevaOferta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @Operation(summary = "Actualizar una oferta existente", description = "Actualiza los detalles de una oferta turística existente por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Oferta actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la oferta inválidos"),
            @ApiResponse(responseCode = "404", description = "Oferta no encontrada")
    })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarOferta(@PathVariable Long id, @Valid @RequestBody Oferta ofertaData) {
        try {
            Oferta ofertaActualizada = ofertaService.actualizarPorId(id, ofertaData);
            if (ofertaActualizada != null) {
                return new ResponseEntity<>(ofertaActualizada, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Oferta con ID " + id + " no encontrada.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Eliminar una oferta", description = "Elimina una oferta turística específica por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Oferta eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Oferta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOferta(@PathVariable Long id) {
        boolean eliminado = ofertaService.eliminarPorId(id);
        if (eliminado) {
            return new ResponseEntity<>("Oferta eliminada", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Oferta no encontrada", HttpStatus.NOT_FOUND);
        }
    }
}

