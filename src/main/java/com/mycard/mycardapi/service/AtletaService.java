package com.mycard.mycardapi.service;

import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Atleta;
import com.mycard.mycardapi.model.repository.AtletaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtletaService {

    private final AtletaRepository repository;

    public AtletaService(AtletaRepository repository) {
        this.repository = repository;
    }

    public Atleta salvar(Atleta atleta) {
        return repository.save(atleta);
    }

    public Optional<Atleta> getAtletaById(Long id) {
        return repository.findById(id);
    }

    public List<Atleta> listarTodos() {
        return repository.findAll();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RegraNegocioException("Atleta não encontrado");
        }
        repository.deleteById(id);
    }
}

