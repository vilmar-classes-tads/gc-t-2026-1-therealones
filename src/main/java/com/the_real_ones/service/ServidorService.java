package com.the_real_ones.service;

import com.the_real_ones.model.Servidor;
import com.the_real_ones.repository.ServidorRepository;

public class ServidorService {

    private ServidorRepository repository;

    public ServidorService(ServidorRepository repository) {
        this.repository = repository;
    }

    public boolean cadastrarServidor(Servidor servidor) {
        if (servidor == null) {
            throw new IllegalArgumentException("Servidor inválido");
        }

        // Validações dos dados do servidor
        validarServidor(servidor);

        // Validações de duplicidade no repositório
        if (repository.existsByCpf(servidor.getCpf())) {
            throw new IllegalArgumentException("Servidor já existente, CPF já cadastrado");
        }

        if (repository.existsByEmail(servidor.getEmailInstitucional())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        repository.create(servidor);
        System.out.println("\nCadastro concluído com sucesso");
        return true;
    }

    private void validarServidor(Servidor servidor) {
        if (servidor.getNomeCompleto() == null || servidor.getNomeCompleto().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (servidor.getCpf() == null || servidor.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }
        if (!isCpfValido(servidor.getCpf())) {
            throw new IllegalArgumentException("CPF inválido");
        }
        if (servidor.getEmailInstitucional() == null || servidor.getEmailInstitucional().trim().isEmpty()) {
            throw new IllegalArgumentException("e-mail é obrigatório");
        }
        if (servidor.getSenha() == null || servidor.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("senha é obrigatória");
        }
        if (servidor.getSenha().length() < 6) {
            throw new IllegalArgumentException("senha deve ter no mínimo 6 caracteres");
        }
        if (servidor.getCampus() == null || servidor.getCampus().trim().isEmpty()) {
            throw new IllegalArgumentException("campus é obrigatório");
        }
        if (servidor.getAreaFormacao() == null || servidor.getAreaFormacao().trim().isEmpty()) {
            throw new IllegalArgumentException("área de Formação é obrigatória");
        }
        if (servidor.getTitulacao() == null || servidor.getTitulacao().trim().isEmpty()) {
            throw new IllegalArgumentException("titulação é obrigatória");
        }
    }

    private boolean isCpfValido(String cpf) {
        if (cpf == null) {
            return false;
        }

        String cpfNumeros = cpf.replaceAll("\\D", "");
        return cpfNumeros.length() == 11;
    }

    public Servidor login(String email, String senha) {
        Servidor servidor = repository.readByEmail(email);
        if (servidor == null) {
            throw new IllegalArgumentException("E-mail não encontrado");
        }
        if (!servidor.verificarSenha(senha)) {
            throw new IllegalArgumentException("Senha incorreta");
        }
        return servidor;
    }
}