package com.grafos.model;

import com.grafos.client.Api;
import com.grafos.response.resultadoValidacaoApi;
import com.grafos.response.resultadoGrafoApi;
import java.io.IOException;
import java.util.*;

public class Dfs {
    private final Api resultadoApi;
    private final Mapeando grafo;
    private final Stack<resultadoGrafoApi> caminho;
    private final Set<Integer> visitado;
    public static int inicio = 0;
    public static int fim = 0;

    public Dfs(Api resultadoApi) {
        this.resultadoApi = resultadoApi;
        this.grafo = new Mapeando();
        this.visitado = new HashSet<>();
        this.caminho = new Stack<>();

    }

    public Mapeando grafo(String id, String labirinto) throws IOException {
        resultadoGrafoApi montadoGrafo = resultadoApi.iniciarLabirinto(id, labirinto);

        dfs(id, labirinto, montadoGrafo);
        return grafo;
    }

    private void dfs(String id, String labirinto, resultadoGrafoApi posicaoAtual) throws IOException {
        visitado.add(posicaoAtual.getPosAtual());

        if (posicaoAtual.inicio()) {
             inicio = posicaoAtual.getPosAtual();
        } else if (posicaoAtual.fim()) {
             fim = posicaoAtual.getPosAtual();
        }

        grafo.construirGrafo(List.of(posicaoAtual));
        caminho.push(posicaoAtual);

        for (int novaPosicao: posicaoAtual.getMovimentos()) {
            if (!visitado.contains(novaPosicao)) {
                resultadoGrafoApi moveResponse = resultadoApi.movimentarLabirinto(id, labirinto, novaPosicao);
                dfs(id, labirinto, moveResponse);
            }
        }
        caminho.pop();
        if (caminho.size() - 1 >= 0) {
            resultadoApi.movimentarLabirinto(id, labirinto, caminho.get(caminho.size() - 1).getPosAtual());
        }

    }

    public resultadoValidacaoApi validarCaminho(String user, String labirinth, List<Integer> movimentos) throws IOException {
        return resultadoApi.validarCaminho(user, labirinth, movimentos);
    }

}
