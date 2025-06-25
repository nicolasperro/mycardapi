package com.mycard.mycardapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "equipe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate dataCriacao;

    private String logradouro;
    private String numero;
    private String cep;
    private String cidade;
    private String uf;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL)
    private List<Atleta> atletas;
}
