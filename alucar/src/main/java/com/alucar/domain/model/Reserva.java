package com.alucar.domain.model;

public class Reserva {
    
    String id;
    String dataReserva;
    String dataDevolucao;
    String status;
    String UsuarioId;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDataReserva() {
        return dataReserva;
    }
    public void setDataReserva(String dataReserva) {
        this.dataReserva = dataReserva;
    }
    public String getDataDevolucao() {
        return dataDevolucao;
    }
    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getUsuarioId() {
        return UsuarioId;
    }
    public void setUsuarioId(String usuarioId) {
        UsuarioId = usuarioId;
    }
}
