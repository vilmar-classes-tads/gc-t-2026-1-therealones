package com.the_real_ones.service;

import com.the_real_ones.model.PlanoTrabalho;
import com.the_real_ones.repository.PlanoTrabalhoRepository;

public class PlanoTrabalhoService {
    private PlanoTrabalhoRepository planoTrabalhoRepository;

    public void adicionarPlano(Long projetoId, PlanoTrabalho plano) {
        if (planoTrabalhoRepository.countByProjetoId(projetoId) >= 4) {
            throw new IllegalArgumentException("Limite máximo de 4 planos de trabalho já atingido");
        }
        planoTrabalhoRepository.create(plano);
    }
}
