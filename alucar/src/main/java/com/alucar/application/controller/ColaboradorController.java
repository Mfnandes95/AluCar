package com.alucar.application.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import com.alucar.application.dto.response.ColaboradorResponseDTO;
import com.alucar.application.mapper.ColaboradorMapper;
import com.alucar.domain.model.Colaborador;
import com.alucar.domain.service.ColaboradorService;

@RestController
@RequestMapping("/colaboradores")
public class ColaboradorController {
    
    private final ColaboradorService colaboradorService;
    private final ColaboradorMapper colaboradorMapper;

    public ColaboradorController(ColaboradorService colaboradorService, ColaboradorMapper colaboradorMapper) {
        this.colaboradorService = colaboradorService;
        this.colaboradorMapper = colaboradorMapper;
    }

    @PostMapping
    public ResponseEntity<ColaboradorResponseDTO> cadastrarColaborador(@Valid @RequestBody ColaboradorResponseDTO colaboradorResponseDTO) {
        Colaborador colaborador = colaboradorMapper.toEntity(colaboradorResponseDTO);
        Colaborador colaboradorCadastrado = colaboradorService.cadastrarColaborador(colaborador);
        ColaboradorResponseDTO colaboradorResponseDTOCadastrado = colaboradorMapper.toResponseDTO(colaboradorCadastrado);
        return ResponseEntity.status(HttpStatus.CREATED).body(colaboradorResponseDTOCadastrado);
    }  

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarColaborador(@PathVariable String id, @Valid @RequestBody ColaboradorResponseDTO colaboradorResponseDTO) {
        Colaborador colaborador = colaboradorMapper.toEntity(colaboradorResponseDTO);
        colaboradorService.atualizarColaborador(id, colaborador);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirColaborador(@PathVariable String id) {
        colaboradorService.excluirColaborador(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<ColaboradorResponseDTO>> listarColaboradoresDisponiveis(
            @RequestParam int pagina, 
            @RequestParam int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        Page<Colaborador> colaboradoresDisponiveis = colaboradorService.getColaboradoresDisponiveis(pageable);
        List<ColaboradorResponseDTO> colaboradoresDisponiveisDTO = colaboradoresDisponiveis.stream()    
                .map(colaboradorMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(colaboradoresDisponiveisDTO);
            }

}
