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
@RequestMapping("/api/v1/arbitros")
@RequiredArgsConstructor
public class ArbitroController {

    private final OrganizacaoArbitragemService service;

    @GetMapping()
    public ResponseEntity get() {
        List<OrganizacaoArbitragem> organizacoes = service.listarTodas();
        // Converte a lista de entidades para uma lista de DTOs para a resposta
        return ResponseEntity.ok(organizacoes.stream()
                .map(OrganizacaoArbitragemDTO::create)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<OrganizacaoArbitragem> organizacao = service.getOrganizacaoById(id);
        if (organizacao.isEmpty()) {
            return new ResponseEntity("Organização de Arbitragem não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(OrganizacaoArbitragemDTO.create(organizacao.get()));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody OrganizacaoArbitragemDTO dto) {
        try {
            OrganizacaoArbitragem organizacao = converter(dto);
            organizacao = service.salvar(organizacao);
            // Retorna o DTO do objeto criado, como no padrão
            return new ResponseEntity(OrganizacaoArbitragemDTO.create(organizacao), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody OrganizacaoArbitragemDTO dto) {
        // Verifica se a organização existe antes de tentar atualizar
        if (service.getOrganizacaoById(id).isEmpty()) {
            return new ResponseEntity("Organização de Arbitragem não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            OrganizacaoArbitragem organizacao = converter(dto);
            organizacao.setId(id); // Garante que o ID correto seja atualizado
            service.salvar(organizacao);
            return ResponseEntity.ok(OrganizacaoArbitragemDTO.create(organizacao));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        try {
            // A lógica de verificação de existência já está no seu service, então apenas chamamos o método
            service.deletar(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            // Captura a exceção caso o service informe que a organização não foi encontrada
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Método privado para converter DTO em Entidade
    private OrganizacaoArbitragem converter(OrganizacaoArbitragemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, OrganizacaoArbitragem.class);
    }
}
