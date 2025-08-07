package com.mycard.mycardapi.api.controller;

import com.mycard.mycardapi.api.dto.LutaDTO;
import com.mycard.mycardapi.exception.RegraNegocioException;
import com.mycard.mycardapi.model.entity.*;
import com.mycard.mycardapi.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lutas")
@RequiredArgsConstructor
public class LutaController {

    private final LutaService service;
    private final EventoService eventoService;
    private final AtletaService atletaService;
    private final ArbitroService arbitroService;
    private final ModalidadeService modalidadeService;
    private final OrganizacaoArbitragemService organizacaoService;

    @GetMapping
    public ResponseEntity<List<LutaDTO>> get() {
        List<Luta> lutas = service.listarTodas();
        List<LutaDTO> dtos = lutas.stream()
                .map(LutaDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        Optional<Luta> luta = service.buscarPorId(id);
        if (luta.isEmpty()) {
            return new ResponseEntity("Luta não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(LutaDTO.create(luta.get()));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody LutaDTO dto) {
        try {
            Luta luta = converter(dto);
            luta = service.salvar(luta);
            return new ResponseEntity(LutaDTO.create(luta), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody LutaDTO dto) {
        if (service.buscarPorId(id).isEmpty()) {
            return new ResponseEntity("Luta não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Luta luta = converter(dto);
            luta.setId(id); 
            Luta lutaAtualizada = service.atualizar(id, luta);
            return ResponseEntity.ok(LutaDTO.create(lutaAtualizada));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        try {
            service.deletar(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    private Luta converter(LutaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Luta luta = modelMapper.map(dto, Luta.class);

        if (dto.getIdEvento() != null) {
            Evento evento = eventoService.getEventoById(dto.getIdEvento())
                    .orElseThrow(() -> new RegraNegocioException("Evento não encontrado: " + dto.getIdEvento()));
            luta.setEvento(evento);
        }

        if (dto.getIdAtleta1() != null) {
            Atleta atleta1 = atletaService.getAtletaById(dto.getIdAtleta1())
                    .orElseThrow(() -> new RegraNegocioException("Atleta 1 não encontrado: " + dto.getIdAtleta1()));
            luta.setAtleta01(atleta1);
        }

        if (dto.getIdAtleta2() != null) {
            Atleta atleta2 = atletaService.getAtletaById(dto.getIdAtleta2())
                    .orElseThrow(() -> new RegraNegocioException("Atleta 2 não encontrado: " + dto.getIdAtleta2()));
            luta.setAtleta02(atleta2);
        }

        if (dto.getIdVencedor() != null) {
            Atleta vencedor = atletaService.getAtletaById(dto.getIdVencedor())
                    .orElseThrow(() -> new RegraNegocioException("Atleta vencedor não encontrado: " + dto.getIdVencedor()));
            luta.setAtletaVencedor(vencedor);
        }

        if (dto.getIdModalidade() != null) {
            Modalidade modalidade = modalidadeService.getModalidadeById(dto.getIdModalidade())
                    .orElseThrow(() -> new RegraNegocioException("Modalidade não encontrada: " + dto.getIdModalidade()));
            luta.setModalidade(modalidade);
        }

        if (dto.getIdArbitro() != null) {
            Arbitro arbitro = arbitroService.getArbitroById(dto.getIdArbitro())
                    .orElseThrow(() -> new RegraNegocioException("Árbitro não encontrado: " + dto.getIdArbitro()));
            luta.setArbitro(arbitro);
        }

        if (dto.getIdOrganizacao() != null) {
            OrganizacaoArbitragem organizacao = organizacaoService.getOrganizacaoById(dto.getIdOrganizacao())
                    .orElseThrow(() -> new RegraNegocioException("Organização de Arbitragem não encontrada: " + dto.getIdOrganizacao()));
            luta.setOrganizacaoArbitragem(organizacao);
        }
        return luta;
    }
}
