package com.alucar.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {
    
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String endereco;
    private String cpf;
    private String cnh;
}