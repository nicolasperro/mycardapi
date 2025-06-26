package com.mycard.mycardapi.service;

import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.MetodoVitoria;
import com.mycard.mycardapi.model.repository.MetodoVitoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetodoVitoriaService {

    private final MetodoVitoriaRepository repository;

    public MetodoVitoriaService(MetodoVitoriaRepository repository) {
        this.repository = repository;
    }

    public MetodoVitoria salvar(MetodoVitoria metodo) {
        return repository.save(metodo);
    }

    public Optional<MetodoVitoria> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<MetodoVitoria> listarTodos() {
        return repository.findAll();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RegraNegocioException("Método de vitória não encontrado");
        }
        repository.deleteById(id);
    }
}

