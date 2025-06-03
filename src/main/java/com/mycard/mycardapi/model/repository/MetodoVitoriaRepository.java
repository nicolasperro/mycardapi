package com.mycard.mycardapi.repository;

import com.mycard.mycardapi.model.entity.MetodoVitoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodoVitoriaRepository extends JpaRepository<MetodoVitoria, Long> {

    MetodoVitoria findByTitulo(String titulo);
}

