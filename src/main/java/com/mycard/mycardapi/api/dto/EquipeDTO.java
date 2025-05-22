package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.Atleta;
import com.mycard.mycardapi.model.entity.Equipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipeDTO {

//    private Foto LogoTipo

    private String nomeEquipe;
    private String logradouro;
    private Integer numEndereco;

//    private String complemento;
//    private String bairro;

    private String cidade;
    private String uf;
    private String cep;

    public static EquipeDTO create(Equipe equipe) {
        ModelMapper modelMapper = new ModelMapper();
        EquipeDTO dto = modelMapper.map(equipe, EquipeDTO.class);

        dto.nomeEquipe = equipe.getNomeEquipe();
        dto.logradouro = equipe.getEndereco().getLogradouro();
        dto.numero = equipe.getEndereco().getNumero();

//        dto.complemento = equipe.getEndereco().getComplemento();
//        dto.bairro = equipe.getEndereco().getBairro();

        dto.cidade = equipe.getEndereco().getCidade();
        dto.uf = equipe.getEndereco().getUf();
        dto.cep = equipe.getEndereco().getCep();
        return dto;
    }

}
