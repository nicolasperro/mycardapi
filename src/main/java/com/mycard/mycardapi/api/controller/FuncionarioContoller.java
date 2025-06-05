package com.mycard.mycardapi.api.controller;

import com.mycard.mycardapi.api.dto.FuncionarioDTO;
import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Funcionario;
import com.mycard.mycardapi.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService service;

    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> listarTodos() {
        List<FuncionarioDTO> dtos = service.listarTodos()
                .stream()
                .map(FuncionarioDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Funcionario> funcionario = service.buscarPorId(id);
        if (funcionario.isEmpty()) {
            return new ResponseEntity<>("Funcionário não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(FuncionarioDTO.create(funcionario.get()));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody FuncionarioDTO dto) {
        try {
            Funcionario entidade = new ModelMapper().map(dto, Funcionario.class);
            entidade = service.salvar(entidade);
            return new ResponseEntity<>(FuncionarioDTO.create(entidade), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody FuncionarioDTO dto) {
        Optional<Funcionario> existente = service.buscarPorId(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>("Funcionário não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            Funcionario entidade = new ModelMapper().map(dto, Funcionario.class);
            entidade.setId(id);
            entidade = service.salvar(entidade);
            return ResponseEntity.ok(FuncionarioDTO.create(entidade));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

