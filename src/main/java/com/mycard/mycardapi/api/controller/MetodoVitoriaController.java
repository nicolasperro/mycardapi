package com.mycard.mycardapi.api.controller;

import com.mycard.mycardapi.api.dto.MetodoVitoriaDTO;
import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.MetodoVitoria;
import com.mycard.mycardapi.service.MetodoVitoriaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/metodos-vitoria")
@RequiredArgsConstructor
public class MetodoVitoriaController {

    private final MetodoVitoriaService service;

    @GetMapping
    public ResponseEntity<List<MetodoVitoriaDTO>> listarTodos() {
        List<MetodoVitoriaDTO> dtos = service.listarTodos()
                .stream()
                .map(MetodoVitoriaDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<MetodoVitoria> metodo = service.buscarPorId(id);
        if (metodo.isEmpty()) {
            return new ResponseEntity<>("Método de vitória não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(MetodoVitoriaDTO.create(metodo.get()));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody MetodoVitoriaDTO dto) {
        try {
            MetodoVitoria entidade = new ModelMapper().map(dto, MetodoVitoria.class);
            entidade = service.salvar(entidade);
            return new ResponseEntity<>(MetodoVitoriaDTO.create(entidade), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody MetodoVitoriaDTO dto) {
        Optional<MetodoVitoria> existente = service.buscarPorId(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>("Método de vitória não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            MetodoVitoria entidade = new ModelMapper().map(dto, MetodoVitoria.class);
            entidade.setId(id);
            entidade = service.salvar(entidade);
            return ResponseEntity.ok(MetodoVitoriaDTO.create(entidade));
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

