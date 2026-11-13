package br.edu.ufersa.hanoi.modelo;

public class RegistroRanking {

    private final String nome;
    private final int nivel;
    private final int movimentos;
    private final long tempoMs;

    public RegistroRanking(String nome, int nivel, int movimentos, long tempoMs) {
        String limpo = (nome == null) ? "" : nome.replace(";", " ").trim();
        this.nome = limpo.isEmpty() ? "Anônimo" : limpo;
        this.nivel = nivel;
        this.movimentos = movimentos;
        this.tempoMs = tempoMs;
    }

    public String getNome() {
        return nome;
    }

    public int getNivel() {
        return nivel;
    }

    public int getMovimentos() {
        return movimentos;
    }

    public long getTempoMs() {
        return tempoMs;
    }

    public String getTempoFormatado(){
        long totalSegundos = tempoMs / 1000;
        long minutos = totalSegundos / 60;
        long segundos = totalSegundos % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }
}
