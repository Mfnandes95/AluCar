package com.alucar.application.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    
    private String token;
    private String tipo;
    private Long usuarioId;
    private String nome;
    private String email;
}
