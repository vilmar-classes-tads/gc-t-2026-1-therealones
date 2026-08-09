package com.the_real_ones.repository;

import com.the_real_ones.model.MembroEquipe;

public interface EquipeRepository {
    void create(MembroEquipe membro);
    boolean existsByCpf(String cpf);
    void deleteByCpf(String cpf);
}
