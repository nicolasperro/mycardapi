package com.mycard.mycardapi.service;

import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Modalidade;
import com.mycard.mycardapi.model.repository.ModalidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModalidadeService {

    private final ModalidadeRepository repository;

    public ModalidadeService(ModalidadeRepository repository) {
        this.repository = repository;
    }

    public Modalidade salvar(Modalidade modalidade) {
        return repository.save(modalidade);
    }

    public Optional<Modalidade> getModalidadeById(Long id) {
        return repository.findById(id);
    }

    public List<Modalidade> listarTodas() {
        return repository.findAll();
    }

    public Optional<Modalidade> buscarPorNome(String nomeModalidade) {
        return repository.findByNomeModalidade(nomeModalidade);
    }

    public Modalidade atualizar(Long id, Modalidade atualizada) {
        return repository.findById(id)
                .map(modalidade -> {
                    modalidade.setNomeModalidade(atualizada.getNomeModalidade());
                    return repository.save(modalidade);
                })
                .orElseThrow(() -> new RegraNegocioException("Modalidade não encontrada com id: " + id));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RegraNegocioException("Modalidade não encontrada com id: " + id);
        }
        repository.deleteById(id);
    }
}