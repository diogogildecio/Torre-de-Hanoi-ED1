package br.edu.ufersa.hanoi.modelo;

public record Movimento(int origem, int destino) {

    public static final int QUANTIDADE_VARETAS = 3;

    public Movimento{
        if (origem < 0 || origem >= QUANTIDADE_VARETAS
                || destino < 0 || destino >= QUANTIDADE_VARETAS) {
            throw new IllegalArgumentException(
                    "Vareta inválida: use índices de 0 a " + (QUANTIDADE_VARETAS - 1) + ".");
        }
    }

    // Movimento que desfaz este (usado pelo "desfazer" do Jogo)
    public Movimento inverso() {
        return new Movimento(destino, origem);
    }
}
