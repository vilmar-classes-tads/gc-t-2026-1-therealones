package com.the_real_ones.model;

public class Servidor {

    public enum Sexo {
        HOMEM_CIS,
        MULHER_CIS,
        HOMEM_TRANS,
        MULHER_TRANS,
        OUTRO
    }

    // Atributos obrigatórios
    private String nomeCompleto = "";
    private String cpf = "";
    private String emailInstitucional = "";
    private String campus = "";
    private String areaFormacao = "";
    private String titulacao = "";

    // Atributos opcionais
    private String nomeSocial = "";
    private Sexo sexo;
    private String URLLattes = "";
    private String telefone = "";

    public Servidor(String nomeCompleto, String cpf, String emailInstitucional, String areaFormacao, String campus, String titulacao) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.emailInstitucional = emailInstitucional;
        this.areaFormacao = areaFormacao;
        this.campus = campus;
        this.titulacao = titulacao;
    }



    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getEmailInstitucional() {
        return emailInstitucional;
    }

    public void setEmailInstitucional(String emailInstitucional) {
        this.emailInstitucional = emailInstitucional;
    }

    public String getAreaFormacao() {
        return areaFormacao;
    }

    public void setAreaFormacao(String areaFormacao) {
        this.areaFormacao = areaFormacao;
    }

    public String getTitulacao() {
        return titulacao;
    }

    public void setTitulacao(String titulacao) {
        this.titulacao = titulacao;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getURLLattes() {
        return URLLattes;
    }

    public void setURLLattes(String URLLattes) {
        this.URLLattes = URLLattes;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
