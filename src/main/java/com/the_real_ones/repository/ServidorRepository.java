package com.the_real_ones.repository;

import com.the_real_ones.model.Servidor;

import java.util.ArrayList;

public class ServidorRepository {

    private final ArrayList<Servidor> servidores = new ArrayList<>();

    public void create(Servidor s) {
        servidores.add(s);
    }

    public Servidor readByCpf(String cpf) {
        for(Servidor s : servidores){
            if(s.getCpf().equals(cpf)) {
                return s;
            }
        }
        return null;
    }

    public void update(Servidor s) {
        for(Servidor serv : servidores) {
            if(serv.getCpf().equals(s.getCpf())) {
                serv.setNomeCompleto(s.getNomeCompleto());
                serv.setEmailInstitucional(s.getEmailInstitucional());
                serv.setCampus(s.getCampus());
                serv.setAreaFormacao(s.getAreaFormacao());
                serv.setTitulacao(s.getTitulacao());
                serv.setNomeSocial(s.getNomeSocial());
                serv.setSexo(s.getSexo());
                serv.setURLLattes(s.getURLLattes());
                serv.setTelefone(s.getTelefone());

                return;
            }
        }
    }

    public void delete(Servidor s) {
        servidores.remove(s);
    }

    public ArrayList<Servidor> readAll() {
        return  new ArrayList<>(servidores);
    }

    public boolean existsByEmail(String email) {
        for(Servidor s : servidores) {
            if(s.getEmailInstitucional().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean existsByCpf(String cpf) {
        for(Servidor s : servidores) {
            if(s.getCpf().equals(cpf)) {
                return true;
            }
        }
        return false;
    }
}
