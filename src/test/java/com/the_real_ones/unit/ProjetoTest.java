package com.the_real_ones.unit;

import com.the_real_ones.model.Projeto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ProjetoTest {

    @Test
    @DisplayName("CT-01: Edição de propriedades do Projeto altera os valores corretamente")
    public void testEditarProjetoSucesso() {
        // Arrange
        Projeto projeto = new Projeto("Título", "Resumo", "Chave", "Público", "Área", "Campus", Projeto.Ods.ODS1);

        // Act
        projeto.editarProjeto("Novo Título", "Novo Resumo", "Nova Chave", "Novo Público", "Nova Área", "Novo Campus", Projeto.Ods.ODS2);

        // Assert
        assertEquals("Novo Título", projeto.getTitulo());
        assertEquals("Novo Resumo", projeto.getResumo());
        assertEquals("Nova Chave", projeto.getPalavraChave());
        assertEquals("Novo Público", projeto.getPublicoAlvo());
        assertEquals("Nova Área", projeto.getAreaTematica());
        assertEquals("Novo Campus", projeto.getCampos());
        assertEquals(Projeto.Ods.ODS2, projeto.getOds());
        
        // Termos nunca é atualizado no método editarProjeto
        assertFalse(projeto.getTermos());
    }
}
