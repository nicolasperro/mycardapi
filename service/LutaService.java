package com.mycard.mycardapi.service;

import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Evento;
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
        validar(luta);
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

    public void validar(Luta luta) {
        if (luta.getEvento() == null || luta.getEvento().getId() == null) {
            throw new RegraNegocioException("Evento inválido!");
        }
        if (luta.getAtleta01() == null || luta.getAtleta01().getId() == null) {
            throw new RegraNegocioException("Primeiro atleta inválido!");
        }
        if (luta.getAtleta02() == null || luta.getAtleta02().getId() == null) {
            throw new RegraNegocioException("Segundo atleta inválido!");
        }
        if (luta.getAtletaVencedor() == null || luta.getAtletaVencedor().getId() == null) {
            throw new RegraNegocioException("Atleta vencedor inválido!");
        }
        if (luta.getModalidade() == null || luta.getModalidade().getId() == null) {
            throw new RegraNegocioException("Modalidade inválida!");
        }
        if (luta.getOrganizacaoArbitragem() == null || luta.getOrganizacaoArbitragem().getId() == null) {
            throw new RegraNegocioException("Organização de Arbitragem inválida!");
        }
        if (luta.getArbitro() == null || luta.getArbitro().getId() == null) {
            throw new RegraNegocioException("Arbitro inválido!");
        }
        if (luta.getPesoCategoria() == null) {
            throw new RegraNegocioException("Peso da categoria inválido!");
        }
        if (luta.getRounds() == null) {
            throw new RegraNegocioException("Quantidade de rounds inválida!");
        }
        if (luta.getRoundEncerramento() == null) {
            throw new RegraNegocioException("Round de encerramento inválido!");
        }
    }

}
