package com.the_real_ones.controller;

import com.the_real_ones.model.Projeto;
import com.the_real_ones.repository.ProjetoRepository;

public class ProjetoController {

    private final ProjetoRepository repository = new ProjetoRepository();

    private String validarCamposObrigatorios(Projeto projeto) {
        if (projeto.getTitulo() == null || projeto.getTitulo().isEmpty()) return "O campo Título é obrigatório.";
        if (projeto.getResumo() == null || projeto.getResumo().isEmpty()) return "O campo Resumo é obrigatório.";
        if (projeto.getPalavraChave() == null || projeto.getPalavraChave().isEmpty()) return "O campo Palavras-chave é obrigatório.";
        if (projeto.getPublicoAlvo() == null || projeto.getPublicoAlvo().isEmpty()) return "O campo Público-alvo é obrigatório.";
        if (projeto.getAreaTematica() == null || projeto.getAreaTematica().isEmpty()) return "O campo Área Temática é obrigatório.";
        if (projeto.getCampos() == null || projeto.getCampos().isEmpty()) return "O campo Campus é obrigatório.";
        if (projeto.getOds() == null) return "O campo ODS é obrigatório.";
        return null;
    }

    public String atualizar(Long id, Projeto dadosNovos) {

        String erroValidacao = validarCamposObrigatorios(dadosNovos);
        if (erroValidacao != null) {
            return erroValidacao;
        }

        Projeto projetoExistente = repository.read(dadosNovos.getTitulo());

        if (projetoExistente == null) {
            return "Projeto não encontrado.";
        }

        if (projetoExistente.getStatus() == Projeto.Status.SUBMETIDO) {
            return "Não é permitido editar um projeto já submetido.";
        }

        if (projetoExistente.getStatus() != Projeto.Status.CORRECAO) {
            return "A edição somente é permitida caso o projeto esteja no status de correção.";
        }

        projetoExistente.editarProjeto(
                dadosNovos.getTitulo(),
                dadosNovos.getResumo(),
                dadosNovos.getPalavraChave(),
                dadosNovos.getPublicoAlvo(),
                dadosNovos.getAreaTematica(),
                dadosNovos.getCampos(),
                dadosNovos.getOds());

        repository.update(projetoExistente);

        return "Projeto editado com sucesso!";
    }

    public String submeter(Projeto projeto) {
        String erroValidacao = validarCamposObrigatorios(projeto);
        if (erroValidacao != null) {
            return erroValidacao;
        }
        
        if (!projeto.getTermos()) {
            return "O Termo de Compromisso é obrigatório.";
        }
        
        projeto.setStatus(Projeto.Status.SUBMETIDO);
        repository.create(projeto);
        return "Projeto submetido com sucesso!";
    }

}
