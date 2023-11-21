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
    private static CloseableHttpClient httpClient = null;
    private static final ObjectMapper objectMapper = new ObjectMapper();
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

    private static <T> T executarRequisicao(String url, String json, Class<T> responseType) throws IOException {
        HttpPost request = new HttpPost(URL + url);
        final StringEntity myEntity = new StringEntity(json);

        request.setEntity(myEntity);
        request.setHeader("Content-type", "application/json");
        request.setHeader("Accept", "application/json");

        HttpResponse response = httpClient.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            return objectMapper.readValue(responseBody, responseType);
        } else {
            throw new IOException("Falha na solicitação: Recebido código de status " + statusCode);
        }
    }

    private static <T> T executarRequisicaoGet(String url, Class<T> responseType) throws IOException {
        HttpGet request = new HttpGet(URL + url);

        HttpResponse response = httpClient.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            return objectMapper.readValue(responseBody, responseType);
        } else {
            throw new IOException("Falha na solicitação: Recebido código de status " + statusCode);
        }
    }

    public resultadoGrafoApi iniciarLabirinto(String id, String labirinto) throws IOException {
        String json = criarJsonIniciar(id, labirinto);
        return executarRequisicao("iniciar", json, resultadoGrafoApi.class);
    }

    public resultadoGrafoApi movimentarLabirinto(String id, String grafo, int posicao) throws IOException {
        String json = criarJsonMovimentos(id, grafo, posicao);
        return executarRequisicao("movimentar", json, resultadoGrafoApi.class);
    }

    public resultadoValidacaoApi validarCaminho(String id, String labirinto, List<Integer> todosMovimentos) throws IOException {
        String json = criarJsonValida(id, labirinto, todosMovimentos);
        return executarRequisicao("validar_caminho", json, resultadoValidacaoApi.class);
    }
    public List<String> listaLabiritos() throws IOException {
        String[] labyrinths = executarRequisicaoGet("labirintos", String[].class);
        return Arrays.stream(labyrinths).toList();
    }
}



