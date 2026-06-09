package com.the_real_ones.controller;

import com.the_real_ones.model.Projeto;
import com.the_real_ones.repository.ProjetoRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoRepository repository = new ProjetoRepository();

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Projeto dadosNovos) {
        Projeto projetoExistente = repository.findById(id);
        
        if (projetoExistente == null) {
            return ResponseEntity.status(404).body("Projeto não encontrado.");
        }

        try {
            projetoExistente.editarProjeto(
                dadosNovos.getTitulo(),
                dadosNovos.getResumo(),
                dadosNovos.getPalavraChave(),
                dadosNovos.getPublicoAlvo(),
                dadosNovos.getAreaTematica(),
                dadosNovos.getCampos(),
                dadosNovos.getOds()
            );

            repository.update(projetoExistente);
            
            return ResponseEntity.ok("Projeto editado com sucesso!");

        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}