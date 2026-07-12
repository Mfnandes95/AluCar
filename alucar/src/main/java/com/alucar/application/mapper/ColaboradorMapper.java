package com.alucar.application.mapper;

import com.alucar.application.dto.response.ColaboradorResponseDTO;
import com.alucar.domain.model.Colaborador;
import org.springframework.stereotype.Component;

@Component
public class ColaboradorMapper {
    
     public Colaborador toEntity(ColaboradorResponseDTO colaboradorDTO) {
        if (colaboradorDTO == null) {
            return null;
        }
        Colaborador colaborador = new Colaborador();
        colaborador.setId(colaboradorDTO.getId());
        colaborador.setNome(colaboradorDTO.getNome());
        colaborador.setEmail(colaboradorDTO.getEmail());
        colaborador.setTelefone(colaboradorDTO.getTelefone());
        return colaborador;
    }

    public ColaboradorResponseDTO toDTO(Colaborador colaborador) {
        if (colaborador == null) {
            return null;
        }
        ColaboradorResponseDTO colaboradorResponseDTO = new ColaboradorResponseDTO(colaborador.getId(), colaborador.getNome(), colaborador.getEmail(), colaborador.getTelefone());
        return colaboradorResponseDTO;
    }
}
