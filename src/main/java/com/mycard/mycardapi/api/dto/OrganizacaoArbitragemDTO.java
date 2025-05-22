package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.Atleta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizacaoArbitragemDTO {

    private String nomeOrganizacao;

    public static OrganizacaoArbitragemDTO create(OrganizacaoArbitragem organizacao) {
        ModelMapper modelMapper = new ModelMapper();
        OrganizacaoArbitragemDTO dto = modelMapper.map(organizacao, OrganizacaoArbitragemDTO.class);

        dto.nomeOrganizacao = organizacao.getNomeOrganizacao();
        return dto;
    }

}
