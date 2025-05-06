package com.mycard.mktapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logo;
    private String nomeEquipe;
    private LocalDate dataCriacao;
    private String logradouro;
    private String numEndereco;
    private String cep;
    private String cidade;
    private String uf;
}