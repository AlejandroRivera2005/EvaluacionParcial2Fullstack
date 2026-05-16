package com.trabajos.trabajosms.repositorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TrabajoRepositorio extends JpaRepository<com.trabajos.trabajosms.modelo.TrabajoModelo, Long> {

}
