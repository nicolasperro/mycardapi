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
    private String nomeCompleto;
    private String genero;
    private String apelido;
    private String numeroContato;
    private String emailContato;

    private Long idEquipe;
    private String nomeEquipe; // ADICIONADO para exibição

    public static AtletaDTO create(Atleta atleta) {
        ModelMapper modelMapper = new ModelMapper();
        AtletaDTO dto = modelMapper.map(atleta, AtletaDTO.class);

        if (atleta.getEquipe() != null) {
            dto.setIdEquipe(atleta.getEquipe().getId());
            dto.setNomeEquipe(atleta.getEquipe().getNomeEquipe()); // <-- Nome exibido na listagem
        }

        return dto;
    }
}
