package com.grafos.model;

import com.grafos.client.Api;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class Labirintos {

    public static Object LabirintoApi;

    public Labirintos() throws NoSuchAlgorithmException, KeyManagementException {
        Api Labirinto = new Api();

        try {
            LabirintoApi = Labirinto.listaLabiritos().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
