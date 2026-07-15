package com.alucar.application.dto.response;

import java.math.BigDecimal;

public class CarroResponseDTO {
    private Long id;
    private String placa;
    private String marca;
    private String modelo;
    private Integer anoFabricacao;
    private Integer anoModelo;
    private String cor;
    private Integer kmAtual;
    private String status;
    private BigDecimal valorDiaria;
    private Long idCategoria;
    private Long idFilial;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public Integer getAnoFabricacao() { return anoFabricacao; }
    public void setAnoFabricacao(Integer anoFabricacao) { this.anoFabricacao = anoFabricacao; }
    public Integer getAnoModelo() { return anoModelo; }
    public void setAnoModelo(Integer anoModelo) { this.anoModelo = anoModelo; }
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    public Integer getKmAtual() { return kmAtual; }
    public void setKmAtual(Integer kmAtual) { this.kmAtual = kmAtual; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(BigDecimal valorDiaria) { this.valorDiaria = valorDiaria; }
    public Long getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }
    public Long getIdFilial() { return idFilial; }
    public void setIdFilial(Long idFilial) { this.idFilial = idFilial; }
}