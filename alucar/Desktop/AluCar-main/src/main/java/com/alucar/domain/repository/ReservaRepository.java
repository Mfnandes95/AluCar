package com.alucar.domain.repository;

import com.alucar.domain.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuarioId(Long usuarioId);

    // Verifica se existe reserva ativa do mesmo carro que se sobrepõe ao período pedido
    @Query("""
        SELECT r FROM Reserva r
        WHERE r.carro.id = :carroId
        AND r.status = 'ativa'
        AND r.dataInicioPrevista <= :dataFim
        AND r.dataFimPrevista >= :dataInicio
        """)
    List<Reserva> findConflitos(@Param("carroId") Long carroId,
                                  @Param("dataInicio") LocalDate dataInicio,
                                  @Param("dataFim") LocalDate dataFim);
}