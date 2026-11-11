package br.edu.ufersa.hanoi.estrutura;

public class PilhaVaziaException extends RuntimeException {

    public PilhaVaziaException(){
        super("A pilha está vazia.");
    }

    public PilhaVaziaException(String message) {
        super(message);
    }
}
