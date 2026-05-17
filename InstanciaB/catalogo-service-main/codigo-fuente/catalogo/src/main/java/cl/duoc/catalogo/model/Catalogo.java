package cl.duoc.catalogo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * La anotación @Entity le dice a Spring que esta clase se convertirá en una tabla en la base de datos.
 * La anotación @Data es de Lombok y genera automáticamente los métodos getter, setter y constructores.
 */
@Data
@Entity
public class Catalogo {

    /**
     * @Id indica que esta variable será la clave primaria (identificador único) de la tabla.
     * @GeneratedValue indica que la base de datos asignará este número automáticamente de forma secuencial.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Columnas normales de la tabla
    private String numeroHabitacion;
    private String tipoHabitacion;
    private String serviciosHabitacion;
}