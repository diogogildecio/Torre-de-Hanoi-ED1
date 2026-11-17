package br.edu.ufersa.hanoi.modelo;

import br.edu.ufersa.hanoi.estrutura.Pilha;

public class Jogo {

    private static final int VARETA_FINAL = Movimento.QUANTIDADE_VARETAS - 1;

    private final int nivel;
    private final Pilha<Argola>[] varetas;
    private final Pilha<Movimento> historico;

    private int movimentos;
    private boolean cronometroIniciado;
    private long inicioNanos;
    private long tempoFinalMs;
    private boolean finalizado;

    @SuppressWarnings("unchecked")
    public Jogo(int nivel) {
        if (nivel < Ranking.NIVEL_MINIMO || nivel > Ranking.NIVEL_MAXIMO) {
            throw new IllegalArgumentException(
                    "Nível inválido: " + nivel + " (use de " + Ranking.NIVEL_MINIMO
                            + " a " + Ranking.NIVEL_MAXIMO + ").");
        }
        this.nivel = nivel;
        this.varetas = (Pilha<Argola>[]) new Pilha[Movimento.QUANTIDADE_VARETAS];
        this.historico = new Pilha<>();
        reiniciar();
    }

    // Volta ao estado inicial do mesmo nível: todas as argolas na vareta 0
    public void reiniciar() {
        for (int i = 0; i < varetas.length; i++) {
            varetas[i] = new Pilha<>(nivel);
        }
        for (int tamanho = nivel; tamanho >= 1; tamanho--) {
            varetas[0].empilhar(new Argola(tamanho)); // maior na base, menor no topo
        }
        while (!historico.estaVazia()) {
            historico.desempilhar();
        }
        movimentos = 0;
        cronometroIniciado = false;
        inicioNanos = 0;
        tempoFinalMs = 0;
        finalizado = false;
    }

    public ResultadoMovimento mover(int origem, int destino) {
        if (finalizado) {
            return ResultadoMovimento.JOGO_FINALIZADO;
        }
        if (!varetaValida(origem) || !varetaValida(destino)) {
            return ResultadoMovimento.VARETA_INVALIDA; // checa antes de criar o Movimento
        }
        if (origem == destino) {
            return ResultadoMovimento.MESMA_VARETA;
        }
        if (varetas[origem].estaVazia()) {
            return ResultadoMovimento.ORIGEM_VAZIA;
        }

        Argola argola = varetas[origem].topo();
        if (!varetas[destino].estaVazia() && !argola.cabeSobre(varetas[destino].topo())) {
            return ResultadoMovimento.ARGOLA_MAIOR;
        }

        // Movimento válido: a partir daqui o estado muda
        if (!cronometroIniciado) {
            cronometroIniciado = true;
            inicioNanos = System.nanoTime();
        }

        transferir(origem, destino);
        historico.empilhar(new Movimento(origem, destino));
        movimentos++;

        if (varetas[VARETA_FINAL].tamanho() == nivel) {
            finalizado = true;
            tempoFinalMs = tempoDecorridoMs(); // congela o tempo
        }
        return ResultadoMovimento.OK;
    }

    // Desfaz o último movimento. Conta como movimento (+1) e some do histórico.
    public boolean desfazer() {
        if (!podeDesfazer()) {
            return false;
        }
        Movimento inverso = historico.desempilhar().inverso();
        transferir(inverso.origem(), inverso.destino());
        movimentos++;
        return true;
    }

    public boolean podeDesfazer() {
        return !finalizado && !historico.estaVazia();
    }

    public boolean isVitoria() {
        return finalizado;
    }

    public int getNivel() {
        return nivel;
    }

    public int getMovimentos() {
        return movimentos;
    }

    public int getMovimentosMinimos() {
        return (1 << nivel) - 1; // 2^n - 1
    }

    public long getTempoMs() {
        if (!cronometroIniciado) {
            return 0;
        }
        return finalizado ? tempoFinalMs : tempoDecorridoMs();
    }

    // Leitura para a interface desenhar: posição 0 = base da vareta
    public int alturaVareta(int vareta) {
        validarVareta(vareta);
        return varetas[vareta].tamanho();
    }

    public Argola getArgola(int vareta, int posicao) {
        validarVareta(vareta);
        return varetas[vareta].get(posicao);
    }

    private void transferir(int origem, int destino) {
        varetas[destino].empilhar(varetas[origem].desempilhar());
    }

    private long tempoDecorridoMs() {
        return (System.nanoTime() - inicioNanos) / 1_000_000;
    }

    private boolean varetaValida(int vareta) {
        return vareta >= 0 && vareta < varetas.length;
    }

    private void validarVareta(int vareta) {
        if (!varetaValida(vareta)) {
            throw new IllegalArgumentException("Vareta inválida: " + vareta);
        }
    }
}