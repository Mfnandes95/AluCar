package com.alucar.domain.repository;

import com.alucar.domain.model.Carro;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VeiculoRepository {
    List<Carro> findByDisponivelTrue();

    @Query("SELECT v FROM Veiculo v WHERE v.disponivel = true AND v.id NOT IN (" +
           "SELECT r.veiculo.id FROM Reserva r WHERE " +
           "(r.dataRetirada <= :fim AND r.dataDevolucao >= :inicio) AND " +
           "r.status NOT IN ('FINALIZADA', 'CANCELADA'))")
    List<Carro> findVeiculosDisponiveisPorPeriodo(@Param("inicio") LocalDateTime inicio, 
                                                    @Param("fim") LocalDateTime fim);

}
