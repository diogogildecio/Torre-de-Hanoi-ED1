package br.edu.ufersa.hanoi.estrutura;

public class Pilha<T> {

    private static final int CAPACIDADE_PADRAO = 10;

    private Object[] elementos;
    private int tamanho;

    public Pilha(){
        this(CAPACIDADE_PADRAO);
    }

    public Pilha(int capacidadeInicial) {
        if (capacidadeInicial <= 0) {
            throw new IllegalArgumentException("A capacidade inicial deve ser maior que zero.");
        }
        this.elementos = new Object[capacidadeInicial];
        this.tamanho = 0;
    }

    public void empilhar(T elemento) {
        if (tamanho == elementos.length) {
            redimensionar();
        }
        elementos[tamanho] = elemento;
        tamanho++;
    }

    @SuppressWarnings("unchecked")
    public T desempilhar() {
        verificarNaoVazia();
        tamanho--;
        T removido = (T) elementos[tamanho];
        elementos[tamanho] = null; // evita manter o objeto "preso" na memória
        return removido;
    }

    @SuppressWarnings("unchecked")
    public T topo() {
        verificarNaoVazia();
        return (T) elementos[tamanho - 1];
    }

    // Posição 0 = base; tamanho() - 1 = topo
    @SuppressWarnings("unchecked")
    public T get(int posicao) {
        if (posicao < 0 || posicao >= tamanho) {
            throw new IndexOutOfBoundsException(
                    "Posição " + posicao + " inválida para pilha de tamanho " + tamanho + ".");
        }
        return (T) elementos[posicao];
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public int tamanho() {
        return tamanho;
    }

    private void verificarNaoVazia() {
        if (estaVazia()) {
            throw new PilhaVaziaException();
        }
    }

    // Dobra a capacidade do vetor interno
    private void redimensionar() {
        Object[] novo = new Object[elementos.length * 2];
        for (int i = 0; i < tamanho; i++) {
            novo[i] = elementos[i];
        }
        elementos = novo;
    }
}