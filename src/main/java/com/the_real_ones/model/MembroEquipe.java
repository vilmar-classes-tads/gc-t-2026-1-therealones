package com.the_real_ones.model;

public class MembroEquipe {
    private String nome;
    private String cpf;
    private String funcao;
    private Integer cargaHoraria;

    public MembroEquipe(String nome, String cpf, String funcao, Integer cargaHoraria) {
        this.nome = nome;
        this.cpf = cpf;
        this.funcao = funcao;
        this.cargaHoraria = cargaHoraria;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getFuncao() { return funcao; }
    public void setFuncao(String funcao) { this.funcao = funcao; }
    public Integer getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(Integer cargaHoraria) { this.cargaHoraria = cargaHoraria; }
}
