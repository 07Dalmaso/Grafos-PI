package com.grafos.model;

import com.grafos.response.resultadoGrafoApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mapeando {
    private final Map<Integer, List<Integer>> listaAdj;

    public Mapeando() {
        this.listaAdj = new HashMap<>();
    }

    public void vertice(int vertice) {
        listaAdj.put(vertice, new ArrayList<>());
    }

    public void adicionarAresta(int verticeOrigem, int verticeDestino) {
        listaAdj.get(verticeOrigem).add(verticeDestino);
        listaAdj.get(verticeDestino).add(verticeOrigem);
    }

    public Map<Integer, List<Integer>> getListaAdj() {
        return listaAdj;
    }

    public void construirGrafo(List<resultadoGrafoApi> respostas) {
        for (resultadoGrafoApi resposta : respostas) {
            int posicaoAtual = resposta.getPosAtual();
            List<Integer> movimentosPossiveis = resposta.getMovimentos();

            if (!listaAdj.containsKey(posicaoAtual)) {
                vertice(posicaoAtual);
            }

            for (int novaPosicao : movimentosPossiveis) {
                if (!listaAdj.containsKey(novaPosicao)) {
                    vertice(novaPosicao);
                }
                if (!listaAdj.get(posicaoAtual).contains(novaPosicao)) {
                    adicionarAresta(posicaoAtual, novaPosicao);
                }
            }
        }
    }

}
