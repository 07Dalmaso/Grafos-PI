package com.grafos.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public class resultadoValidacaoApi {

    @JsonProperty("caminho_valido")
    private boolean caminhoValido;
    @JsonProperty("quantidade_movimentos")
    private int movimentos;

    public resultadoValidacaoApi() {
    }

    public resultadoValidacaoApi(boolean caminhoValido, int movimentos) {
        this.caminhoValido = caminhoValido;
        this.movimentos = movimentos;
    }


    @Override
    public String toString() {
        return "Validação: " + caminhoValido + "\nMovimentos: " + movimentos + " ";
    }
}
