package br.edu.ufersa.hanoi.ui;

import br.edu.ufersa.hanoi.modelo.Jogo;
import br.edu.ufersa.hanoi.modelo.Ranking;
import br.edu.ufersa.hanoi.modelo.RegistroRanking;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TelaVitoria extends VBox {

    private static final int TAMANHO_MAXIMO_NOME = 20;

    private final Navegador navegador;
    private final int nivel;
    private final int movimentos;
    private final int minimo;
    private final long tempoMs;

    private final TextField campoNome = new TextField();
    private final Button btnSalvar = new Button("Salvar no ranking");
    private final Label lblResultado = new Label();

    public TelaVitoria(Navegador navegador, Jogo jogo) {
        super(18);
        this.navegador = navegador;
        this.nivel = jogo.getNivel();
        this.movimentos = jogo.getMovimentos();
        this.minimo = jogo.getMovimentosMinimos();
        this.tempoMs = jogo.getTempoMs();

        setAlignment(Pos.CENTER);
        setPadding(new Insets(20));
        getStyleClass().add("raiz");

        Label titulo = new Label("Você venceu!");
        titulo.getStyleClass().add("titulo");

        Label estrelas = new Label(montarEstrelas(calcularEstrelas()));
        estrelas.getStyleClass().add("estrelas");

        Label lblNivel = new Label(nivel + " argolas");
        lblNivel.getStyleClass().add("subtitulo");

        Label lblMovimentos = new Label("Movimentos: " + movimentos + "  (mínimo: " + minimo + ")");
        lblMovimentos.getStyleClass().add("info-jogo");

        Label lblTempo = new Label("Tempo: " + formatarTempo(tempoMs));
        lblTempo.getStyleClass().add("info-jogo");

        getChildren().addAll(titulo, estrelas, lblNivel, lblMovimentos, lblTempo,
                criarLinhaNome(), lblResultado, criarBotoes());

        lblResultado.getStyleClass().add("mensagem");
        lblResultado.setMinHeight(30);
    }

    // ---------- Montagem ----------

    private HBox criarLinhaNome() {
        campoNome.setPromptText("Seu nome");
        campoNome.setPrefWidth(260);
        campoNome.getStyleClass().add("campo-nome");
        // limita o tamanho do nome
        campoNome.textProperty().addListener((obs, antigo, novo) -> {
            if (novo.length() > TAMANHO_MAXIMO_NOME) {
                campoNome.setText(antigo);
            }
        });
        campoNome.setOnAction(e -> salvar()); // Enter também salva

        btnSalvar.getStyleClass().add("botao-acao");
        btnSalvar.setOnAction(e -> salvar());

        HBox linha = new HBox(10, campoNome, btnSalvar);
        linha.setAlignment(Pos.CENTER);
        return linha;
    }

    private HBox criarBotoes() {
        Button jogarNovamente = new Button("Jogar novamente");
        jogarNovamente.getStyleClass().add("botao-acao");
        jogarNovamente.setOnAction(e -> navegador.mostrarJogo(nivel));

        Button verRanking = new Button("Ver ranking");
        verRanking.getStyleClass().add("botao-acao");
        verRanking.setOnAction(e -> navegador.mostrarRanking());

        Button menu = new Button("Menu");
        menu.getStyleClass().add("botao-acao");
        menu.setOnAction(e -> navegador.mostrarMenu());

        HBox botoes = new HBox(15, jogarNovamente, verRanking, menu);
        botoes.setAlignment(Pos.CENTER);
        return botoes;
    }

    // ---------- Ações ----------

    private void salvar() {
        // trava logo no início: impede salvar o mesmo resultado duas vezes
        campoNome.setDisable(true);
        btnSalvar.setDisable(true);

        Ranking ranking = navegador.getRanking();
        RegistroRanking registro = new RegistroRanking(campoNome.getText(), nivel, movimentos, tempoMs);
        int posicao = ranking.adicionar(registro); // 0 = fora do top 10

        String texto = (posicao == 0)
                ? "Fora do top 10 neste nível."
                : "Você ficou em " + posicao + "º lugar!";

        try {
            navegador.getRankingArquivo().salvar(ranking);
        } catch (IOException e) {
            texto += "  (Não foi possível gravar o arquivo do ranking.)";
        }
        lblResultado.setText(texto);
    }

    // ---------- Estrelas ----------

    // 3 = igualou o mínimo; 2 = até 1,5x o mínimo; 1 = acima disso
    private int calcularEstrelas() {
        if (movimentos == minimo) {
            return 3;
        }
        if (movimentos * 2 <= minimo * 3) { // movimentos <= 1,5 * mínimo, sem usar double
            return 2;
        }
        return 1;
    }

    private String montarEstrelas(int quantidade) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 3; i++) {
            sb.append(i <= quantidade ? "★" : "☆");
        }
        return sb.toString();
    }

    private String formatarTempo(long ms) {
        long segundos = ms / 1000;
        return String.format("%02d:%02d", segundos / 60, segundos % 60);
    }
}