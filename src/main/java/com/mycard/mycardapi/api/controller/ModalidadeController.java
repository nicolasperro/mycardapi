package com.mycard.mycardapi.api.controller;

import com.mycard.mycardapi.api.dto.ModalidadeDTO;
import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.Modalidade;
import com.mycard.mycardapi.service.ModalidadeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/modalidades")
@RequiredArgsConstructor
public class ModalidadeController {

    private final ModalidadeService service;

    @GetMapping
    public ResponseEntity<List<ModalidadeDTO>> get() {
        List<Modalidade> modalidades = service.listarTodas();
        List<ModalidadeDTO> dtos = modalidades.stream()
                .map(ModalidadeDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        Optional<Modalidade> modalidade = service.getModalidadeById(id);
        if (modalidade.isEmpty()) {
            return new ResponseEntity("Modalidade não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(ModalidadeDTO.create(modalidade.get()));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody ModalidadeDTO dto) {
        try {
            Modalidade modalidade = converter(dto);
            modalidade = service.salvar(modalidade);
            return new ResponseEntity(ModalidadeDTO.create(modalidade), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody ModalidadeDTO dto) {
        // Usando o método de atualização do seu service que já faz a busca
        try {
            Modalidade modalidade = converter(dto);
            Modalidade modalidadeAtualizada = service.atualizar(id, modalidade);
            return ResponseEntity.ok(ModalidadeDTO.create(modalidadeAtualizada));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        try {
            service.deletar(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Modalidade converter(ModalidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Modalidade.class);
    }
}