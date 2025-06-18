package com.mycard.mycardapi.service;

import com.mycard.mycardapi.model.entity.Luta;
import com.mycard.mycardapi.model.repository.LutaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LutaService {

    private final LutaRepository lutaRepository;

    public LutaService(LutaRepository lutaRepository) {
        this.lutaRepository = lutaRepository;
    }

    public Luta salvar(Luta luta) {
        return lutaRepository.save(luta);
    }

    public Optional<Luta> buscarPorId(Long id) {
        return lutaRepository.findById(id);
    }

    public List<Luta> listarTodas() {
        return lutaRepository.findAll();
    }

    public Luta atualizar(Long id, Luta lutaAtualizada) {
        return lutaRepository.findById(id)
                .map(luta -> {
                    luta.setEvento(lutaAtualizada.getEvento());
                    luta.setAtleta01(lutaAtualizada.getAtleta01());
                    luta.setAtleta02(lutaAtualizada.getAtleta02());
                    luta.setAtletaVencedor(lutaAtualizada.getAtletaVencedor());
                    luta.setArbitro(lutaAtualizada.getArbitro());
                    luta.setModalidade(lutaAtualizada.getModalidade());
                    luta.setPesoCategoria(lutaAtualizada.getPesoCategoria());
                    luta.setRounds(lutaAtualizada.getRounds());
                    return lutaRepository.save(luta);
                })
                .orElseThrow(() -> new RuntimeException("Luta não encontrada com id: " + id));
    }

    public void deletar(Long id) {
        if (!lutaRepository.existsById(id)) {
            throw new RuntimeException("Luta não encontrada com id: " + id);
        }
        lutaRepository.deleteById(id);
    }
}
