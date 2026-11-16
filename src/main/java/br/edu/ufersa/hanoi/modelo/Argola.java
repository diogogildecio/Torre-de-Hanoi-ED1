package br.edu.ufersa.hanoi.modelo;

public record Argola(int tamanho) {

    public Argola{
        if(tamanho < 1){
            throw new IllegalArgumentException("O tamanho da argola deve ser maior que zero.");
        }
    }

    public boolean cabeSobre(Argola outra){
        return this.tamanho < outra.tamanho;
    }
}
