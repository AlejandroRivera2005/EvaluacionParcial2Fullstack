package com.trabajos.trabajosms.servicio;
import com.trabajos.trabajosms.dto.TrabajoDTOentrada;
import com.trabajos.trabajosms.dto.TrabajoDTOsalida;
import com.trabajos.trabajosms.excepcion.RecursoNoEncontradoException;
import com.trabajos.trabajosms.modelo.TrabajoModelo;
import com.trabajos.trabajosms.repositorio.TrabajoRepositorio;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrabajoServicio {
    private final TrabajoRepositorio trabajorepositorio;

    public TrabajoServicio(TrabajoRepositorio trabajorepositorio) {
        this.trabajorepositorio = trabajorepositorio;
    }

   public TrabajoDTOsalida findDtoById(Long id) {
        TrabajoModelo s = trabajorepositorio.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Cargo no encontrado con id: " + id));

        return new TrabajoDTOsalida(
            id, 
            s.getCargo(), 
            s.getJornada(), 
            s.getModalidad(), 
            s.getSalario(), 
            s.getDescripcion());
   }

   public List<TrabajoDTOsalida> findAllDto() {
        List<TrabajoModelo> trabajos = trabajorepositorio.findAll();
        return trabajos.stream()
            .map(s -> new TrabajoDTOsalida(
                s.getId(),
                s.getCargo(),
                s.getJornada(),
                s.getModalidad(),
                s.getSalario(),
                s.getDescripcion()))
            .toList();
   }

   public TrabajoDTOsalida crearTrabajoDTOsalida(TrabajoDTOentrada dto) {
        TrabajoModelo nuevoTrabajo = new TrabajoModelo();
        nuevoTrabajo.setCargo(dto.getCargo());
        nuevoTrabajo.setJornada(dto.getJornada());
        nuevoTrabajo.setModalidad(dto.getModalidad());
        nuevoTrabajo.setSalario(dto.getSalario());
        nuevoTrabajo.setDescripcion(dto.getDescripcion());

        TrabajoModelo trabajoGuardado = trabajorepositorio.save(nuevoTrabajo);

        return new TrabajoDTOsalida(
            trabajoGuardado.getId(),
            trabajoGuardado.getCargo(),
            trabajoGuardado.getJornada(),
            trabajoGuardado.getModalidad(),
            trabajoGuardado.getSalario(),
            trabajoGuardado.getDescripcion());
   }

   public TrabajoDTOsalida actualizarTrabajoDTOsalida(Long id, TrabajoDTOentrada dto) {
    Optional<TrabajoModelo> trabajoExistente = trabajorepositorio.findById(id);
    
    if (trabajoExistente.isPresent()) {
        TrabajoModelo trabajoModificado = trabajoExistente.get();
        
        trabajoModificado.setCargo(dto.getCargo());
        trabajoModificado.setJornada(dto.getJornada());
        trabajoModificado.setModalidad(dto.getModalidad());
        trabajoModificado.setSalario(dto.getSalario());
        trabajoModificado.setDescripcion(dto.getDescripcion());
        
        TrabajoModelo actualizado = trabajorepositorio.save(trabajoModificado);
        
        return new TrabajoDTOsalida(
            actualizado.getId(),
            actualizado.getCargo(),
            actualizado.getJornada(),
            actualizado.getModalidad(),
            actualizado.getSalario(),
            actualizado.getDescripcion()
        );
        } else {
            throw new RecursoNoEncontradoException("Cargo no encontrado con id: " + id);
        }
    }

    public boolean eliminarTrabajo(Long id) {
        if (trabajorepositorio.existsById(id)) {
            trabajorepositorio.deleteById(id);
            return true;
        } else {
            throw new RecursoNoEncontradoException("Cargo no encontrado con id: " + id);
        }
    }

}
