package br.edu.ufersa.hanoi.ui;

import br.edu.ufersa.hanoi.modelo.Ranking;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TelaNiveis extends VBox {

    public TelaNiveis(Navegador navegador) {
        super(15);
        setAlignment(Pos.CENTER);
        getStyleClass().add("raiz");

        Label titulo = new Label("Escolha o nível");
        titulo.getStyleClass().add("titulo");

        Label dica = new Label("Quanto mais argolas, mais difícil");
        dica.getStyleClass().add("subtitulo");

        getChildren().addAll(titulo, dica);

        for (int nivel = Ranking.NIVEL_MINIMO; nivel <= Ranking.NIVEL_MAXIMO; nivel++) {
            int minimo = (1 << nivel) - 1; // 2^n - 1
            Button botao = new Button(nivel + " argolas");
            botao.getStyleClass().add("botao-menu");
            botao.getStyleClass().add("botao-nivel");

            final int nivelEscolhido = nivel; // variável usada dentro da lambda
            botao.setOnAction(e -> navegador.mostrarJogo(nivelEscolhido));

            getChildren().add(botao);
        }

        Button voltar = new Button("<< Voltar");
        voltar.getStyleClass().add("botao-voltar");
        voltar.setOnAction(e -> navegador.mostrarMenu());
        getChildren().add(voltar);
    }
}