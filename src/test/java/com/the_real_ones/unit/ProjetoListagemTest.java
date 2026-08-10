package com.the_real_ones.unit;

import com.the_real_ones.model.Projeto;
import com.the_real_ones.repository.ProjetoRepository;
import com.the_real_ones.service.ProjetoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjetoListagemTest {

    @Mock
    private ProjetoRepository projetoRepository;

    @InjectMocks
    private ProjetoService projetoService;

    private Projeto projetoA;
    private Projeto projetoB;

    @BeforeEach
    public void setup() {
        projetoA = new Projeto("Projeto A", "Resumo A", "Chave A", "Público A", "Tecnologia", "Campus A", Projeto.Ods.ODS1);
        projetoA.setStatus(Projeto.Status.SUBMETIDO);

        projetoB = new Projeto("Projeto B", "Resumo B", "Chave B", "Público B", "Saúde", "Campus B", Projeto.Ods.ODS3);
        projetoB.setStatus(Projeto.Status.CORRECAO);
    }

    @Test
    @DisplayName("CT-57: Visualizar todos os projetos")
    public void testVisualizarTodosOsProjetos() {
        // Arrange
        ArrayList<Projeto> listaProjetos = new ArrayList<>(List.of(projetoA, projetoB));
        when(projetoRepository.readAll()).thenReturn(listaProjetos);

        // Act
        List<Projeto> resultado = projetoService.listarTodosProjetos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(projetoA));
        assertTrue(resultado.contains(projetoB));
        verify(projetoRepository, times(1)).readAll();
    }

    @Test
    @DisplayName("CT-58: Visualizar por filtro")
    public void testVisualizarPorFiltro() {
        // Arrange
        String filtroArea = "Tecnologia";
        when(projetoRepository.filtrarPorArea(filtroArea)).thenReturn(new ArrayList<>(List.of(projetoA)));

        // Act
        List<Projeto> resultado = projetoService.filtrarProjetos(filtroArea);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tecnologia", resultado.get(0).getAreaTematica());
        verify(projetoRepository, times(1)).filtrarPorArea(filtroArea);
    }

    @Test
    @DisplayName("CT-59: Visualização do Gestor/Diretor")
    public void testVisualizacaoGestorDiretor() {
        // Arrange
        String perfilUsuario = "GESTOR";
        String campusGestor = "Campus A";
        when(projetoRepository.readByCampus(campusGestor)).thenReturn(new ArrayList<>(List.of(projetoA)));

        // Act
        List<Projeto> resultado = projetoService.listarProjetosPorPerfil(perfilUsuario, campusGestor);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Campus A", resultado.get(0).getCampos());
        verify(projetoRepository, times(1)).readByCampus(campusGestor);
    }

    @Test
    @DisplayName("CT-60: Download de arquivos")
    public void testDownloadArquivosIndependenteDePerfil() {
        // Arrange
        Long projetoId = 1L;
        when(projetoRepository.possuiArquivos(projetoId)).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> projetoService.downloadArquivos(projetoId, "QUALQUER_CARGO"));
        verify(projetoRepository, times(1)).possuiArquivos(projetoId);
    }

    @Test
    @DisplayName("CT-61: Filtrar projetos por múltiplos critérios (Admin Geral)")
    public void testFiltrarProjetosMultiplosCriteriosAdmin() {
        // Arrange
        String edital = "Edital 2026";
        String campus = "Campus A";
        String area = "Tecnologia";
        Projeto.Status status = Projeto.Status.SUBMETIDO;

        when(projetoRepository.filtrarMultiplos(edital, campus, area, status))
                .thenReturn(new ArrayList<>(List.of(projetoA)));

        // Act
        List<Projeto> resultado = projetoService.filtrarMultiplosCriterios(edital, campus, area, status);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Campus A", resultado.get(0).getCampos());
        assertEquals(Projeto.Status.SUBMETIDO, resultado.get(0).getStatus());
        verify(projetoRepository, times(1)).filtrarMultiplos(edital, campus, area, status);
    }

    @Test
    @DisplayName("CT-62: Restrição de visualização de Campus e Status para Diretor")
    public void testRestricaoVisualizacaoDiretor() {
        // Arrange
        String campusDiretor = "Campus A";
        when(projetoRepository.readByCampusEStatusPermitidos(eq(campusDiretor), anyList()))
                .thenReturn(new ArrayList<>(List.of(projetoA)));

        // Act
        List<Projeto> resultado = projetoService.listarProjetosParaDiretor(campusDiretor);

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.stream().anyMatch(p -> !p.getCampos().equals(campusDiretor)));
        verify(projetoRepository, times(1)).readByCampusEStatusPermitidos(eq(campusDiretor), anyList());
    }

    @Test
    @DisplayName("CT-63: Limpar filtros aplicados na listagem")
    public void testLimparFiltrosAplicados() {
        // Arrange
        ArrayList<Projeto> todosProjetos = new ArrayList<>(List.of(projetoA, projetoB));
        when(projetoRepository.readAll()).thenReturn(todosProjetos);

        // Act
        List<Projeto> resultado = projetoService.limparFiltrosEListar("ADMIN_GERAL", null);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(projetoRepository, times(1)).readAll();
    }

    @Test
    @DisplayName("CT-64: Download de Anexos e Planos de Trabalho de projeto de terceiros")
    public void testDownloadArquivosProjetoTerceiros() {
        // Arrange
        Long projetoId = 10L;
        String usuarioAtualCpf = "999.999.999-99"; // Usuário não é o dono do projeto
        when(projetoRepository.possuiArquivos(projetoId)).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> projetoService.downloadPlanoTrabalho(projetoId, usuarioAtualCpf));
        assertDoesNotThrow(() -> projetoService.downloadAnexos(projetoId, usuarioAtualCpf));
    }

    @Test
    @DisplayName("CT-65: Filtrar projetos sem resultados correspondentes")
    public void testFiltrarProjetosSemResultados() {
        // Arrange
        when(projetoRepository.filtrarMultiplos("Inexistente", "Inexistente", "Inexistente", Projeto.Status.VAZIO))
                .thenReturn(new ArrayList<>());

        // Act
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            projetoService.filtrarComValidacaoResultado("Inexistente", "Inexistente", "Inexistente", Projeto.Status.VAZIO);
        });

        // Assert
        assertTrue(ex.getMessage().contains("Nenhum projeto encontrado para os filtros selecionados"));
    }

    @Test
    @DisplayName("CT-66: Tentativa de download de arquivos em projetos sem anexos cadastrados")
    public void testDownloadArquivosSemAnexosCadastrados() {
        // Arrange
        Long projetoSemAnexoId = 5L;
        when(projetoRepository.possuiArquivos(projetoSemAnexoId)).thenReturn(false);

        // Act
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            projetoService.downloadAnexos(projetoSemAnexoId, "111.111.111-11");
        });

        // Assert
        assertTrue(ex.getMessage().contains("O projeto não possui arquivos anexados"));
    }

    @Test
    @DisplayName("CT-67: Persistência de filtros ao navegar pela paginação")
    public void testPersistenciaDeFiltrosNaPaginacao() {
        // Arrange
        String filtroStatus = "SUBMETIDO";
        int pagina = 2;
        int tamanhoPagina = 10;

        when(projetoRepository.filtrarComPaginacao(filtroStatus, pagina, tamanhoPagina))
                .thenReturn(new ArrayList<>(List.of(projetoA)));

        // Act
        List<Projeto> resultadoPagina2 = projetoService.buscarPaginaFiltrada(filtroStatus, pagina, tamanhoPagina);

        // Assert
        assertNotNull(resultadoPagina2);
        assertEquals(1, resultadoPagina2.size());
        assertEquals(Projeto.Status.SUBMETIDO, resultadoPagina2.get(0).getStatus());
        verify(projetoRepository, times(1)).filtrarComPaginacao(filtroStatus, pagina, tamanhoPagina);
    }
}
