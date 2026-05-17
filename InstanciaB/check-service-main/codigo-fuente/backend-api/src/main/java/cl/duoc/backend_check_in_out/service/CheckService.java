package cl.duoc.backend_check_in_out.service;

import org.springframework.stereotype.Service;

import cl.duoc.backend_check_in_out.model.Datos;
import cl.duoc.backend_check_in_out.repository.CheckRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Service indica que esta clase contiene la lógica de negocio.
 */
@Service
public class CheckService {

    // Se declara el repositorio para poder usar las funciones de la base de datos.
    private final CheckRepository libroRepository;

    // Constructor para inyectar la dependencia del repositorio.
    public CheckService(CheckRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    /**
     * CREATE (Crear): Guarda un nuevo libro en la base de datos.
     */
    public Datos guardarLibro(Datos libro) {
        // El método save() es provisto automáticamente por JpaRepository
        return libroRepository.save(libro);
    }

    /**
     * READ (Leer todos): Obtiene una lista con todos los libros guardados.
     */
    public List<Datos> obtenerTodos() {
        // El método findAll() trae todos los registros de la tabla
        return libroRepository.findAll();
    }

    /**
     * READ (Leer por ID): Busca un libro específico usando su identificador.
     * Devuelve un Optional porque el libro podría no existir.
     */
    public Optional<Datos> obtenerPorId(Long id) {
        return libroRepository.findById(id);
    }

    /**
     * UPDATE (Actualizar): Reemplaza los datos de un libro existente.
     */
    public Datos actualizarLibro(Long id, Datos detallesLibro) {
        // Primero verificamos si el libro existe en la base de datos
        Optional<Datos> libroExistente = libroRepository.findById(id);
        
        if (libroExistente.isPresent()) {
            // Obtenemos el libro real de la base de datos
            Datos libroAActualizar = libroExistente.get();
            // Actualizamos sus datos con los nuevos datos recibidos
            libroAActualizar.setNumeroHabitacion(detallesLibro.getNumeroHabitacion());
            libroAActualizar.setEstadoHabitacion(detallesLibro.getEstadoHabitacion());
            libroAActualizar.setNombreHuesped(detallesLibro.getNombreHuesped());
            libroAActualizar.setRutHuesped(detallesLibro.getRutHuesped());
            libroAActualizar.setEmailHuesped(detallesLibro.getEmailHuesped());
            // Guardamos los cambios
            return libroRepository.save(libroAActualizar);
        } else {
            // Si no existe, retornamos nulo o podríamos lanzar un error
            return null; 
        }
    }

    /**
     * DELETE (Eliminar): Borra un libro de la base de datos usando su ID.
     */
    public boolean eliminarLibro(Long id) {
        if (libroRepository.existsById(id)) {
            libroRepository.deleteById(id);
            return true;
        }
        return false;
    }
}