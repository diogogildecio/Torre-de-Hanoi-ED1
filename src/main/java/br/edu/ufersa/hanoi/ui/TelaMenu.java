package br.edu.ufersa.hanoi.ui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TelaMenu extends VBox {

    public TelaMenu(Navegador navegador) {
        super(20);
        setAlignment(Pos.CENTER);
        getStyleClass().add("raiz");

        Label titulo = new Label("Torre de Hanói");
        titulo.getStyleClass().add("titulo");

        Button jogar = criarBotao("Jogar");
        jogar.setOnAction(e -> navegador.mostrarNiveis());

        Button ranking = criarBotao("Ranking");
        ranking.setOnAction(e -> navegador.mostrarRanking());

        Button sair = criarBotao("Sair");
        sair.setOnAction(e -> Platform.exit());

        getChildren().addAll(titulo, jogar, ranking, sair);
    }

    private Button criarBotao(String texto) {
        Button botao = new Button(texto);
        botao.getStyleClass().add("botao-menu");
        return botao;
    }
}
