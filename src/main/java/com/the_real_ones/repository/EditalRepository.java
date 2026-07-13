package com.the_real_ones.repository;

import com.the_real_ones.model.Edital;

import java.util.ArrayList;

public class EditalRepository {

    private final ArrayList<Edital> editais = new ArrayList<>();

    public void create(Edital e) {
        editais.add(e);
    }

    public Edital readByNumeroEAno(int numero, int ano) {
        for (Edital e : editais) {
            if (e.getNumero() == numero && e.getAno() == ano) {
                return e;
            }
        }
        return null;
    }

    public void update(Edital e) {
        for (Edital ed : editais) {
            if (ed.getNumero() == e.getNumero() && ed.getAno() == e.getAno()) {
                ed.setTitulo(e.getTitulo());
                ed.setDataInicioSubmissao(e.getDataInicioSubmissao());
                ed.setDataFimSubmissao(e.getDataFimSubmissao());
                ed.setDataInicioAvaliacao(e.getDataInicioAvaliacao());
                ed.setDataFimAvaliacao(e.getDataFimAvaliacao());
                
                return;
            }
        }
    }

    public void delete(Edital e) {
        editais.remove(e);
    }

    public ArrayList<Edital> readAll() {
        return new ArrayList<>(editais);
    }

}
