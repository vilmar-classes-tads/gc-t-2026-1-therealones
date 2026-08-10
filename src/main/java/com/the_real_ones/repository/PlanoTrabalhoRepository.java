package com.the_real_ones.repository;

import com.the_real_ones.model.PlanoTrabalho;

public interface PlanoTrabalhoRepository {
    int countByProjetoId(Long projetoId);
    void create(PlanoTrabalho plano);
}
