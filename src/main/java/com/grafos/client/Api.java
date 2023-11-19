package com.grafos.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grafos.response.resultadoValidacaoApi;
import com.grafos.response.resultadoGrafoApi;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;
import java.security.cert.X509Certificate;

public class Api {
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String URL = "https://gtm.delary.dev/";

    public Api() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManager[] trustAllCertificates = new TrustManager[]{new InsecureTrustManager()};
        sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());

        httpClient = HttpClients.custom()
                .setSslcontext(sslContext)
                .build();
    }

    private static class InsecureTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {

        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {

        }
    }

    public String criarJsonIniciar(String id, String labirinto) {
        return "{\"id\":\"" + id + "\",\"labirinto\":\"" + labirinto + "\"}";
    }

    public String criarJsonMovimentos(String id, String labirinto, int posicao) {
        return "{\"id\":\"" + id + "\",\"labirinto\":\"" + labirinto + "\",\"nova_posicao\":" + posicao + "}";
    }

    public String criarJsonValida(String id, String labirinto, List<Integer> todosMovimentos) {
        return "{\"id\":\"" + id + "\",\"labirinto\":\"" + labirinto + "\",\"todos_movimentos\":" + todosMovimentos + "}";
    }

    public List<String> listaLabiritos() throws IOException {
        String url = URL + "labirintos";
        HttpGet request = new HttpGet(url);

        CloseableHttpResponse httpResponse = httpClient.execute(request);

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = httpResponse.getEntity();
            String responseBody = EntityUtils.toString(entity);
            String[] labyrinths = objectMapper.readValue(responseBody, String[].class);

            return Arrays.stream(labyrinths).toList();
        } else {
            throw new IOException("Erro na solicitação: Código de status " + statusCode);
        }
    }

    public resultadoGrafoApi iniciarLabirinto(String id, String labirinto) throws IOException {
        String url = URL + "iniciar";
        HttpPost request = new HttpPost(url);
        final List<NameValuePair> params = new ArrayList<>();

        String json = criarJsonIniciar(id, labirinto);
        final StringEntity myEntity = new StringEntity(json);

        request.setEntity(myEntity);
        request.setHeader("Content-type", "application/json");
        request.setHeader("Accept", "application/json");

        HttpResponse response = httpClient.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            return objectMapper.readValue(responseBody, resultadoGrafoApi.class);
        } else {
            throw new IOException("Erro na solicitação de exploração: Código de status " + statusCode);
        }
    }

    public resultadoGrafoApi movimentarLabirinto(String id, String grafo, int posicao) throws IOException {
        String url = URL + "movimentar";
        HttpPost request = new HttpPost(url);
        final List<NameValuePair> params = new ArrayList<>();
        String json = criarJsonMovimentos(id, grafo, posicao);

        final StringEntity myEntity = new StringEntity(json);

        request.setEntity(myEntity);
        request.setHeader("Content-type", "application/json");
        request.setHeader("Accept", "application/json");

        HttpResponse response = httpClient.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);

            return objectMapper.readValue(responseBody, resultadoGrafoApi.class);
        } else {
            throw new IOException("Erro na solicitação de movimento: Código de status " + statusCode);
        }
    }

    public resultadoValidacaoApi validarCaminho(String id, String labirinto, List<Integer> todosMovimentos) throws IOException {
        String url = "https://gtm.delary.dev/validar_caminho";
        HttpPost request = new HttpPost(url);
        final List<NameValuePair> params = new ArrayList<>();
        String json = criarJsonValida(id, labirinto, todosMovimentos);

        final StringEntity myEntity = new StringEntity(json);

        request.setEntity(myEntity);
        request.setHeader("Content-type", "application/json");
        request.setHeader("Accept", "application/json");
        HttpResponse response = httpClient.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);

            return objectMapper.readValue(responseBody, resultadoValidacaoApi.class);
        } else {
            throw new IOException("Erro na solicitação de movimento: Código de status " + statusCode);
        }

    }
}



