package br.edu.ufersa.hanoi.modelo;

import br.edu.ufersa.hanoi.estrutura.Busca;
import br.edu.ufersa.hanoi.estrutura.Lista;
import br.edu.ufersa.hanoi.estrutura.Ordenacao;

public class Ranking {

    public static final int NIVEL_MINIMO = 3;
    public static final int NIVEL_MAXIMO = 7;
    public static final int LIMITE_TOP = 10;

    private final Lista<RegistroRanking>[] listasPorNivel;
    private final ComparadorRanking comparador = new ComparadorRanking();

    @SuppressWarnings("unchecked")
    public Ranking() {
        int quantidade = NIVEL_MAXIMO - NIVEL_MINIMO + 1;
        listasPorNivel = (Lista<RegistroRanking>[]) new Lista[quantidade];
        for (int i = 0; i < quantidade; i++) {
            listasPorNivel[i] = new Lista<>();
        }
    }

    // Adiciona, reordena e corta no top 10.
    // Devolve a posição (1 = primeiro) ou 0 se ficou fora do top 10.
    public int adicionar(RegistroRanking registro) {
        Lista<RegistroRanking> lista = listaDoNivel(registro.getNivel());

        lista.adicionar(registro);
        Ordenacao.insertionSort(lista, comparador);

        int posicao = 0;
        for (int i = 0; i < lista.tamanho(); i++) {
            if (lista.get(i) == registro) { // mesma referência, não só mesmos dados
                posicao = i + 1;
                break;
            }
        }

        while (lista.tamanho() > LIMITE_TOP) {
            lista.remover(lista.tamanho() - 1);
        }

        return posicao > LIMITE_TOP ? 0 : posicao;
    }

    public int tamanho(int nivel) {
        return listaDoNivel(nivel).tamanho();
    }

    // posicao começa em 0; a posição exibida na tela é posicao + 1
    public RegistroRanking get(int nivel, int posicao) {
        return listaDoNivel(nivel).get(posicao);
    }

    // Busca parcial por nome, ignorando maiúsculas. Devolve os índices encontrados.
    public Lista<Integer> buscarPorNome(int nivel, String texto) {
        String termo = (texto == null) ? "" : texto.trim().toLowerCase();
        return Busca.buscarPosicoes(listaDoNivel(nivel),
                r -> r.getNome().toLowerCase().contains(termo));
    }

    private Lista<RegistroRanking> listaDoNivel(int nivel) {
        if (nivel < NIVEL_MINIMO || nivel > NIVEL_MAXIMO) {
            throw new IllegalArgumentException(
                    "Nível inválido: " + nivel + " (use de " + NIVEL_MINIMO + " a " + NIVEL_MAXIMO + ").");
        }
        return listasPorNivel[nivel - NIVEL_MINIMO];
    }
}

