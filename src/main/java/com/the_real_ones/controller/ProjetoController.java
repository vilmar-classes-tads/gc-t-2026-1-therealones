package com.the_real_ones.controller;

import com.the_real_ones.model.Projeto;
import com.the_real_ones.repository.ProjetoRepository;

public class ProjetoController {

    private final ProjetoRepository repository = new ProjetoRepository();

    public String atualizar(Long id, Projeto dadosNovos) {

        Projeto projetoExistente = repository.read(dadosNovos.getTitulo());

        if (projetoExistente == null) {
            return "Projeto não encontrado.";
        }

        if (projetoExistente.getStatus() == Projeto.Status.SUBMETIDO) {
            return "Não é permitido editar um projeto já submetido.";
        }

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

        return "Projeto editado com sucesso!";
    }

}
