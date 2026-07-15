package com.alucar.domain.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_veiculo", nullable = false) // Ajustado de id_carro para id_veiculo
    private Carro carro;
  
    @ManyToOne
    @JoinColumn(name = "id_filial_retirada", nullable = false)
    private Filial filialRetirada;

    @ManyToOne
    @JoinColumn(name = "id_filial_devolucao", nullable = false)
    private Filial filialDevolucao;

    @Column(name = "data_reserva")
    private LocalDateTime dataReserva = LocalDateTime.now();

    @Column(name = "data_inicio_prevista", nullable = false)
    private LocalDate dataInicioPrevista;

    @Column(name = "data_fim_prevista", nullable = false)
    private LocalDate dataFimPrevista;

    @Column(nullable = false)
    private String status = "ativa";

    public Reserva() {}

    public Long getIdReserva() { return idReserva; }
    public void setIdReserva(Long idReserva) { this.idReserva = idReserva; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Carro getCarro() { return carro; }
    public void setCarro(Carro carro) { this.carro = carro; }
    public Filial getFilialRetirada() { return filialRetirada; }
    public void setFilialRetirada(Filial filialRetirada) { this.filialRetirada = filialRetirada; }
    public Filial getFilialDevolucao() { return filialDevolucao; }
    public void setFilialDevolucao(Filial filialDevolucao) { this.filialDevolucao = filialDevolucao; }
    public LocalDateTime getDataReserva() { return dataReserva; }
    public void setDataReserva(LocalDateTime dataReserva) { this.dataReserva = dataReserva; }
    public LocalDate getDataInicioPrevista() { return dataInicioPrevista; }
    public void setDataInicioPrevista(LocalDate dataInicioPrevista) { this.dataInicioPrevista = dataInicioPrevista; }
    public LocalDate getDataFimPrevista() { return dataFimPrevista; }
    public void setDataFimPrevista(LocalDate dataFimPrevista) { this.dataFimPrevista = dataFimPrevista; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}