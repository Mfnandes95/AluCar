package com.alucar.domain.repository;

import com.alucar.domain.model.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {
    List<Colaborador> findByNomeContainingIgnoreCase(String nome);
    Page<Colaborador> findByDisponivelTrue(Pageable pageable);
}