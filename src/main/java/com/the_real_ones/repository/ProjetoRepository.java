package com.the_real_ones.repository;

import com.the_real_ones.model.Projeto;

import java.util.ArrayList;
import java.util.List;

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

    // --- Stubs para Testes ---

    public ArrayList<Projeto> filtrarPorArea(String area) {
        return new ArrayList<>();
    }

    public ArrayList<Projeto> readByCampus(String campus) {
        return new ArrayList<>();
    }

    public boolean possuiArquivos(Long id) {
        return false;
    }

    public ArrayList<Projeto> filtrarMultiplos(String edital, String campus, String area, Projeto.Status status) {
        return new ArrayList<>();
    }

    public ArrayList<Projeto> readByCampusEStatusPermitidos(String campus, List<Object> status) {
        return new ArrayList<>();
    }

    public ArrayList<Projeto> filtrarComPaginacao(String status, int pagina, int tamanho) {
        return new ArrayList<>();
    }
}
