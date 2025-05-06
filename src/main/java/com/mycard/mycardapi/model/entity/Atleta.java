package com.mycard.mktapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foto;
    private String nomeCompleto;
    private String genero;
    private LocalDate dataNascimento;
    private String nacionalidade;

    @ManyToOne
    private Equipe equipe;
    private Double altura;
    private Double pesoCorporal;
    private String apelido;
    private String numeroContato;
    private String emailContato;
    private String textoDescricao;
}