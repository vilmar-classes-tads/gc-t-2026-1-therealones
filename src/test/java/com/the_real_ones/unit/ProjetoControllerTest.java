package com.the_real_ones.unit;

import com.the_real_ones.controller.ProjetoController;
import com.the_real_ones.model.Projeto;
import com.the_real_ones.repository.ProjetoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProjetoControllerTest {

    @Test
    @DisplayName("CT-01: Edição permitida caso o projeto esteja no status CORRECAO")
    public void testEdicaoPermitidaParaStatusCorrecao() {
        Projeto dadosNovos = new Projeto("Projeto Teste", "Resumo", "Chave", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        Projeto projetoExistente = new Projeto("Projeto Teste", "Resumo Antigo", "Chave Antiga", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        projetoExistente.setStatus(Projeto.Status.CORRECAO);

        try (MockedConstruction<ProjetoRepository> mocked = mockConstruction(ProjetoRepository.class,
                (mock, context) -> {
                    when(mock.read("Projeto Teste")).thenReturn(projetoExistente);
                })) {

            ProjetoController controller = new ProjetoController();
            String resultado = controller.atualizar(1L, dadosNovos);

            assertEquals("Projeto editado com sucesso!", resultado);
            ProjetoRepository mockRepo = mocked.constructed().get(0);
            verify(mockRepo, times(1)).read("Projeto Teste");
            verify(mockRepo, times(1)).update(projetoExistente);
            assertEquals("Resumo", projetoExistente.getResumo());
        }
    }

    @Test
    @DisplayName("CT-02: Edição bloqueada caso o projeto já esteja SUBMETIDO")
    public void testEdicaoBloqueadaParaStatusSubmetido() {
        Projeto dadosNovos = new Projeto("Projeto Teste", "Resumo", "Chave", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        Projeto projetoExistente = new Projeto("Projeto Teste", "Resumo Antigo", "Chave Antiga", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        projetoExistente.setStatus(Projeto.Status.SUBMETIDO);

        try (MockedConstruction<ProjetoRepository> mocked = mockConstruction(ProjetoRepository.class,
                (mock, context) -> {
                    when(mock.read("Projeto Teste")).thenReturn(projetoExistente);
                })) {

            ProjetoController controller = new ProjetoController();
            String resultado = controller.atualizar(1L, dadosNovos);

            assertEquals("Não é permitido editar um projeto já submetido.", resultado);
            ProjetoRepository mockRepo = mocked.constructed().get(0);
            verify(mockRepo, times(1)).read("Projeto Teste");
            verify(mockRepo, never()).update(any(Projeto.class));
        }
    }

    @Test
    @DisplayName("CT-03: Edição bloqueada caso o projeto esteja VAZIO (Falha esperada indicando defeito)")
    public void testEdicaoBloqueadaParaStatusVazio() {
        Projeto dadosNovos = new Projeto("Projeto Teste", "Resumo", "Chave", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        Projeto projetoExistente = new Projeto("Projeto Teste", "Resumo Antigo", "Chave Antiga", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        projetoExistente.setStatus(Projeto.Status.VAZIO);

        try (MockedConstruction<ProjetoRepository> mocked = mockConstruction(ProjetoRepository.class,
                (mock, context) -> {
                    when(mock.read("Projeto Teste")).thenReturn(projetoExistente);
                })) {

            ProjetoController controller = new ProjetoController();
            String resultado = controller.atualizar(1L, dadosNovos);

            // A Issue exige que a edição SOMENTE seja permitida em CORRECAO. 
            // Logo, para VAZIO deve retornar que a edição não é permitida.
            // O teste irá falhar porque a implementação não bloqueia VAZIO.
            assertEquals("A edição somente é permitida caso o projeto esteja no status de correção.", resultado, 
                "Defeito: A implementação atual permite edição de projeto no status VAZIO.");
        }
    }

    @Test
    @DisplayName("CT-04: Retorno correto ao tentar atualizar um projeto que não existe")
    public void testEdicaoProjetoInexistente() {
        Projeto dadosNovos = new Projeto("Projeto Teste", "Resumo", "Chave", "Público", "Área", "Campus", Projeto.Ods.ODS1);

        try (MockedConstruction<ProjetoRepository> mocked = mockConstruction(ProjetoRepository.class,
                (mock, context) -> {
                    when(mock.read("Projeto Teste")).thenReturn(null);
                })) {

            ProjetoController controller = new ProjetoController();
            String resultado = controller.atualizar(1L, dadosNovos);

            assertEquals("Projeto não encontrado.", resultado);
            ProjetoRepository mockRepo = mocked.constructed().get(0);
            verify(mockRepo, times(1)).read("Projeto Teste");
            verify(mockRepo, never()).update(any(Projeto.class));
        }
    }

    @Test
    @DisplayName("CT-05: Inconsistência de Arquitetura - O id recebido é ignorado e a busca é feita pelo título")
    public void testInconsistenciaBuscaPorIdIgnorado() {
        Projeto dadosNovos = new Projeto("Título do Objeto", "Resumo", "Chave", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        Projeto projetoExistente = new Projeto("Título do Objeto", "Resumo Antigo", "Chave Antiga", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        projetoExistente.setStatus(Projeto.Status.CORRECAO);
        
        Long idIgnorado = 999L;

        try (MockedConstruction<ProjetoRepository> mocked = mockConstruction(ProjetoRepository.class,
                (mock, context) -> {
                    when(mock.read("Título do Objeto")).thenReturn(projetoExistente);
                })) {

            ProjetoController controller = new ProjetoController();
            controller.atualizar(idIgnorado, dadosNovos);

            ProjetoRepository mockRepo = mocked.constructed().get(0);
            // Verifica que o repositório foi chamado usando o Título e NUNCA usou o ID passado
            verify(mockRepo).read("Título do Objeto");
        }
    }
}
