package com.mycard.mycardapi.api.dto;

import com.mycard.mycardapi.model.entity.Atleta;
import com.mycard.mycardapi.model.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {

    private String nomeCompleto;
    private String cpf;
    private Integer numEndereco;

//    private String complemento;
//    private String bairro;

    private String cidade;
    private String uf;
    private String cep;
    private String email;
    private String telefone;

    public static FuncionarioDTO create(Funcionario funcionario) {
        ModelMapper modelMapper = new ModelMapper();
        FuncionarioDTO dto = modelMapper.map(funcionario, FuncionarioDTO.class);

        dto.nomeCompleto = funcionario.getNomeFuncionario();
        dto.cpf = funcionario.getCpf();
        dto.logradouro = evento.getEndereco().getLogradouro();
        dto.numero = evento.getEndereco().getNumero();

//        dto.complemento = evento.getEndereco().getComplemento();
//        dto.bairro = evento.getEndereco().getBairro();

        dto.cidade = evento.getEndereco().getCidade();
        dto.uf = evento.getEndereco().getUf();
        dto.cep = evento.getEndereco().getCep();
        dto.email = funcionario.getEmail();
        dto.telefone = funcionario.getTelefone();
        return dto;
    }

}
