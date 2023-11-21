# Projeto Labirinto
Este projeto implementa um algoritmo que interage com uma API para navegar em um labirinto. A API possui três endpoints principais:

#### /iniciar: Inicia o labirinto.
#### /movimentar: Move-se pelo labirinto até encontrar o nó de saída.
#### /validar_caminho: Verifica se o caminho encontrado está correto.
O algoritmo utiliza uma busca em profundidade (DFS) para mapear o labirinto e, em seguida, uma busca em largura (BFS) para encontrar o caminho mais curto.

Como o grafo do labirinto não é ponderado, o problema pode ser resolvido em tempo O(V + E), onde V é o número de vértices e E é o número de arestas. A ideia é usar uma versão modificada da BFS, na qual continuamos armazenando o predecessor de um determinado vértice enquanto realizamos a busca.

Primeiro, inicializamos uma matriz dist[0, 1, ...., v-1] de tal forma que dist[i] armazena a distância do vértice i do vértice de origem. Também inicializamos a matriz pred[0, 1, ....., v-1] de tal forma que pred[i] representa o predecessor imediato do vértice i na BFS a partir da fonte.

Agora, podemos obter o comprimento do caminho da fonte para qualquer outro vértice em tempo O(1) usando a matriz dist. Para imprimir o caminho da fonte para qualquer vértice, podemos usar a matriz pred, o que levará tempo O(V) no pior caso, pois V é o tamanho da matriz pred.

Assim, a maior parte do tempo do algoritmo é gasto realizando a BFS a partir de uma determinada fonte, o que sabemos que leva tempo O(V+E). Portanto, a complexidade de tempo do nosso algoritmo é O(V+E).
# Link Api
#### https://gtm.delary.dev/