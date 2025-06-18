package com.mycard.mycardapi.service;

import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Arbitro;
import com.mycard.mycardapi.model.repository.ArbitroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArbitroService {

    private final ArbitroRepository repository;

    public ArbitroService(ArbitroRepository repository) {
        this.repository = repository;
    }

    public Arbitro salvar(Arbitro arbitro) {
        // validação
        return repository.save(arbitro);
    }

    public Optional<Arbitro> getArbitroById(Long id) {
        return repository.findById(id);
    }

    public List<Arbitro> listarTodos() {
        return repository.findAll();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RegraNegocioException("Árbitro não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
