package com.the_real_ones.repository;

import com.the_real_ones.model.Projeto;

import java.util.ArrayList;

public class ServidorRepository {

    private final ArrayList<Projeto> projeto = new ArrayList<>();

    public void create(Projeto s) {
        projeto.add(s);
    }

    public Projeto read(String titulo){
        for(Projeto p : projeto){
            if(p.equals(titulo)) {
                return p;
            }
        }

        return null;
    }

    public void update(Projeto p) {
        for(Projeto proj : projeto) {
            if(proj.getTitulo().equals(p.getTitulo())) {
                proj.setTitulo(p.getTitulo());
                proj.setResumo(p.getResumo());
                proj.setPalavraChave(p.getPalavraChave());
                proj.setPublicoAlvo(p.getPublicoAlvo());
                proj.setAreaTematica(p.getAreaTematica());
                proj.setCampos(p.getCampos());
                proj.setOds(p.getOds());

                return;
            }
        }
    }

    public void delete(Proj p) {
        projeto.remove(p);
    }

    public ArrayList<Projeto> readAll() {
        return  new ArrayList<>(projetos);
    }

}
