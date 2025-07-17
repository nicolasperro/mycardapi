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
@RequestMapping("/api/v1/organizacoesArbitragem")
@RequiredArgsConstructor
public class OrganizacaoArbitragemController {

    private final OrganizacaoArbitragemService service;

    @GetMapping
    public ResponseEntity<List<OrganizacaoArbitragemDTO>> get() {
        List<OrganizacaoArbitragem> organizacoes = service.listarTodas();
        List<OrganizacaoArbitragemDTO> dtos = organizacoes.stream()
                .map(OrganizacaoArbitragemDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        Optional<OrganizacaoArbitragem> organizacao = service.getOrganizacaoById(id);
        if (organizacao.isEmpty()) {
            return new ResponseEntity("Organização de Arbitragem não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(OrganizacaoArbitragemDTO.create(organizacao.get()));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody OrganizacaoArbitragemDTO dto) {
        try {
            OrganizacaoArbitragem organizacao = converter(dto);
            organizacao = service.salvar(organizacao);
            return new ResponseEntity(OrganizacaoArbitragemDTO.create(organizacao), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody OrganizacaoArbitragemDTO dto) {
        if (service.getOrganizacaoById(id).isEmpty()) {
            return new ResponseEntity("Organização de Arbitragem não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            OrganizacaoArbitragem organizacao = converter(dto);
            organizacao.setId(id);
            organizacao = service.salvar(organizacao);
            return ResponseEntity.ok(OrganizacaoArbitragemDTO.create(organizacao));
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

    private OrganizacaoArbitragem converter(OrganizacaoArbitragemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, OrganizacaoArbitragem.class);
    }
}