package com.grafos.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grafos.client.Api;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static com.grafos.model.Bfs.BFS;
import java.util.Iterator;

public class Resultados {
    public static String id;
    public static String maze;

    public Resultados(String id, String maze) {
        this.id = id;
        this.maze = maze;

        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> resultList = new ArrayList<>();

        Dfs solucaoMapeada = null;
        try {
            solucaoMapeada = new Dfs(new Api());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
            return;
        }

        try {

            Map<Integer, List<Integer>> adjacencyList = solucaoMapeada.grafo(id, maze).getListaAdj();
            System.out.println("------------Grafo Mapeado--------------------------------------------------------------");
            System.out.println(adjacencyList);
            String jsonData = adjacencyList.toString();
            String output = convertendoFormato(jsonData);
            Result result = contarVertices(output);
            int totalVertices = result.getTotalVertices();
            String lastKey = result.getLastKey();
            System.out.println("------------Quantidade de Vértices----------------------------------------------------");
            System.out.println("Total: " + totalVertices);

            int v = Integer.parseInt(lastKey) + 1;

            ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(v);
            for (int i = 0; i < v; i++) {
                adj.add(new ArrayList<Integer>());
            }

            Bfs.popularGrafoParaJson(adj, output);
            int inicioGrafo = Dfs.inicio, fimGrafo = Dfs.fim;
            caminhoMaisCurto(adj, inicioGrafo, fimGrafo, v, resultList);
            System.out.println("------------Caminho Valido-----------------------------------------------");
            System.out.println(solucaoMapeada.validarCaminho(this.id, this.maze, resultList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertendoFormato(String input) {

        input = input.substring(1, input.length() - 1);
        input = input.replaceAll("=", ":");
        input = input.replaceAll("(\\d+):", "\"$1\":");
        input = input.replaceAll("\\[([^,]+)]", "[$1]");
        input = input.replaceAll("\\],", "],");
        input = input.replaceAll(",\\s*", ",");
        return "{\n" + input + "\n}";
    }


    private static Result contarVertices(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        int totalVertices = jsonNode.size();

        String lastKey = "";
        Iterator<String> keys = jsonNode.fieldNames();
        while(keys.hasNext()){
            lastKey = keys.next();
        }

        return new Result(totalVertices, lastKey);
    }

    static class Result {
        private final int totalVertices;
        private final String lastKey;

        public Result(int totalVertices, String lastKey) {
            this.totalVertices = totalVertices;
            this.lastKey = lastKey;
        }

        public int getTotalVertices() {
            return totalVertices;
        }

        public String getLastKey() {
            return lastKey;
        }
    }


    static void caminhoMaisCurto(ArrayList<ArrayList<Integer>> adj, int s, int dest, int v, List<Integer> resultList) throws Exception {

        int pred[] = new int[v];
        int dist[] = new int[v];

        if (BFS(adj, s, dest, v, pred, dist) == false) {
            System.out.println("A origem e o destino fornecidos não estão conectados.");
            return;
        }

        LinkedList<Integer> path = new LinkedList<Integer>();
        int crawl = dest;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }

//        System.out.println("Shortest path length is: " + dist[dest]);
        System.out.println("------------Menor caminho-----------------------------------------------");
        System.out.println("Caminho: ");
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print(path.get(i) + " ");
            resultList.add(path.get(i));
        }
        System.out.println("");
    }

}
