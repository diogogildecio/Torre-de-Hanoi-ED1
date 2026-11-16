package br.edu.ufersa.hanoi.modelo;

public enum ResultadoMovimento {
    OK("Movimento realizado."),
    ORIGEM_VAZIA("A vareta de origem está vazia."),
    ARGOLA_MAIOR("Não é possível colocar uma argola maior sobre uma menor."),
    MESMA_VARETA("Escolha uma vareta destino diferente da origem."),
    VARETA_INVALIDA("Vareta inválida."),
    JOGO_FINALIZADO("O jogo já terminou.");

    private final String mensagem;

    ResultadoMovimento(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public boolean foiRealizado() {
        return this == OK;
    }
}
