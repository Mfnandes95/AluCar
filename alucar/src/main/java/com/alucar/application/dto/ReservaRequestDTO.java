package com.alucar.application.dto.request;

import java.time.LocalDate;

public class ReservaRequestDTO {
    private Long carroId;
    private Long filialRetiradaId;
    private Long filialDevolucaoId;
    private LocalDate dataInicioPrevista;
    private LocalDate dataFimPrevista;

    public Long getCarroId() { return carroId; }
    public void setCarroId(Long carroId) { this.carroId = carroId; }
    public Long getFilialRetiradaId() { return filialRetiradaId; }
    public void setFilialRetiradaId(Long filialRetiradaId) { this.filialRetiradaId = filialRetiradaId; }
    public Long getFilialDevolucaoId() { return filialDevolucaoId; }
    public void setFilialDevolucaoId(Long filialDevolucaoId) { this.filialDevolucaoId = filialDevolucaoId; }
    public LocalDate getDataInicioPrevista() { return dataInicioPrevista; }
    public void setDataInicioPrevista(LocalDate dataInicioPrevista) { this.dataInicioPrevista = dataInicioPrevista; }
    public LocalDate getDataFimPrevista() { return dataFimPrevista; }
    public void setDataFimPrevista(LocalDate dataFimPrevista) { this.dataFimPrevista = dataFimPrevista; }
}
