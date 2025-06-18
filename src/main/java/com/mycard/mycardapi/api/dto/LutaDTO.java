package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.Luta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LutaDTO {

    private Long id;
    private Long idEvento;
    private Long idAtleta1;
    private Long idAtleta2;
    private Long idVencedor;
    private Long idArbitro;      // <-- O controller usa o getter deste campo: getIdArbitro()
    private Long idModalidade;   // <-- O controller usa o getter deste campo: getIdModalidade()
    private String pesoCategoria;
    private Integer rounds;

    public static LutaDTO create(Luta luta) {
        ModelMapper modelMapper = new ModelMapper();
        LutaDTO dto = modelMapper.map(luta, LutaDTO.class);

        if (luta.getEvento() != null) dto.setIdEvento(luta.getEvento().getId());
        if (luta.getAtleta01() != null) dto.setIdAtleta1(luta.getAtleta01().getId());
        if (luta.getAtleta02() != null) dto.setIdAtleta2(luta.getAtleta02().getId());
        if (luta.getAtletaVencedor() != null) dto.setIdVencedor(luta.getAtletaVencedor().getId());
        if (luta.getArbitro() != null) dto.setIdArbitro(luta.getArbitro().getId());
        if (luta.getModalidade() != null) dto.setIdModalidade(luta.getModalidade().getId());

        return dto;
    }
}