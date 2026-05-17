package com.the_real_ones.service;

import com.the_real_ones.model.Servidor;
import com.the_real_ones.repository.ServidorRepository;

public class ServidorService {

    private ServidorRepository repository;

    public ServidorService(ServidorRepository repository) {
        this.repository = repository;
    }

    public boolean cadastrarServidor(Servidor servidor) {

        if(repository.existsByCpf(servidor.getCpf())) {
            throw new IllegalArgumentException("Servidor já existente, CPF já cadastrado");
        }
        if(repository.existsByEmail(servidor.getEmailInstitucional())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        repository.create(servidor);
        return true;
    }
}
