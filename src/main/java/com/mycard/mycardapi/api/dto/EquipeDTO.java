package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.Equipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipeDTO {

    private Long id;
    private String nomeEquipe;
    private String dataCriacao;
    private String logradouro;
    private String numEndereco;
    private String cep;
    private String cidade;
    private String uf;

    public static EquipeDTO create(Equipe equipe) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(equipe, EquipeDTO.class);
    }
}