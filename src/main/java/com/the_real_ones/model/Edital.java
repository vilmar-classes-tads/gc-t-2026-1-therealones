package com.the_real_ones.model;

import java.time.LocalDate;

public class Edital {
    private String titulo;
    private Integer numero;
    private Integer ano;
    private LocalDate dataInicioSubmissao;
    private LocalDate dataFimSubmissao;
    private LocalDate dataInicioAvaliacao;
    private LocalDate dataFimAvaliacao;

    public Edital(String titulo, Integer numero, Integer ano, LocalDate dataInicioSubmissao, 
                  LocalDate dataFimSubmissao, LocalDate dataInicioAvaliacao, LocalDate dataFimAvaliacao) {
        validarCamposObrigatorios(titulo, numero, ano, dataInicioSubmissao, dataFimSubmissao, dataInicioAvaliacao, dataFimAvaliacao);
        validarPeriodo(dataInicioSubmissao, dataFimSubmissao);
        validarPeriodo(dataInicioAvaliacao, dataFimAvaliacao);
        validarConflitoPeriodos(dataFimSubmissao, dataInicioAvaliacao);
        
        this.titulo = titulo;
        this.numero = numero;
        this.ano = ano;
        this.dataInicioSubmissao = dataInicioSubmissao;
        this.dataFimSubmissao = dataFimSubmissao;
        this.dataInicioAvaliacao = dataInicioAvaliacao;
        this.dataFimAvaliacao = dataFimAvaliacao;
    }

    private void validarCamposObrigatorios(String titulo, Integer numero, Integer ano, LocalDate dataInicioSubmissao, 
                                           LocalDate dataFimSubmissao, LocalDate dataInicioAvaliacao, LocalDate dataFimAvaliacao) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo Título é obrigatório.");
        }
        if (numero == null) {
            throw new IllegalArgumentException("O campo Número é obrigatório.");
        }
        if (ano == null) {
            throw new IllegalArgumentException("O campo Ano é obrigatório.");
        }
        if (dataInicioSubmissao == null) {
            throw new IllegalArgumentException("A Data de Início da Submissão é obrigatória.");
        }
        if (dataFimSubmissao == null) {
            throw new IllegalArgumentException("A Data de Fim da Submissão é obrigatória.");
        }
        if (dataInicioAvaliacao == null) {
            throw new IllegalArgumentException("A Data de Início da Avaliação é obrigatória.");
        }
        if (dataFimAvaliacao == null) {
            throw new IllegalArgumentException("A Data de Fim da Avaliação é obrigatória.");
        }
    }

    private void validarPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio != null && fim != null && inicio.isAfter(fim)) {
            throw new IllegalArgumentException("Período inválido: a data inicial não pode ser depois da data final.");
        }
    }
    
    private void validarConflitoPeriodos(LocalDate fimSubmissao, LocalDate inicioAvaliacao) {
        if (fimSubmissao != null && inicioAvaliacao != null && inicioAvaliacao.isBefore(fimSubmissao)) {
            throw new IllegalArgumentException("A data inicial da avaliação não pode ser anterior à data final de submissão.");
        }
    }

    public void atualizar(String titulo, Integer numero, Integer ano, LocalDate dataInicioSubmissao, 
                          LocalDate dataFimSubmissao, LocalDate dataInicioAvaliacao, LocalDate dataFimAvaliacao) {
        validarCamposObrigatorios(titulo, numero, ano, dataInicioSubmissao, dataFimSubmissao, dataInicioAvaliacao, dataFimAvaliacao);
        validarPeriodo(dataInicioSubmissao, dataFimSubmissao);
        validarPeriodo(dataInicioAvaliacao, dataFimAvaliacao);
        validarConflitoPeriodos(dataFimSubmissao, dataInicioAvaliacao);
        
        this.titulo = titulo;
        this.numero = numero;
        this.ano = ano;
        this.dataInicioSubmissao = dataInicioSubmissao;
        this.dataFimSubmissao = dataFimSubmissao;
        this.dataInicioAvaliacao = dataInicioAvaliacao;
        this.dataFimAvaliacao = dataFimAvaliacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public LocalDate getDataInicioSubmissao() {
        return dataInicioSubmissao;
    }

    public void setDataInicioSubmissao(LocalDate dataInicioSubmissao) {
        this.dataInicioSubmissao = dataInicioSubmissao;
    }

    public LocalDate getDataFimSubmissao() {
        return dataFimSubmissao;
    }

    public void setDataFimSubmissao(LocalDate dataFimSubmissao) {
        this.dataFimSubmissao = dataFimSubmissao;
    }

    public LocalDate getDataInicioAvaliacao() {
        return dataInicioAvaliacao;
    }

    public void setDataInicioAvaliacao(LocalDate dataInicioAvaliacao) {
        this.dataInicioAvaliacao = dataInicioAvaliacao;
    }

    public LocalDate getDataFimAvaliacao() {
        return dataFimAvaliacao;
    }

    public void setDataFimAvaliacao(LocalDate dataFimAvaliacao) {
        this.dataFimAvaliacao = dataFimAvaliacao;
    }
}
