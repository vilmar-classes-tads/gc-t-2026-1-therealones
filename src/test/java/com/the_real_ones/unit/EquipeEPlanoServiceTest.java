package com.the_real_ones.unit;

import com.the_real_ones.model.MembroEquipe;
import com.the_real_ones.model.PlanoTrabalho;
import com.the_real_ones.repository.EquipeRepository;
import com.the_real_ones.repository.PlanoTrabalhoRepository;
import com.the_real_ones.service.EquipeService;
import com.the_real_ones.service.PlanoTrabalhoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EquipeEPlanoServiceTest {

    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private PlanoTrabalhoRepository planoTrabalhoRepository;

    @InjectMocks
    private EquipeService equipeService;

    @InjectMocks
    private PlanoTrabalhoService planoTrabalhoService;

    private MembroEquipe membroValido;

    @BeforeEach
    public void setup() {
        membroValido = new MembroEquipe(
            "Igor Vitor",
            "111.111.111-11",
            "Desenvolvedor",
            44
        );
    }

    @Test
    @DisplayName("CT-46: Adicionar membro da equipe com dados válidos")
    public void testAdicionarMembroSucesso() {
        // Arrange (membroValido inicializado no setup)

        // Act & Assert
        assertDoesNotThrow(() -> equipeService.adicionarMembro(membroValido));
        verify(equipeRepository, times(1)).create(membroValido);
    }

    @Test
    @DisplayName("CT-47: Remover membro existente da equipe")
    public void testRemoverMembroSucesso() {
        // Arrange
        String cpf = "111.111.111-11";
        when(equipeRepository.existsByCpf(cpf)).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> equipeService.removerMembro(cpf));
        verify(equipeRepository, times(1)).deleteByCpf(cpf);
    }

    @Test
    @DisplayName("CT-48: Adicionar plano de trabalho para bolsistas/voluntários até o limite máximo permitido (4 planos)")
    public void testAdicionarQuartoPlanoTrabalhoSucesso() {
        // Arrange
        when(planoTrabalhoRepository.countByProjetoId(1L)).thenReturn(3);
        PlanoTrabalho quartoPlano = new PlanoTrabalho(
            "Desenvolvimento Frontend",
            "Bolsista",
            "Criação de telas"
        );

        // Act & Assert
        assertDoesNotThrow(() -> planoTrabalhoService.adicionarPlano(1L, quartoPlano));
        verify(planoTrabalhoRepository, times(1)).create(quartoPlano);
    }

    @Test
    @DisplayName("CT-49: Tentativa de adicionar o 5º plano de trabalho excedendo o limite da regra de negócio")
    public void testAdicionarQuintoPlanoTrabalhoExcedendoLimite() {
        // Arrange
        when(planoTrabalhoRepository.countByProjetoId(1L)).thenReturn(4);
        PlanoTrabalho quintoPlano = new PlanoTrabalho(
            "Testes e Qualidade",
            "Voluntário",
            "Escrita de testes"
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            planoTrabalhoService.adicionarPlano(1L, quintoPlano);
        });

        // Assert
        assertTrue(ex.getMessage().contains("Limite máximo de 4 planos de trabalho já atingido"));
        verify(planoTrabalhoRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-50: Validação de campos obrigatórios ao adicionar membro da equipe")
    public void testAdicionarMembroCamposObrigatoriosVazios() {
        // Arrange
        MembroEquipe membroVazio = new MembroEquipe(
            "",
            "",
            "",
            null
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            equipeService.adicionarMembro(membroVazio);
        });

        // Assert
        assertTrue(ex.getMessage().contains("Campos obrigatórios não preenchidos"));
        verify(equipeRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-51: Tentativa de adicionar membro da equipe com nome vazio")
    public void testAdicionarMembroNomeVazio() {
        // Arrange
        MembroEquipe membroNomeVazio = new MembroEquipe(
            "",
            "111.111.111-11",
            "Desenvolvedor",
            44
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            equipeService.adicionarMembro(membroNomeVazio);
        });

        // Assert
        assertTrue(ex.getMessage().contains("o cadastro não foi realizado, o nome é obrigatório"));
        verify(equipeRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-52: Tentativa de adicionar membro da equipe com CPF vazio")
    public void testAdicionarMembroCpfVazio() {
        // Arrange
        MembroEquipe membroCpfVazio = new MembroEquipe(
            "Igor Vitor",
            "",
            "Desenvolvedor",
            44
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            equipeService.adicionarMembro(membroCpfVazio);
        });

        // Assert
        assertTrue(ex.getMessage().contains("o cadastro não foi realizado, o CPF é obrigatório"));
        verify(equipeRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-53: Tentativa de adicionar membro da equipe com função vazia")
    public void testAdicionarMembroFuncaoVazia() {
        // Arrange
        MembroEquipe membroFuncaoVazia = new MembroEquipe(
            "Igor Vitor",
            "111.111.111-11",
            "",
            44
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            equipeService.adicionarMembro(membroFuncaoVazia);
        });

        // Assert
        assertTrue(ex.getMessage().contains("o cadastro não foi realizado, a função é obrigatório"));
        verify(equipeRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-54: Tentativa de adicionar membro da equipe com carga horária vazia")
    public void testAdicionarMembroCargaHorariaVazia() {
        // Arrange
        MembroEquipe membroChVazia = new MembroEquipe(
            "Igor Vitor",
            "111.111.111-11",
            "Desenvolvedor",
            null
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            equipeService.adicionarMembro(membroChVazia);
        });

        // Assert
        assertTrue(ex.getMessage().contains("o cadastro não foi realizado, a carga horária é obrigatório"));
        verify(equipeRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-55: Tentativa de adicionar membro da equipe com CPF em tamanho inválido")
    public void testAdicionarMembroCpfInvalido() {
        // Arrange
        MembroEquipe membroCpfInvalido = new MembroEquipe(
            "Igor Vitor",
            "111.111.111-1",
            "Desenvolvedor",
            44
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            equipeService.adicionarMembro(membroCpfInvalido);
        });

        // Assert
        assertTrue(ex.getMessage().contains("CPF inválido"));
        verify(equipeRepository, never()).create(any());
    }

    @Test
    @DisplayName("CT-56: Tentativa de adicionar membro da equipe com Carga Horária igual a zero ou negativa")
    public void testAdicionarMembroCargaHorariaInvalida() {
        // Arrange
        MembroEquipe membroChInvalida = new MembroEquipe(
            "Igor Vitor",
            "111.111.111-11",
            "Desenvolvedor",
            0
        );

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            equipeService.adicionarMembro(membroChInvalida);
        });

        // Assert
        assertTrue(ex.getMessage().contains("a carga horária deve ser maior que zero"));
        verify(equipeRepository, never()).create(any());
    }
}