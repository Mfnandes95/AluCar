package com.alucar.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "veiculo")
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_veiculo")
    private Long id;

    private String placa;
    private String marca;
    private String modelo;

    @Column(name = "ano_fabricacao")
    private Integer anoFabricacao;

    @Column(name = "ano_modelo")
    private Integer anoModelo;

    private String cor;

    @Column(name = "km_atual")
    private Integer kmAtual;

    // Substitui o antigo campo booleano "disponivel"; valores esperados no banco: 'disponivel', 'indisponivel', etc.
    private String status;

    // Adicionada conforme solicitado: preço por carro individual (coluna nova em veiculo)
    @Column(name = "valor_diaria")
    private BigDecimal valorDiaria;

    // Campo simples (sem relação JPA) só para satisfazer a FK NOT NULL do banco
    @Column(name = "id_categoria", nullable = false)
    private Long idCategoria;

    @ManyToOne
    @JoinColumn(name = "id_filial", nullable = false)
    private Filial filial;

    public Carro() {}

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
    public Filial getFilial() { return filial; }
    public void setFilial(Filial filial) { this.filial = filial; }
}