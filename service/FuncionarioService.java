package com.mycard.mycardapi.service;

import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Evento;
import com.mycard.mycardapi.model.entity.Funcionario;
import com.mycard.mycardapi.model.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public Funcionario salvar(Funcionario funcionario) {
        validar(funcionario);
        return repository.save(funcionario);
    }

    public Optional<Funcionario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Funcionario> listarTodos() {
        return repository.findAll();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RegraNegocioException("Funcionário não encontrado");
        }
        repository.deleteById(id);
    }

    public void validar(Funcionario funcionario) {
        if (funcionario.getNome() == null || funcionario.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido!");
        }
        if (funcionario.getCpf() == null) {
            throw new RegraNegocioException("CPF inválido!");
        }
        if (funcionario.getEmailContato() == null) {
            throw new RegraNegocioException("Email de contato inválido!");
        }
        if (funcionario.getNumeroContato() == null) {
            throw new RegraNegocioException("Número de contato inválido!");
        }
    }
}

