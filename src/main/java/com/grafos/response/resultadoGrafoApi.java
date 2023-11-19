package com.grafos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class resultadoGrafoApi {
    @JsonProperty("pos_atual")
    private int posAtual;
    @JsonProperty("inicio")
    private boolean inicio;
    @JsonProperty("final")
    private boolean fim;
    @JsonProperty("movimentos")
    private List<Integer> movimentos;


    public int getPosAtual() {
        return posAtual;
    }

    public boolean isInicio() {
        return inicio;
    }

    public boolean isFinal() {
        return fim;
    }

    public List<Integer> getMovimentos() {
        return movimentos;
    }

    @Override
    public String toString() {
        return "Iniciar/Movimentar{" +
                "posAtual=" + posAtual +
                ", start=" + inicio +
                ", fim=" + fim +
                ", movimentos=" + movimentos +
                '}';
    }
}
