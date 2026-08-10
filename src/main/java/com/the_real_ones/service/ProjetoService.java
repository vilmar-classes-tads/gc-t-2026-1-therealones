package com.the_real_ones.service;

import com.the_real_ones.model.Projeto;
import com.the_real_ones.repository.ProjetoRepository;

import java.util.ArrayList;
import java.util.List;

public class ProjetoService {

    private ProjetoRepository projetoRepository;

    public ProjetoService() {
    }

    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public List<Projeto> listarTodosProjetos() {
        return projetoRepository.readAll();
    }

    public List<Projeto> filtrarProjetos(String area) {
        return projetoRepository.filtrarPorArea(area);
    }

    public List<Projeto> listarProjetosPorPerfil(String perfil, String campus) {
        return projetoRepository.readByCampus(campus);
    }

    public void downloadArquivos(Long id, String cargo) {
        projetoRepository.possuiArquivos(id);
    }

    public List<Projeto> filtrarMultiplosCriterios(String edital, String campus, String area, Projeto.Status status) {
        return projetoRepository.filtrarMultiplos(edital, campus, area, status);
    }

    public List<Projeto> listarProjetosParaDiretor(String campus) {
        return projetoRepository.readByCampusEStatusPermitidos(campus, new ArrayList<>());
    }

    public List<Projeto> limparFiltrosEListar(String perfil, Object o) {
        return projetoRepository.readAll();
    }

    public void downloadPlanoTrabalho(Long id, String cpf) {
        if (!projetoRepository.possuiArquivos(id)) {
            throw new IllegalStateException("O projeto não possui arquivos anexados");
        }
    }

    public void downloadAnexos(Long id, String cpf) {
        if (!projetoRepository.possuiArquivos(id)) {
            throw new IllegalStateException("O projeto não possui arquivos anexados");
        }
    }

    public List<Projeto> filtrarComValidacaoResultado(String edital, String campus, String area, Projeto.Status status) {
        List<Projeto> resultados = projetoRepository.filtrarMultiplos(edital, campus, area, status);
        if (resultados == null || resultados.isEmpty()) {
            throw new IllegalArgumentException("Nenhum projeto encontrado para os filtros selecionados");
        }
        return resultados;
    }

    public List<Projeto> buscarPaginaFiltrada(String status, int pagina, int tamanho) {
        return projetoRepository.filtrarComPaginacao(status, pagina, tamanho);
    }
}
