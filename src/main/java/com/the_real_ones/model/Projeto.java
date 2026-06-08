package com.the_real_ones.model;

public class Projeto{
    public enum Ods {
        ODS1,
        ODS2,
        ODS3,
        ODS4,
        ODS5,
        ODS6,
        ODS7,
        ODS8,
        ODS9,
        ODS10,
        ODS11,
        ODS12,
        ODS13,
        ODS14,
        ODS15,
        ODS16
    }

    public enum Status {
        VAZIO,
        SUBMETIDO,
        CORRECAO
    }
    
    private String titulo;
    private String resumo;
    private String palavraChave;
    private String publicoAlvo;
    private String areaTematica;
    private String campos;
    private Ods ods;
    private boolean termos = false;
    public Status status;


    public Projeto(String titulo, String resumo, String palavraChave, String publicoAlvo, String areaTematica, String campos, Ods ods) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.palavraChave = palavraChave;
        this.publicoAlvo = publicoAlvo;
        this.areaTematica = areaTematica;
        this.campos = campos;
        this.ods = ods;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getPalavraChave() {
        return palavraChave;
    }

    public void setPalavraChave(String palavraChave) {
        this.palavraChave = palavraChave;
    }

    public String getPublicoAlvo() {
        return publicoAlvo;
    }

    public void setPublicoAlvo(String publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }

    public String getAreaTematica() {
        return areaTematica;
    }

    public void setAreaTematica(String areaTematica) {
        this.areaTematica = areaTematica;
    }

    public String getCampos() {
        return campos;
    }

    public void setCampos(String campos) {
        this.campos = campos;
    }

    public Ods getOds() {
        return ods;
    }

    public void setOds(Ods ods) {
        this.ods = ods;
    }

    public boolean getTermos() {
        return termos;
    }

    public void setTermos(boolean termos) {
        this.termos = termos;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}