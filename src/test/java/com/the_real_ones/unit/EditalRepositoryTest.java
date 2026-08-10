package com.the_real_ones.unit;

import com.the_real_ones.model.Edital;
import com.the_real_ones.repository.EditalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EditalRepositoryTest {

    private EditalRepository editalRepository;

    @BeforeEach
    public void setup() {
        editalRepository = new EditalRepository();
    }

    @Test
    @DisplayName("CT-18a: Listagem de editais retornando a lista vazia quando não há itens cadastrados")
    public void testListarEditaisVazio() {
        // Arrange (Setup inicializa repositório vazio)

        // Act
        ArrayList<Edital> list = editalRepository.readAll();

        // Assert
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("CT-18b: Listagem de editais com sucesso quando existem itens cadastrados")
    public void testListarEditaisComSucesso() {
        // Arrange
        LocalDate inicioSubmissao = LocalDate.of(2026, 8, 1);
        LocalDate fimSubmissao = LocalDate.of(2026, 8, 10);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 8, 11);
        LocalDate fimAvaliacao = LocalDate.of(2026, 8, 20);
        
        Edital edital1 = new Edital("Edital 1", 1, 2026, inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao);
        Edital edital2 = new Edital("Edital 2", 2, 2026, inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao);
        
        editalRepository.create(edital1);
        editalRepository.create(edital2);

        // Act
        ArrayList<Edital> list = editalRepository.readAll();

        // Assert
        assertNotNull(list);
        assertEquals(2, list.size());
        assertTrue(list.contains(edital1));
        assertTrue(list.contains(edital2));
    }
}
