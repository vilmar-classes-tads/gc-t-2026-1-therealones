package com.the_real_ones.repository;

import com.the_real_ones.model.Projeto;

import java.util.ArrayList;

public class ProjetoRepository {

private final ArrayList<Projeto> projetos = new ArrayList<>();

public void create(Projeto projeto) {
    projetos.add(projeto);
}

public Projeto read(String titulo) {
    for (Projeto projeto : projetos) {
        if (projeto.getTitulo().equals(titulo)) {
            return projeto;
        }
    }

    return null;
}

public void update(Projeto projetoAtualizado) {
    for (Projeto projeto : projetos) {
        if (projeto.getTitulo().equals(projetoAtualizado.getTitulo())) {

            projeto.setTitulo(projetoAtualizado.getTitulo());
            projeto.setResumo(projetoAtualizado.getResumo());
            projeto.setPalavraChave(projetoAtualizado.getPalavraChave());
            projeto.setPublicoAlvo(projetoAtualizado.getPublicoAlvo());
            projeto.setAreaTematica(projetoAtualizado.getAreaTematica());
            projeto.setCampos(projetoAtualizado.getCampos());
            projeto.setOds(projetoAtualizado.getOds());

            return;
        }
    }
}

public void delete(Projeto projeto) {
    projetos.remove(projeto);
}

public ArrayList<Projeto> readAll() {
    return new ArrayList<>(projetos);
}

}
