package com.pagos.pagosms.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pagos.pagosms.modelo.PagoModelo;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepositorio extends JpaRepository<PagoModelo, Long> {
}