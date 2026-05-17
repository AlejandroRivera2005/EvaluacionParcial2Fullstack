package com.microservicio.soportehotel.repositorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SoporteRepository extends JpaRepository<com.microservicio.soportehotel.soporte_modelo.Soporte, Long> {

}
