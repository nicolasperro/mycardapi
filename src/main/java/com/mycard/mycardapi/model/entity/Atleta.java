package com.mycard.mycardapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "atleta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCompleto;
    private String genero;
    private String dataNascimento;
    private String nacionalidade;
    private String altura;
    private String pesoCorporal;
    private String apelido;
    private String numeroContato;
    private String emailContato;
    private String textoDescricao;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;
}
