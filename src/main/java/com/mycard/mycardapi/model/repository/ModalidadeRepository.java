package com.mycard.mycardapi.model.repository;

import com.mycard.mycardapi.model.entity.Modalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModalidadeRepository extends JpaRepository<Modalidade, Long> {

    Optional<Modalidade> findByNomeModalidade(String nomeModalidade);
}

