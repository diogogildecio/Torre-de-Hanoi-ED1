package br.edu.ufersa.hanoi.ui;

import br.edu.ufersa.hanoi.modelo.RegistroRanking;

public class LinhaRanking {
    private final int posicao;
    private final String nome;
    private final int movimentos;
    private final String tempo;

    public LinhaRanking(int posicao, RegistroRanking registro) {
        this.posicao = posicao;
        this.nome = registro.getNome();
        this.movimentos = registro.getMovimentos();
        this.tempo = registro.getTempoFormatado();
    }

    // Getters no padrão JavaBean: o PropertyValueFactory depende desses nomes
    public int getPosicao() {
        return posicao;
    }

    public String getNome() {
        return nome;
    }

    public int getMovimentos() {
        return movimentos;
    }

    public String getTempo() {
        return tempo;
    }
}

