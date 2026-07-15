package com.alucar.domain.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "filial")
public class Filial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_filial")
    private Long idFilial;

    private String nome;
    private String endereco;
    private String cidade;
    private String uf;
    private String telefone;
    private String email;

    @Column(name = "data_abertura")
    private LocalDate dataAbertura;

    public Filial() {}

    public Long getIdFilial() { return idFilial; }
    public void setIdFilial(Long idFilial) { this.idFilial = idFilial; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDate dataAbertura) { this.dataAbertura = dataAbertura; }
}