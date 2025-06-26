package com.mycard.mycardapi.service;

import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Equipe;
import com.mycard.mycardapi.model.repository.EquipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipeService {

    private final EquipeRepository repository;

    public EquipeService(EquipeRepository repository) {
        this.repository = repository;
    }

    public Equipe salvar(Equipe equipe) {
        return repository.save(equipe);
    }

    public Optional<Equipe> getEquipeById(Long id) {
        return repository.findById(id);
    }

    public List<Equipe> listarTodas() {
        return repository.findAll();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RegraNegocioException("Equipe não encontrada");
        }
        repository.deleteById(id);
    }
}

