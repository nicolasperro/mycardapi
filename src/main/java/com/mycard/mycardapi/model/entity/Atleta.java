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

    private String nome;
    private String genero;
    private LocalDate dataNascimento;
    private String nacionalidade;
    private Double altura;
    private Double peso;
    private String apelido;
    private String numeroContato;
    private String emailContato;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;
}
