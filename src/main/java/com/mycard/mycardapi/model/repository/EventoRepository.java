package com.mycard.mycardapi.repository;

import com.mycard.mycardapi.model.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByCidade(String cidade);
    List<Evento> findByUf(String uf);
    // data do evento
}


