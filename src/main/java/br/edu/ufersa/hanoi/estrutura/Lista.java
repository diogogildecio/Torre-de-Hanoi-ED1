package br.edu.ufersa.hanoi.estrutura;

public class Lista<T> {
    private static class No<T> {
        T dado;
        No<T> proximo;

        No(T dado) {
            this.dado = dado;
        }
    }

    private No<T> inicio;
    private No<T> fim;
    private int tamanho;

    public Lista() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }

    // Insere no final, em tempo constante graças à referência para o fim
    public void adicionar(T elemento) {
        No<T> novo = new No<>(elemento);
        if (estaVazia()) {
            inicio = novo;
        } else {
            fim.proximo = novo;
        }
        fim = novo;
        tamanho++;
    }

    public void adicionarNoInicio(T elemento) {
        No<T> novo = new No<>(elemento);
        novo.proximo = inicio;
        inicio = novo;
        if (fim == null) {
            fim = novo;
        }
        tamanho++;
    }

    public T get(int posicao) {
        return obterNo(posicao).dado;
    }

    // Substitui o elemento da posição e devolve o antigo (usado pela ordenação)
    public T set(int posicao, T elemento) {
        No<T> no = obterNo(posicao);
        T antigo = no.dado;
        no.dado = elemento;
        return antigo;
    }

    public T remover(int posicao) {
        validarPosicao(posicao);
        T removido;

        if (posicao == 0) {
            removido = inicio.dado;
            inicio = inicio.proximo;
            if (inicio == null) {
                fim = null; // a lista ficou vazia
            }
        } else {
            No<T> anterior = obterNo(posicao - 1);
            No<T> alvo = anterior.proximo;
            removido = alvo.dado;
            anterior.proximo = alvo.proximo;
            if (alvo == fim) {
                fim = anterior; // removeu o último
            }
        }

        tamanho--;
        return removido;
    }

    public int tamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public void limpar() {
        inicio = null;
        fim = null;
        tamanho = 0;
    }

    private No<T> obterNo(int posicao) {
        validarPosicao(posicao);
        No<T> atual = inicio;
        for (int i = 0; i < posicao; i++) {
            atual = atual.proximo;
        }
        return atual;
    }

    private void validarPosicao(int posicao) {
        if (posicao < 0 || posicao >= tamanho) {
            throw new IndexOutOfBoundsException(
                    "Posição " + posicao + " inválida para lista de tamanho " + tamanho + ".");
        }
    }
}
