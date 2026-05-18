package cl.duoc.backend_api_oferta_turismo.Controlador;
import cl.duoc.backend_api_oferta_turismo.Dto.OfertaCreateDto;
import cl.duoc.backend_api_oferta_turismo.Dto.OfertaDto;
import cl.duoc.backend_api_oferta_turismo.Modelo.Oferta;
import cl.duoc.backend_api_oferta_turismo.Servicio.OfertaService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

private final OfertaService ofertaService;

    public OfertaController(OfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<?> getOferta(@PathVariable Long id) {
        OfertaDto dto = ofertaService.findDtoById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return new ResponseEntity<>("Oferta no encontrada", HttpStatus.NOT_FOUND);
    }

   
    @GetMapping
    public ResponseEntity<List<Oferta>> listarOfertas() {
        return new ResponseEntity<>(ofertaService.obtenerTodas(), HttpStatus.OK);
    }

   
    @PostMapping("/registrar")
    public ResponseEntity<OfertaDto> crearOferta(@Valid @RequestBody OfertaCreateDto dto) {
        OfertaDto creada = ofertaService.registrarNuevaOferta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    
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

