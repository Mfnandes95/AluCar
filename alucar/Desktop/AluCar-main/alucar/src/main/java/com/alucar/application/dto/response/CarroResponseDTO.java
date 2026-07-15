package com.alucar.application.dto.response;

public class CarroResponseDTO {
    
    private Long id;
    private String modelo;
    private String marca;
    private String ano;
    private String tipoCombustivel;
    private String quilometragem;
    private String cor;
    private String placa;
    private Double valorDiaria;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getMarca() {
        return marca;
    }   
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getAno() {
        return ano;
    }
    public void setAno(String ano) {
        this.ano = ano;
    }
    public String getTipoCombustivel() {
        return tipoCombustivel;
    }
    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }
    public String getQuilometragem() {
        return quilometragem;
    }
    public void setQuilometragem(String quilometragem) {
        this.quilometragem = quilometragem;
    }
    public String getCor() {
        return cor;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public Double getValorDiaria() {
        return valorDiaria;
    }
    public void setValorDiaria(Double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }
}
