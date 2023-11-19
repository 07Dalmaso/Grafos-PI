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

    public Mapeando grafo(String user, String labyrinth) throws IOException {
        resultadoGrafoApi starting = resultadoApi.iniciarLabirinto(user, labyrinth);

        dfs(user, labyrinth, starting);
        return grafo;
    }

    private void dfs(String user, String labirinth, resultadoGrafoApi currentPosition) throws IOException {
        visitado.add(currentPosition.getPosAtual());

        if (currentPosition.isInicio()) {
             inicio = currentPosition.getPosAtual();
        } else if (currentPosition.isFinal()) {
             fim = currentPosition.getPosAtual();
        }

        grafo.construirGrafo(List.of(currentPosition));
        caminho.push(currentPosition);

        for (int newPosition : currentPosition.getMovimentos()) {
            if (!visitado.contains(newPosition)) {
                resultadoGrafoApi moveResponse = resultadoApi.movimentarLabirinto(user, labirinth, newPosition);
                dfs(user, labirinth, moveResponse);
            }
        }
        caminho.pop();
        if (caminho.size() - 1 >= 0) {
            resultadoApi.movimentarLabirinto(user, labirinth, caminho.get(caminho.size() - 1).getPosAtual());
        }

    }

    public resultadoValidacaoApi validarCaminho(String user, String labirinth, List<Integer> movimentos) throws IOException {
        return resultadoApi.validarCaminho(user, labirinth, movimentos);
    }

}
