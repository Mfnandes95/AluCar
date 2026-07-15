package com.alucar.domain.model;

import jakarta.persistence.*; // Importação otimizada

@Entity
@Table(name = "colaboradores")
public class Colaborador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    @Column(unique = true, nullable = false) // Garante que e-mail seja único e obrigatório
    private String email;
    
    private String senha;
    private String telefone;
    private String cargo;
    private Boolean disponivel; // Adicionado para controle de disponibilidade

    // Construtor vazio (obrigatório pelo JPA)
    public Colaborador() {}

    // Getters and Setters mantidos conforme sua lógica original
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public Boolean getDisponivel() { return disponivel; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel;
}
}