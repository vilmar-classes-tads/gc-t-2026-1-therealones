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

    // === EXISTING TESTS RENAMED ===

    @Test
    @DisplayName("CT-32 / CT-46: Edição de projetos já submetidos com o status de correção / Edição de projeto submetido que possui status Em Correção")
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
    @DisplayName("CT-43: Bloqueio de edição para projeto já submetido")
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
    @DisplayName("CT-31: Bloquear a edição de projetos já cadastrados")
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

            assertEquals("A edição somente é permitida caso o projeto esteja no status de correção.", resultado, 
                "Defeito: A implementação atual permite edição de projeto no status VAZIO.");
        }
    }

    // === MISSING TESTS IMPLEMENTATION ===

    // -- Submissão validations --

    @Test
    @DisplayName("CT-33: Tentativa de submissão de projeto com Título vazio")
    public void testSubmissaoComTituloVazio() {
        Projeto projeto = new Projeto("", "Resumo válido", "Palavra-chave", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        ProjetoController controller = new ProjetoController();
        String resultado = controller.submeter(projeto);
        assertEquals("O campo Título é obrigatório.", resultado, "Deve bloquear submissão sem título");
    }

    @Test
    @DisplayName("CT-34: Tentativa de submissão de projeto com Resumo vazio")
    public void testSubmissaoComResumoVazio() {
        Projeto projeto = new Projeto("Título", "", "Palavra-chave", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        ProjetoController controller = new ProjetoController();
        String resultado = controller.submeter(projeto);
        assertEquals("O campo Resumo é obrigatório.", resultado, "Deve bloquear submissão sem resumo");
    }

    @Test
    @DisplayName("CT-35: Tentativa de submissão de projeto com Palavras-chave vazias")
    public void testSubmissaoComPalavrasChaveVazias() {
        Projeto projeto = new Projeto("Título", "Resumo", "", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        ProjetoController controller = new ProjetoController();
        String resultado = controller.submeter(projeto);
        assertEquals("O campo Palavras-chave é obrigatório.", resultado, "Deve bloquear submissão sem palavras-chave");
    }

    @Test
    @DisplayName("CT-36: Tentativa de submissão de projeto com Público-alvo vazio")
    public void testSubmissaoComPublicoAlvoVazio() {
        Projeto projeto = new Projeto("Título", "Resumo", "Palavra-chave", "", "Área", "Campus", Projeto.Ods.ODS1);
        ProjetoController controller = new ProjetoController();
        String resultado = controller.submeter(projeto);
        assertEquals("O campo Público-alvo é obrigatório.", resultado, "Deve bloquear submissão sem público-alvo");
    }

    @Test
    @DisplayName("CT-37: Tentativa de submissão de projeto sem selecionar Área Temática")
    public void testSubmissaoSemAreaTematica() {
        Projeto projeto = new Projeto("Título", "Resumo", "Palavra-chave", "Público", "", "Campus", Projeto.Ods.ODS1);
        ProjetoController controller = new ProjetoController();
        String resultado = controller.submeter(projeto);
        assertEquals("O campo Área Temática é obrigatório.", resultado, "Deve bloquear submissão sem área temática");
    }

    @Test
    @DisplayName("CT-38: Tentativa de submissão de projeto sem selecionar Campus")
    public void testSubmissaoSemCampus() {
        Projeto projeto = new Projeto("Título", "Resumo", "Palavra-chave", "Público", "Área", "", Projeto.Ods.ODS1);
        ProjetoController controller = new ProjetoController();
        String resultado = controller.submeter(projeto);
        assertEquals("O campo Campus é obrigatório.", resultado, "Deve bloquear submissão sem campus");
    }

    @Test
    @DisplayName("CT-39: Tentativa de submissão de projeto sem selecionar ODS")
    public void testSubmissaoSemOds() {
        Projeto projeto = new Projeto("Título", "Resumo", "Palavra-chave", "Público", "Área", "Campus", null);
        ProjetoController controller = new ProjetoController();
        String resultado = controller.submeter(projeto);
        assertEquals("O campo ODS é obrigatório.", resultado, "Deve bloquear submissão sem ODS");
    }

    @Test
    @DisplayName("CT-40: Tentativa de submissão de projeto sem aceitar o Termo de Compromisso")
    public void testSubmissaoSemTermoDeCompromisso() {
        Projeto projeto = new Projeto("Título", "Resumo", "Palavra-chave", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        // Assuming Termos is false by default in Projeto
        ProjetoController controller = new ProjetoController();
        String resultado = controller.submeter(projeto);
        assertEquals("O Termo de Compromisso é obrigatório.", resultado, "Deve bloquear submissão sem aceite do termo");
    }

    // -- Atualização/Edição validations --

    @Test
    @DisplayName("CT-41: Tentativa de edição de rascunho apagando campo obrigatório (Título)")
    public void testEdicaoRascunhoSemTitulo() {
        Projeto dadosNovos = new Projeto("", "Resumo", "Chave", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        Projeto projetoExistente = new Projeto("Título Antigo", "Resumo Antigo", "Chave Antiga", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        projetoExistente.setStatus(Projeto.Status.VAZIO);

        try (MockedConstruction<ProjetoRepository> mocked = mockConstruction(ProjetoRepository.class,
                (mock, context) -> {
                    when(mock.read("")).thenReturn(projetoExistente); // Simulando a busca que retorna o projeto (caso busque pelo novo título)
                    when(mock.read("Título Antigo")).thenReturn(projetoExistente); // Simulando a busca pelo antigo
                })) {

            ProjetoController controller = new ProjetoController();
            String resultado = controller.atualizar(1L, dadosNovos);

            assertEquals("O campo Título é obrigatório.", resultado, "Deve bloquear atualização com título vazio");
        }
    }

    @Test
    @DisplayName("CT-42: Tentativa de edição de rascunho apagando campo obrigatório (Resumo)")
    public void testEdicaoRascunhoSemResumo() {
        Projeto dadosNovos = new Projeto("Título", "", "Chave", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        Projeto projetoExistente = new Projeto("Título", "Resumo Antigo", "Chave Antiga", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        projetoExistente.setStatus(Projeto.Status.VAZIO);

        try (MockedConstruction<ProjetoRepository> mocked = mockConstruction(ProjetoRepository.class,
                (mock, context) -> {
                    when(mock.read("Título")).thenReturn(projetoExistente);
                })) {

            ProjetoController controller = new ProjetoController();
            String resultado = controller.atualizar(1L, dadosNovos);

            assertEquals("O campo Resumo é obrigatório.", resultado, "Deve bloquear atualização com resumo vazio");
        }
    }

    @Test
    @DisplayName("CT-45: Tentativa de edição de projeto em status Em Correção limpando campo obrigatório")
    public void testEdicaoCorrecaoSemPalavrasChave() {
        Projeto dadosNovos = new Projeto("Título", "Resumo", "", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        Projeto projetoExistente = new Projeto("Título", "Resumo Antigo", "Chave Antiga", "Público", "Área", "Campus", Projeto.Ods.ODS1);
        projetoExistente.setStatus(Projeto.Status.CORRECAO);

        try (MockedConstruction<ProjetoRepository> mocked = mockConstruction(ProjetoRepository.class,
                (mock, context) -> {
                    when(mock.read("Título")).thenReturn(projetoExistente);
                })) {

            ProjetoController controller = new ProjetoController();
            String resultado = controller.atualizar(1L, dadosNovos);

            assertEquals("O campo Palavras-chave é obrigatório.", resultado, "Deve bloquear edição com palavras-chave vazias");
        }
    }

    @Test
    @DisplayName("CT-30: Permitir que coordenadores submetam novos projetos com dados válidos")
    public void testSubmissaoComSucesso() {
        Projeto projeto = new Projeto("Título Válido", "Resumo Válido", "Palavras-chave Válidas", 
                                      "Público-alvo", "Área Temática", "Campus", Projeto.Ods.ODS1);
        projeto.setTermos(true);
        
        try (MockedConstruction<ProjetoRepository> mocked = mockConstruction(ProjetoRepository.class)) {
            ProjetoController controller = new ProjetoController();
            String resultado = controller.submeter(projeto);
            
            // O código atual do controller retorna "Projeto submetido com sucesso!"
            assertEquals("Projeto submetido com sucesso!", resultado, "Deve submeter o projeto corretamente");
            
            ProjetoRepository mockRepo = mocked.constructed().get(0);
            verify(mockRepo, times(1)).create(projeto);
            assertEquals(Projeto.Status.SUBMETIDO, projeto.getStatus());
        }
    }
}
