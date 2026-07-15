package com.alucar.domain.repository;

import com.alucar.domain.model.Carro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

    // --- Métodos de Busca Simples (Spring Data JPA) ---
    Carro findByPlaca(String placa);
    Carro findByPlacaAndStatus(String placa, String status);
    Carro findByIdAndStatus(Long id, String status);
    Page<Carro> findByStatus(String status, Pageable pageable);

    // --- Consulta Personalizada (Sua lógica de disponibilidade por período) ---
    @Query("SELECT c FROM Carro c WHERE c.status = 'disponivel' AND c.id NOT IN (" +
           "SELECT r.carro.id FROM Reserva r WHERE r.status = 'ativa' " +
           "AND r.dataInicioPrevista <= :fim AND r.dataFimPrevista >= :inicio)")
    List<Carro> findDisponiveisNoPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}