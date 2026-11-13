package br.edu.ufersa.hanoi.estrutura;
import java.util.Comparator;

public class Ordenacao {

    private Ordenacao(){}

    // Insertion Sort estável sobre a Lista, usando get e set
    public static <T> void insertionSort(Lista<T> lista, Comparator<? super T> comparador) {
        for (int i = 1; i < lista.tamanho(); i++) {
            T chave = lista.get(i);
            int j = i - 1;

            // Desloca para a direita só quem for ESTRITAMENTE maior que a chave.
            // É isso que mantém a ordem de chegada nos empates (estabilidade).
            while (j >= 0 && comparador.compare(lista.get(j), chave) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, chave);
        }
    }
}
