package com.mycard.mycardapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "modalidade")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Modalidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "modalidade", cascade = CascadeType.ALL)
    private List<MetodoVitoria> metodosVitoria;
}
