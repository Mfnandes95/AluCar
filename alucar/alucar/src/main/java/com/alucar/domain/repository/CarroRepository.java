package com.alucar.domain.repository;

import com.alucar.domain.model.Carro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

    // --- Métodos de Busca Simples (Spring Data JPA) ---
    Carro findByPlaca(String placa);
    Carro findByPlacaAndDisponivelTrue(String placa);
    Carro findByIdAndDisponivelTrue(Long id);
    Page<Carro> findByDisponivelTrue(Pageable pageable);

    // --- Consulta Personalizada (Sua lógica de disponibilidade) ---
    @Query("SELECT c FROM Carro c WHERE c.id NOT IN (" +
       "SELECT r.carroId FROM Reserva r " +
       "WHERE (r.dataReserva <= :fim AND r.dataDevolucao >= :inicio) " +
       "AND r.status NOT IN ('FINALIZADA', 'CANCELADA'))")
       List<Carro> findCarrosDisponiveis(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}