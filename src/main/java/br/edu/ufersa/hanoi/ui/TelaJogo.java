package br.edu.ufersa.hanoi.ui;

import br.edu.ufersa.hanoi.modelo.Argola;
import br.edu.ufersa.hanoi.modelo.Jogo;
import br.edu.ufersa.hanoi.modelo.Movimento;
import br.edu.ufersa.hanoi.modelo.ResultadoMovimento;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class TelaJogo extends BorderPane {

    // Cores por TAMANHO da argola (índice = tamanho - 1): vermelho ao violeta
    private static final String[] CORES = {
            "#e63946", "#f4811f", "#f7c548", "#43b86a", "#3d8bfd", "#5b5fd6", "#9b5de5"
    };

    private static final double ALTURA_HASTE = 220;
    private static final double LARGURA_HASTE = 10;
    private static final double ALTURA_ARGOLA = 24;
    private static final double ESPACO_ARGOLAS = 2;
    private static final double LARGURA_MINIMA_ARGOLA = 50;
    private static final double PASSO_LARGURA = 25;
    private static final double LARGURA_VARETA = 270;

    private final Navegador navegador;
    private final Jogo jogo;
    private final VBox[] caixasArgolas = new VBox[Movimento.QUANTIDADE_VARETAS];
    private final VBox[] varetas = new VBox[Movimento.QUANTIDADE_VARETAS];

    private final Label lblMovimentos = new Label();
    private final Label lblTempo = new Label();
    private final Label lblMensagem = new Label();
    private final Button btnDesfazer = new Button("Desfazer");

    private final Timeline cronometro;
    private int origemSelecionada = -1; // -1 = nenhuma vareta selecionada

    public TelaJogo(Navegador navegador, int nivel) {
        this.navegador = navegador;
        this.jogo = new Jogo(nivel);
        getStyleClass().add("raiz");
        setPadding(new Insets(20));

        setTop(criarCabecalho(nivel));
        setCenter(criarVaretas());
        setBottom(criarRodape());

        cronometro = new Timeline(new KeyFrame(Duration.millis(100), e -> atualizarCronometro()));
        cronometro.setCycleCount(Timeline.INDEFINITE);
        cronometro.play();

        atualizar();
    }

    // ---------- Montagem da tela ----------

    private HBox criarCabecalho(int nivel) {
        Label lblNivel = new Label(nivel + " argolas");
        lblNivel.getStyleClass().add("info-jogo");
        lblMovimentos.getStyleClass().add("info-jogo");
        lblTempo.getStyleClass().add("info-jogo");

        HBox cabecalho = new HBox(50, lblNivel, lblMovimentos, lblTempo);
        cabecalho.setAlignment(Pos.CENTER);
        cabecalho.setPadding(new Insets(0, 0, 20, 0));
        return cabecalho;
    }

    private HBox criarVaretas() {
        HBox centro = new HBox(10);
        centro.setAlignment(Pos.CENTER);
        for (int i = 0; i < varetas.length; i++) {
            varetas[i] = criarVareta(i);
            centro.getChildren().add(varetas[i]);
        }
        return centro;
    }

    private VBox criarVareta(int indice) {
        Rectangle haste = new Rectangle(LARGURA_HASTE, ALTURA_HASTE);
        haste.getStyleClass().add("haste");

        VBox argolas = new VBox(ESPACO_ARGOLAS);
        argolas.setAlignment(Pos.BOTTOM_CENTER);
        caixasArgolas[indice] = argolas;

        StackPane area = new StackPane(haste, argolas);
        StackPane.setAlignment(haste, Pos.BOTTOM_CENTER);
        area.setPrefHeight(ALTURA_HASTE);
        area.setMaxHeight(ALTURA_HASTE);

        Rectangle base = new Rectangle(LARGURA_VARETA - 20, 14);
        base.setArcWidth(8);
        base.setArcHeight(8);
        base.getStyleClass().add("base-vareta");

        VBox vareta = new VBox(area, base);
        vareta.setAlignment(Pos.BOTTOM_CENTER);
        vareta.setPrefWidth(LARGURA_VARETA);
        vareta.setMaxWidth(LARGURA_VARETA);
        vareta.getStyleClass().add("vareta");
        vareta.setOnMouseClicked(e -> aoClicarVareta(indice));
        return vareta;
    }

    private VBox criarRodape() {
        lblMensagem.getStyleClass().add("mensagem");
        lblMensagem.setMinHeight(30); // reserva o espaço para a tela não "pular"

        btnDesfazer.getStyleClass().add("botao-acao");
        btnDesfazer.setOnAction(e -> {
            jogo.desfazer();
            origemSelecionada = -1;
            lblMensagem.setText("");
            atualizar();
        });

        Button btnReiniciar = new Button("Reiniciar");
        btnReiniciar.getStyleClass().add("botao-acao");
        btnReiniciar.setOnAction(e -> {
            jogo.reiniciar();
            origemSelecionada = -1;
            lblMensagem.setText("");
            atualizar();
        });

        Button btnMenu = new Button("Menu");
        btnMenu.getStyleClass().add("botao-acao");
        btnMenu.setOnAction(e -> {
            cronometro.stop();
            navegador.mostrarMenu();
        });

        HBox botoes = new HBox(15, btnDesfazer, btnReiniciar, btnMenu);
        botoes.setAlignment(Pos.CENTER);

        VBox rodape = new VBox(15, lblMensagem, botoes);
        rodape.setAlignment(Pos.CENTER);
        rodape.setPadding(new Insets(20, 0, 0, 0));
        return rodape;
    }

    // ---------- Interação ----------

    private void aoClicarVareta(int indice) {
        if (jogo.isVitoria()) {
            return;
        }

        // 1º clique: escolhe a origem
        if (origemSelecionada == -1) {
            if (jogo.alturaVareta(indice) == 0) {
                lblMensagem.setText(ResultadoMovimento.ORIGEM_VAZIA.getMensagem());
            } else {
                origemSelecionada = indice;
                lblMensagem.setText("");
            }
            atualizar();
            return;
        }

        // Clicar de novo na origem cancela a seleção (não é erro)
        if (indice == origemSelecionada) {
            origemSelecionada = -1;
            atualizar();
            return;
        }

        // 2º clique: tenta mover
        ResultadoMovimento resultado = jogo.mover(origemSelecionada, indice);
        origemSelecionada = -1;
        lblMensagem.setText(resultado.foiRealizado() ? "" : resultado.getMensagem());
        atualizar();

        if (jogo.isVitoria()) {
            cronometro.stop();
            navegador.mostrarVitoria(jogo);
        }
    }

    // ---------- Desenho ----------

    // Redesenha tudo a partir do estado do Jogo (no máximo 7 argolas, é barato)
    private void atualizar() {
        for (int v = 0; v < varetas.length; v++) {
            caixasArgolas[v].getChildren().clear();

            int altura = jogo.alturaVareta(v);
            // do topo para a base, porque o VBox empilha de cima para baixo
            for (int pos = altura - 1; pos >= 0; pos--) {
                boolean destacada = (v == origemSelecionada && pos == altura - 1);
                caixasArgolas[v].getChildren().add(criarArgola(jogo.getArgola(v, pos), destacada));
            }

            varetas[v].getStyleClass().remove("vareta-selecionada");
            if (v == origemSelecionada) {
                varetas[v].getStyleClass().add("vareta-selecionada");
            }
        }

        lblMovimentos.setText("Movimentos: " + jogo.getMovimentos()
                + "  (mínimo: " + jogo.getMovimentosMinimos() + ")");
        btnDesfazer.setDisable(!jogo.podeDesfazer());
        atualizarCronometro();
    }

    private Rectangle criarArgola(Argola argola, boolean destacada) {
        double largura = LARGURA_MINIMA_ARGOLA + PASSO_LARGURA * (argola.tamanho() - 1);
        Rectangle r = new Rectangle(largura, ALTURA_ARGOLA);
        r.setArcWidth(14);
        r.setArcHeight(14);
        r.setFill(Color.web(CORES[argola.tamanho() - 1]));
        if (destacada) {
            r.setStroke(Color.WHITE);
            r.setStrokeWidth(3);
        }
        return r;
    }

    private void atualizarCronometro() {
        long segundosTotais = jogo.getTempoMs() / 1000;
        lblTempo.setText(String.format("Tempo: %02d:%02d", segundosTotais / 60, segundosTotais % 60));
    }
}
