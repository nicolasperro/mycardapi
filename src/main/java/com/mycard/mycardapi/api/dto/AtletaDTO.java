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

    private Long id;
    private Integer matricula;
    private String nome;
    private Long idCurso;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public static AtletaDTO create(Atleta atleta) {
        ModelMapper modelMapper = new ModelMapper();
        AtletaDTO dto = modelMapper.map(atleta, AtletaDTO.class);
        dto.logradouro = atleta.getEndereco().getLogradouro();//as linhas com erro são pq eu copiei do marco e precisa ajeitar pro contexto do nosso projeto
        dto.numero = atleta.getEndereco().getNumero();
        dto.complemento = atleta.getEndereco().getComplemento();
        dto.bairro = atleta.getEndereco().getBairro();
        dto.cidade = atleta.getEndereco().getCidade();
        dto.uf = atleta.getEndereco().getUf();
        dto.cep = atleta.getEndereco().getCep();
        return dto;
    }

}
