package com.the_real_ones.unit;

import com.the_real_ones.model.Edital;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EditalTest {

    @Test
    @DisplayName("CT-14: Criação de Edital com períodos válidos")
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
    @DisplayName("CT-15: Tentativa de criação de Edital com Data de Início de Submissão maior que a Data de Fim de Submissão")
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
    @DisplayName("CT-16: Tentativa de criação de Edital com Data de Início de Avaliação maior que a Data de Fim de Avaliação")
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

    @Test
    @DisplayName("CT-17: Edição de edital com sucesso")
    public void testEdicaoEditalSucesso() {
        LocalDate inicioSubmissao = LocalDate.of(2026, 8, 1);
        LocalDate fimSubmissao = LocalDate.of(2026, 8, 10);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 8, 11);
        LocalDate fimAvaliacao = LocalDate.of(2026, 8, 20);

        Edital edital = new Edital("Edital PIVICT 2026", 1, 2026, inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao);

        LocalDate novoFimSubmissao = LocalDate.of(2026, 9, 5);
        LocalDate novoInicioAvaliacao = LocalDate.of(2026, 9, 6);
        LocalDate novoFimAvaliacao = LocalDate.of(2026, 9, 20);

        assertDoesNotThrow(() -> {
            edital.atualizar("Edital PIVICT 2026 - Retificado", 1, 2026, inicioSubmissao, novoFimSubmissao, novoInicioAvaliacao, novoFimAvaliacao);
        });

        assertEquals("Edital PIVICT 2026 - Retificado", edital.getTitulo());
        assertEquals(novoFimSubmissao, edital.getDataFimSubmissao());
    }

    @Test
    @DisplayName("CT-19: Tentativa de cadastro com data inicial de avaliação anterior a data final de submissão")
    public void testCriacaoEditalConflitoPeriodos() {
        LocalDate inicioSubmissao = LocalDate.of(2026, 8, 1);
        LocalDate fimSubmissao = LocalDate.of(2026, 8, 31);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 8, 15);
        LocalDate fimAvaliacao = LocalDate.of(2026, 9, 15);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new Edital("Edital PIBIEX", 1, 2026, inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao);
        });

        assertTrue(ex.getMessage().contains("A data inicial da avaliação não pode ser anterior à data final de submissão."));
    }

    @Test
    @DisplayName("CT-20: Tentativa de cadastro sem preencher campos obrigatórios")
    public void testCriacaoEditalCamposNulos() {
        LocalDate inicioSubmissao = LocalDate.of(2026, 8, 1);
        LocalDate fimSubmissao = LocalDate.of(2026, 8, 31);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 9, 1);
        LocalDate fimAvaliacao = LocalDate.of(2026, 9, 15);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new Edital(null, null, null, inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao);
        });

        assertTrue(ex.getMessage().contains("O campo Título é obrigatório."));
    }

    @Test
    @DisplayName("CT-21: Tentativa de cadastro de edital com Título vazio")
    public void testCriacaoEditalTituloVazio() {
        LocalDate inicioSubmissao = LocalDate.of(2026, 8, 1);
        LocalDate fimSubmissao = LocalDate.of(2026, 8, 31);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 9, 1);
        LocalDate fimAvaliacao = LocalDate.of(2026, 9, 15);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new Edital("", 5, 2026, inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao);
        });

        assertTrue(ex.getMessage().contains("O campo Título é obrigatório."));
    }

    @Test
    @DisplayName("CT-22: Tentativa de cadastro de edital com Número vazio")
    public void testCriacaoEditalNumeroVazio() {
        LocalDate inicioSubmissao = LocalDate.of(2026, 8, 1);
        LocalDate fimSubmissao = LocalDate.of(2026, 8, 31);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 9, 1);
        LocalDate fimAvaliacao = LocalDate.of(2026, 9, 15);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new Edital("Edital", null, 2026, inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao);
        });

        assertTrue(ex.getMessage().contains("O campo Número é obrigatório."));
    }

    @Test
    @DisplayName("CT-23: Tentativa de cadastro de edital com Ano vazio")
    public void testCriacaoEditalAnoVazio() {
        LocalDate inicioSubmissao = LocalDate.of(2026, 8, 1);
        LocalDate fimSubmissao = LocalDate.of(2026, 8, 31);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 9, 1);
        LocalDate fimAvaliacao = LocalDate.of(2026, 9, 15);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new Edital("Edital Cultura", 6, null, inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao);
        });

        assertTrue(ex.getMessage().contains("O campo Ano é obrigatório."));
    }

    @Test
    @DisplayName("CT-24: Tentativa de cadastro de edital com Data de Início da Submissão vazia")
    public void testCriacaoEditalInicioSubmissaoVazia() {
        LocalDate fimSubmissao = LocalDate.of(2026, 8, 31);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 9, 1);
        LocalDate fimAvaliacao = LocalDate.of(2026, 9, 15);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new Edital("Edital Esporte", 7, 2026, null, fimSubmissao, inicioAvaliacao, fimAvaliacao);
        });

        assertTrue(ex.getMessage().contains("A Data de Início da Submissão é obrigatória."));
    }

    @Test
    @DisplayName("CT-25: Tentativa de cadastro de edital com Data de Fim da Submissão vazia")
    public void testCriacaoEditalFimSubmissaoVazia() {
        LocalDate inicioSubmissao = LocalDate.of(2026, 8, 1);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 9, 1);
        LocalDate fimAvaliacao = LocalDate.of(2026, 9, 15);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new Edital("Edital TI", 8, 2026, inicioSubmissao, null, inicioAvaliacao, fimAvaliacao);
        });

        assertTrue(ex.getMessage().contains("A Data de Fim da Submissão é obrigatória."));
    }

    @Test
    @DisplayName("CT-26: Tentativa de cadastro de edital com Data de Início da Avaliação vazia")
    public void testCriacaoEditalInicioAvaliacaoVazia() {
        LocalDate inicioSubmissao = LocalDate.of(2026, 8, 1);
        LocalDate fimSubmissao = LocalDate.of(2026, 8, 31);
        LocalDate fimAvaliacao = LocalDate.of(2026, 9, 15);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new Edital("Edital Social", 9, 2026, inicioSubmissao, fimSubmissao, null, fimAvaliacao);
        });

        assertTrue(ex.getMessage().contains("A Data de Início da Avaliação é obrigatória."));
    }

    @Test
    @DisplayName("CT-27: Tentativa de cadastro de edital com Data de Fim da Avaliação vazia")
    public void testCriacaoEditalFimAvaliacaoVazia() {
        LocalDate inicioSubmissao = LocalDate.of(2026, 8, 1);
        LocalDate fimSubmissao = LocalDate.of(2026, 8, 31);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 9, 1);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new Edital("Edital Digital", 10, 2026, inicioSubmissao, fimSubmissao, inicioAvaliacao, null);
        });

        assertTrue(ex.getMessage().contains("A Data de Fim da Avaliação é obrigatória."));
    }

    @Test
    @DisplayName("CT-28: Tentativa de edição apagando campo obrigatório (Título)")
    public void testEdicaoEditalTituloVazio() {
        LocalDate inicioSubmissao = LocalDate.of(2026, 8, 1);
        LocalDate fimSubmissao = LocalDate.of(2026, 8, 10);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 8, 11);
        LocalDate fimAvaliacao = LocalDate.of(2026, 8, 20);

        Edital edital = new Edital("Edital Base", 1, 2026, inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            edital.atualizar("", 1, 2026, inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao);
        });

        assertTrue(ex.getMessage().contains("O campo Título é obrigatório."));
    }

    @Test
    @DisplayName("CT-29: Tentativa de edição de edital inserindo intervalo de datas inválido")
    public void testEdicaoEditalDatasInvalidas() {
        LocalDate inicioSubmissao = LocalDate.of(2026, 8, 10);
        LocalDate fimSubmissao = LocalDate.of(2026, 8, 20);
        LocalDate inicioAvaliacao = LocalDate.of(2026, 8, 21);
        LocalDate fimAvaliacao = LocalDate.of(2026, 8, 30);

        Edital edital = new Edital("Edital Teste", 1, 2026, inicioSubmissao, fimSubmissao, inicioAvaliacao, fimAvaliacao);

        LocalDate novoFimSubmissao = LocalDate.of(2026, 8, 1); // Anterior ao inicioSubmissao (10/08)

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            edital.atualizar("Edital Teste", 1, 2026, inicioSubmissao, novoFimSubmissao, inicioAvaliacao, fimAvaliacao);
        });

        assertTrue(ex.getMessage().contains("Período inválido: a data inicial não pode ser depois da data final."));
    }
}
