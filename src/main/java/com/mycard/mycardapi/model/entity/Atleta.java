package com.mycard.mycardapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String foto;

    private String nomeCompleto;
    private String genero;
    private LocalDate dataNascimento;
    private String nacionalidade;
    private Double altura;
    private Double pesoCorporal;
    private String apelido;
    private String numeroContato;
    private String emailContato;
    private String textoDescricao;

    @ManyToOne
    private Equipe equipe;
}