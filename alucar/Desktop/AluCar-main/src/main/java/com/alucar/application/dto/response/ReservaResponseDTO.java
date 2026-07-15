package com.alucar.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservaResponseDTO {
    private Long idReserva;
    private String carroModelo;
    private String filialRetiradaNome;
    private String filialDevolucaoNome;
    private LocalDateTime dataReserva;
    private LocalDate dataInicioPrevista;
    private LocalDate dataFimPrevista;
    private String status;

    public ReservaResponseDTO(Long idReserva, String carroModelo, String filialRetiradaNome,
                                String filialDevolucaoNome, LocalDateTime dataReserva,
                                LocalDate dataInicioPrevista, LocalDate dataFimPrevista, String status) {
        this.idReserva = idReserva;
        this.carroModelo = carroModelo;
        this.filialRetiradaNome = filialRetiradaNome;
        this.filialDevolucaoNome = filialDevolucaoNome;
        this.dataReserva = dataReserva;
        this.dataInicioPrevista = dataInicioPrevista;
        this.dataFimPrevista = dataFimPrevista;
        this.status = status;
    }

    public Long getIdReserva() { return idReserva; }
    public String getCarroModelo() { return carroModelo; }
    public String getFilialRetiradaNome() { return filialRetiradaNome; }
    public String getFilialDevolucaoNome() { return filialDevolucaoNome; }
    public LocalDateTime getDataReserva() { return dataReserva; }
    public LocalDate getDataInicioPrevista() { return dataInicioPrevista; }
    public LocalDate getDataFimPrevista() { return dataFimPrevista; }
    public String getStatus() { return status; }
}
