package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.Atleta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtletaDTO {

    private Long id;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String nacionalidade;
    private Double altura;
    private String genero;
    private Double pesoCorporal;
    private String apelido;
    private String numeroContato;
    private String emailContato;
    private String textoDescricao;
    private Long idEquipe; 
    public static AtletaDTO create(Atleta atleta) {
        ModelMapper modelMapper = new ModelMapper();
        AtletaDTO dto = modelMapper.map(atleta, AtletaDTO.class);

        if (atleta.getEquipe() != null) {
            dto.setIdEquipe(atleta.getEquipe().getId());
        }

        return dto;
    }
}