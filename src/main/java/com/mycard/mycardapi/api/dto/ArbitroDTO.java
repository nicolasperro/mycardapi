package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.Arbitro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArbitroDTO {

    private Long id;
    private String nomeArbitro;
    private LocalDate dataNascimento;
    private Long idOrganizacao;
    private String nomeOrganizacao; // ✅ Adicionado

    public static ArbitroDTO create(Arbitro arbitro) {
        ModelMapper modelMapper = new ModelMapper();
        ArbitroDTO dto = modelMapper.map(arbitro, ArbitroDTO.class);

        if (arbitro.getOrganizacao() != null) {
            dto.setIdOrganizacao(arbitro.getOrganizacao().getId());
            dto.setNomeOrganizacao(arbitro.getOrganizacao().getNomeOrganizacao()); // ✅ Aqui
        }

        return dto;
    }
}
