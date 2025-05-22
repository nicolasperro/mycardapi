package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.Atleta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtletaDTO {

    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String nacionalidade;
    private Equipe equipe;
    private Double altura;
    private String genero;
    private Double pesoCorporal;
    private String apelido;
    private String numeroContato;
    private String emailContato;
    private String textoDescricao;

    public static AtletaDTO create(Atleta atleta) {
        ModelMapper modelMapper = new ModelMapper();
        AtletaDTO dto = modelMapper.map(atleta, AtletaDTO.class);

        dto.nomeCompleto = atleta.getNomeCompleto();
        dto.dataNascimento = atleta.getDataNascimento();
        dto.nacionalidade = atleta.getNacionalidade();
        dto.equipe = atleta.getEquipe();
        dto.altura = atleta.getAltura();
        dto.genero = atleta.getGenero();
        dto.pesoCorporal = atleta.getPesoCorporal();
        dto.apelido = atleta.getApelido();
        dto.numeroContato = atleta.getNumeroContato();
        dto.emailContato = atleta.getEmailContato();
        dto.textoDescricao = atleta.getTextoDescricao();
        return dto;
    }

}
