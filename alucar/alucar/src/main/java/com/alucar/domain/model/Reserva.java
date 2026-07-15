package com.alucar.domain.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
public class Reserva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usamos LocalDate para facilitar manipulações de calendário
    private LocalDate dataReserva;
    private LocalDate dataDevolucao;
    
    // Status pode ser um Enum no futuro, por enquanto mantemos String
    private String status;

    // Relacionamento com o usuário (Assumindo que você terá uma classe Usuario)
    // Se ainda não tiver, podemos manter como Long para guardar apenas o ID
    private Long usuarioId;

    private Long carroId; // Adicionado para referenciar o carro reservado

    // Construtor padrão necessário para o JPA
    public Reserva() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDataReserva() { return dataReserva; }
    public void setDataReserva(LocalDate dataReserva) { this.dataReserva = dataReserva; }

    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getCarroId() { return carroId; }
    public void setCarroId(Long carroId) { this.carroId = carroId; }
}