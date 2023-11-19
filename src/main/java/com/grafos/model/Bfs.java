package com.grafos.model;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Bfs {

    static void popularGrafoParaJson(ArrayList<ArrayList<Integer>> adj, String jsonData) {
        Pattern pattern = Pattern.compile("\"(\\d+)\":\\[([^\\]]+)\\]");
        Matcher matcher = pattern.matcher(jsonData);

        while (matcher.find()) {
            int source = Integer.parseInt(matcher.group(1));
            String[] destinations = matcher.group(2).split(",");

            for (String destination : destinations) {
                addEdge(adj, source, Integer.parseInt(destination));
            }
        }
    }


    static void addEdge(ArrayList<ArrayList<Integer>> adj, int source, int destination) {
        while (adj.size() <= source || adj.size() <= destination) {
            adj.add(new ArrayList<>());
        }
        adj.get(source).add(destination);
    }

    static boolean BFS(ArrayList<ArrayList<Integer>> adj, int src, int dest, int v, int pred[], int dist[]) {

        LinkedList<Integer> queue = new LinkedList<Integer>();
        boolean visited[] = new boolean[v];
        for (int i = 0; i < v; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        visited[src] = true;
        dist[src] = 0;
        queue.add(src);

        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (visited[adj.get(u).get(i)] == false) {
                    visited[adj.get(u).get(i)] = true;
                    dist[adj.get(u).get(i)] = dist[u] + 1;
                    pred[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));
                    if (adj.get(u).get(i) == dest)
                        return true;
                }
            }
        }
        return false;
    }
}
