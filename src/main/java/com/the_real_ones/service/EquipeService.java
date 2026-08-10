package com.the_real_ones.service;

import com.the_real_ones.model.MembroEquipe;
import com.the_real_ones.repository.EquipeRepository;

public class EquipeService {
    private EquipeRepository equipeRepository;

    public void adicionarMembro(MembroEquipe membro) {
        boolean nomeVazio = membro.getNome() == null || membro.getNome().trim().isEmpty();
        boolean cpfVazio = membro.getCpf() == null || membro.getCpf().trim().isEmpty();
        boolean funcaoVazia = membro.getFuncao() == null || membro.getFuncao().trim().isEmpty();
        boolean chVazia = membro.getCargaHoraria() == null;

        if (nomeVazio && cpfVazio && funcaoVazia && chVazia) {
            throw new IllegalArgumentException("Campos obrigatórios não preenchidos");
        }

        if (nomeVazio) {
            throw new IllegalArgumentException("o cadastro não foi realizado, o nome é obrigatório");
        }
        if (cpfVazio) {
            throw new IllegalArgumentException("o cadastro não foi realizado, o CPF é obrigatório");
        }
        if (funcaoVazia) {
            throw new IllegalArgumentException("o cadastro não foi realizado, a função é obrigatório");
        }
        if (chVazia) {
            throw new IllegalArgumentException("o cadastro não foi realizado, a carga horária é obrigatório");
        }
        if (membro.getCpf().length() != 14) {
            throw new IllegalArgumentException("CPF inválido");
        }
        if (membro.getCargaHoraria() <= 0) {
            throw new IllegalArgumentException("a carga horária deve ser maior que zero");
        }

        equipeRepository.create(membro);
    }

    public void removerMembro(String cpf) {
        if (equipeRepository.existsByCpf(cpf)) {
            equipeRepository.deleteByCpf(cpf);
        }
    }
}
