package br.edu.ufersa.hanoi.ui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TelaMenu extends VBox {

    public TelaMenu(Navegador navegador) {
        super(20);
        setAlignment(Pos.CENTER);
        getStyleClass().add("raiz");

        Label titulo = new Label("Torre de Hanói");
        titulo.getStyleClass().add("titulo");

        Label subtitulo = new Label("Mova todas as argolas para a última vareta");
        subtitulo.getStyleClass().add("subtitulo-menu");

        Button jogar = criarBotao("Jogar");
        jogar.setOnAction(e -> navegador.mostrarNiveis());
        jogar.getStyleClass().add("botao-principal");

        Button ranking = criarBotao("Ranking");
        ranking.setOnAction(e -> navegador.mostrarRanking());

        Button sair = criarBotao("Sair");
        sair.setOnAction(e -> Platform.exit());
        sair.getStyleClass().add("botao-sair");

        Button instrucoes = criarBotao("Instruções");
        instrucoes.setOnAction(e -> navegador.mostrarInstrucoes());

        getChildren().addAll(criarTorreDecorativa(), titulo, subtitulo, jogar, instrucoes, ranking, sair);
    }

    private Button criarBotao(String texto) {
        Button botao = new Button(texto);
        botao.getStyleClass().add("botao-menu");
        return botao;
    }

    private HBox criarTorreDecorativa() {
        int[][] disposicao = {{5, 3, 1}, {4}, {2}}; // tamanhos de baixo para cima
        HBox torre = new HBox(25);
        torre.setAlignment(Pos.BOTTOM_CENTER);

        for (int[] tamanhos : disposicao) {
            VBox argolas = new VBox(2);
            argolas.setAlignment(Pos.BOTTOM_CENTER);
            for (int i = tamanhos.length - 1; i >= 0; i--) { // o VBox empilha de cima para baixo
                Rectangle r = new Rectangle(24 + 14 * (tamanhos[i] - 1), 14);
                r.setArcWidth(8);
                r.setArcHeight(8);
                r.setFill(Color.web(TelaJogo.CORES[tamanhos[i] - 1]));
                argolas.getChildren().add(r);
            }

            Rectangle haste = new Rectangle(6, 80);
            haste.getStyleClass().add("haste");

            StackPane area = new StackPane(haste, argolas);
            StackPane.setAlignment(haste, Pos.BOTTOM_CENTER);
            area.setPrefSize(100, 80);
            area.setMaxSize(100, 80);

            Rectangle base = new Rectangle(100, 8);
            base.getStyleClass().add("base-vareta");

            VBox vareta = new VBox(area, base);
            vareta.setAlignment(Pos.BOTTOM_CENTER);
            torre.getChildren().add(vareta);
        }
        return torre;
    }
}
