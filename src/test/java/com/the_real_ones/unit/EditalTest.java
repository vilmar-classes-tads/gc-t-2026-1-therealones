package com.the_real_ones.unit;

import com.the_real_ones.model.Edital;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EditalTest {

    @Test
    @DisplayName("CT-01: Criação de Edital com períodos válidos")
    public void testCriacaoEditalSucesso() {
        // Arrange
        LocalDate inicioSubmissao = LocalDate.of(2026, 8, 1);
        LocalDate fimSubmissao = LocalDate.of(2026, 8, 10);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 8, 11);
        LocalDate fimAvaliacao = LocalDate.of(2026, 8, 20);

        // Act & Assert
        assertDoesNotThrow(() -> {
            new Edital("Edital de Teste", 1, 2026, inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao);
        });
    }

    @Test
    @DisplayName("CT-02: Tentativa de criação de Edital com Data de Início de Submissão maior que a Data de Fim de Submissão")
    public void testCriacaoEditalSubmissaoInvalida() {
        // Arrange
        LocalDate inicioSubmissao = LocalDate.of(2026, 8, 10);
        LocalDate fimSubmissao = LocalDate.of(2026, 8, 1); // Inválido
        LocalDate inicioAvaliacao = LocalDate.of(2026, 8, 11);
        LocalDate fimAvaliacao = LocalDate.of(2026, 8, 20);

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new Edital("Edital de Teste", 1, 2026, inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao);
        });

        // Assert
        assertTrue(ex.getMessage().contains("Período inválido: a data inicial não pode ser depois da data final."));
    }

    @Test
    @DisplayName("CT-03: Tentativa de criação de Edital com Data de Início de Avaliação maior que a Data de Fim de Avaliação")
    public void testCriacaoEditalAvaliacaoInvalida() {
        // Arrange
        LocalDate inicioSubmissao = LocalDate.of(2026, 8, 1);
        LocalDate fimSubmissao = LocalDate.of(2026, 8, 10);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 8, 20);
        LocalDate fimAvaliacao = LocalDate.of(2026, 8, 11); // Inválido

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new Edital("Edital de Teste", 1, 2026, inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao);
        });

        // Assert
        assertTrue(ex.getMessage().contains("Período inválido: a data inicial não pode ser depois da data final."));
    }
}
