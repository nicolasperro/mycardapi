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
@RequestMapping("/api/v1/metodosVitoria")
@RequiredArgsConstructor
public class MetodoVitoriaController {

    private final MetodoVitoriaService service;

    @GetMapping
    public ResponseEntity<List<MetodoVitoriaDTO>> get() {
        List<MetodoVitoria> metodos = service.listarTodos();
        List<MetodoVitoriaDTO> dtos = metodos.stream()
                .map(MetodoVitoriaDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        Optional<MetodoVitoria> metodo = service.buscarPorId(id);
        if (metodo.isEmpty()) {
            return new ResponseEntity("Método de vitória não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(MetodoVitoriaDTO.create(metodo.get()));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody MetodoVitoriaDTO dto) {
        try {
            MetodoVitoria metodo = converter(dto);
            metodo = service.salvar(metodo);
            return new ResponseEntity(MetodoVitoriaDTO.create(metodo), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody MetodoVitoriaDTO dto) {
        if (service.buscarPorId(id).isEmpty()) {
            return new ResponseEntity("Método de vitória não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            MetodoVitoria metodo = converter(dto);
            metodo.setId(id);
            metodo = service.salvar(metodo);
            return ResponseEntity.ok(MetodoVitoriaDTO.create(metodo));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        try {
            // A verificação de existência já é feita no seu service
            service.deletar(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private MetodoVitoria converter(MetodoVitoriaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, MetodoVitoria.class);
    }
}
