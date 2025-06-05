package com.mycard.mycardapi.api.controller;

import com.mycard.mycardapi.api.dto.OrganizacaoArbitragemDTO;
import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.OrganizacaoArbitragem;
import com.mycard.mycardapi.service.OrganizacaoArbitragemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/organizacoes")
@RequiredArgsConstructor
public class OrganizacaoArbitragemController {

    private final OrganizacaoArbitragemService service;

    @GetMapping
    public ResponseEntity<List<OrganizacaoArbitragemDTO>> listarTodas() {
        List<OrganizacaoArbitragem> lista = service.listarTodas();
        List<OrganizacaoArbitragemDTO> dtos = lista.stream()
                .map(OrganizacaoArbitragemDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<OrganizacaoArbitragem> org = service.getOrganizacaoById(id);
        if (org.isEmpty()) {
            return new ResponseEntity<>("Organização de arbitragem não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(OrganizacaoArbitragemDTO.create(org.get()));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody OrganizacaoArbitragemDTO dto) {
        ModelMapper mapper = new ModelMapper();
        OrganizacaoArbitragem entidade = mapper.map(dto, OrganizacaoArbitragem.class);
        entidade = service.salvar(entidade);
        return new ResponseEntity<>(OrganizacaoArbitragemDTO.create(entidade), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody OrganizacaoArbitragemDTO dto) {
        Optional<OrganizacaoArbitragem> existente = service.getOrganizacaoById(id);
        if (existente.isEmpty()) {
            return new ResponseEntity<>("Organização de arbitragem não encontrada", HttpStatus.NOT_FOUND);
        }

        OrganizacaoArbitragem entidade = new ModelMapper().map(dto, OrganizacaoArbitragem.class);
        entidade.setId(id);
        entidade = service.salvar(entidade);

        return ResponseEntity.ok(OrganizacaoArbitragemDTO.create(entidade));
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

