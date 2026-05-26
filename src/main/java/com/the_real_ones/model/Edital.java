package com.the_real_ones.model;

import java.time.LocalDate;

public class Edital {
    private String titulo;
    private int numero;
    private int ano;
    private LocalDate dataInicioSubmissao;
    private LocalDate dataFimSubmissao;
    private LocalDate dataInicioAvaliacao;
    private LocalDate dataFimAvaliacao;

    public Edital(String titulo, int numero, int ano, LocalDate dataInicioSubmissao, 
                  LocalDate dataFimSubmissao, LocalDate dataInicioAvaliacao, LocalDate dataFimAvaliacao) {
        validarPeriodo(dataInicioSubmissao, dataFimSubmissao);
        validarPeriodo(dataInicioAvaliacao, dataFimAvaliacao);
        this.titulo = titulo;
        this.numero = numero;
        this.ano = ano;
        this.dataInicioSubmissao = dataInicioSubmissao;
        this.dataFimSubmissao = dataFimSubmissao;
        this.dataInicioAvaliacao = dataInicioAvaliacao;
        this.dataFimAvaliacao = dataFimAvaliacao;
    }

    private void validarPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("Período inválido: a data inicial não pode ser depois da data final.");
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
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
