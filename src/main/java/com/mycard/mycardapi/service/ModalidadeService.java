package com.mycard.mycardapi.service;

import com.mycard.mycardapi.model.entity.Modalidade;
import com.mycard.mycardapi.model.repository.ModalidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModalidadeService {

    private final ModalidadeRepository modalidadeRepository;

    public ModalidadeService(ModalidadeRepository modalidadeRepository) {
        this.modalidadeRepository = modalidadeRepository;
    }

    public Modalidade salvar(Modalidade modalidade) {
        return modalidadeRepository.save(modalidade);
    }

    public Optional<Modalidade> buscarPorId(Long id) {
        return modalidadeRepository.findById(id);
    }

    public List<Modalidade> listarTodas() {
        return modalidadeRepository.findAll();
    }

    public Optional<Modalidade> buscarPorNome(String nomeModalidade) {
        return modalidadeRepository.findByNomeModalidade(nomeModalidade);
    }

    public Modalidade atualizar(Long id, Modalidade atualizada) {
        return modalidadeRepository.findById(id)
                .map(modalidade -> {
                    modalidade.setNomeModalidade(atualizada.getNomeModalidade());
                    return modalidadeRepository.save(modalidade);
                })
                .orElseThrow(() -> new RuntimeException("Modalidade não encontrada com id: " + id));
    }

    public void deletar(Long id) {
        if (!modalidadeRepository.existsById(id)) {
            throw new RuntimeException("Modalidade não encontrada com id: " + id);
        }
        modalidadeRepository.deleteById(id);
    }
}
