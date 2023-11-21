package com.grafos;
import com.grafos.model.Resultados;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import com.grafos.model.Labirintos;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException {
        Scanner scanner = new Scanner(System.in);
        String continuar = "s";
        while (continuar.equalsIgnoreCase("s")) {
            new Labirintos();
            System.out.print("----------------------------------------------------------------------------------\n");
            System.out.print("Lista de garfos disponiveis na API: \n");
            String labyrinthsString = Labirintos.LabirintoApi.toString();
            labyrinthsString = labyrinthsString.substring(1, labyrinthsString.length() - 1);
            String[] labyrinths = labyrinthsString.split(", ");
            for (int i = 0; i < labyrinths.length; i++) {
                System.out.println((i + 1) + ". " + labyrinths[i]);
            }
            System.out.print("----------------------------------------------------------------------------------\n");

            int labyrinthNumber = 0;
            while (true) {
                System.out.print("Digite o número do labirinto: ");
                try {
                    labyrinthNumber = Integer.parseInt(scanner.nextLine());
                    if (labyrinthNumber >= 1 && labyrinthNumber <= labyrinths.length) {
                        break;
                    } else {
                        System.out.println("Número do labirinto inválido. Por favor, tente novamente.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor, insira um número.");
                }
            }
            String maze = labyrinths[labyrinthNumber - 1];
            System.out.println("Você selecionou o labirinto: " + maze);
            System.out.print("Para executar a API, é necessário fornecer um ‘ID’.\n");
            System.out.print("Digite o ID: ");
            String id = scanner.nextLine();
            long execuçaoInicial  = System.currentTimeMillis();
            long tempoAtualEmSegundos = execuçaoInicial / 1000;
            Resultados resultados = new Resultados(id, maze);
            long execuçãoFinal  = System.currentTimeMillis();
            long tempoAtualEmSegundosFim = execuçãoFinal / 1000;
            long execucaoTotalSegunddos = tempoAtualEmSegundosFim - tempoAtualEmSegundos;
            long execucaoTotalMiliSegunddos = execuçãoFinal - execuçaoInicial;
            System.out.println("------------Tempo de execução-------------------------------------------------");
            System.out.println("Total em segundos: " + execucaoTotalSegunddos);
            System.out.println("Total em Milisegundos: " + execucaoTotalMiliSegunddos);

            System.out.print("Deseja executar outro grafo? (s/n): ");
            continuar = scanner.nextLine();
        }
        scanner.close();
    }

}
