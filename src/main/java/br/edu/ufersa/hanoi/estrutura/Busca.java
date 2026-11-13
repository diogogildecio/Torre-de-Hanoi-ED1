package br.edu.ufersa.hanoi.estrutura;
import java.util.function.Predicate;
public class Busca {

    private Busca(){}

    // Busca sequencial: devolve os índices de TODAS as ocorrências que
    // satisfazem o critério. Lista vazia se nada for encontrado.
    public static <T> Lista<Integer> buscarPosicoes(Lista<T> lista, Predicate<? super T> criterio) {
        Lista<Integer> posicoes = new Lista<>();
        for (int i = 0; i < lista.tamanho(); i++) {
            if (criterio.test(lista.get(i))) {
                posicoes.adicionar(i);
            }
        }
        return posicoes;
    }
}
