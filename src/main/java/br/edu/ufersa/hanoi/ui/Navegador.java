package br.edu.ufersa.hanoi.ui;

import br.edu.ufersa.hanoi.arquivo.RankingArquivo;
import br.edu.ufersa.hanoi.modelo.Jogo;
import br.edu.ufersa.hanoi.modelo.Ranking;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;

public class Navegador {

    public static final double LARGURA = 900;
    public static final double ALTURA = 600;

    private final Stage stage;
    private final Ranking ranking;
    private final RankingArquivo rankingArquivo;

    public Navegador(Stage stage, Ranking ranking, RankingArquivo rankingArquivo) {
        this.stage = stage;
        this.ranking = ranking;
        this.rankingArquivo = rankingArquivo;
        this.stage.setResizable(false); // janela fixa por enquanto
    }

    public Ranking getRanking() {
        return ranking;
    }

    public RankingArquivo getRankingArquivo() {
        return rankingArquivo;
    }

    public void mostrarMenu() {
        trocarTela(new TelaMenu(this));
    }

    // ---- Esqueletos: substitua pelo método real conforme cada tela ficar pronta ----

    public void mostrarNiveis() {
        trocarTela(new TelaNiveis(this));
    }

    public void mostrarJogo(int nivel) {
        trocarTela(new TelaJogo(this, nivel));
    }

    public void mostrarVitoria(Jogo jogo) {
        trocarTela(new TelaVitoria(this, jogo));
    }

    public void mostrarRanking() {
        trocarTela(new TelaRanking(this));
    }

    // ---- Infraestrutura ----

    // Único lugar que define tamanho da janela e CSS
    private void trocarTela(Parent raiz) {
        Scene cena = new Scene(raiz, LARGURA, ALTURA);
        cena.getStylesheets().add(getClass().getResource("/estilo.css").toExternalForm());
        stage.setScene(cena);
    }

    // Tela temporária só para testar a navegação
    private Parent telaProvisoria(String nome) {
        Label titulo = new Label(nome + " - em construção");
        titulo.getStyleClass().add("subtitulo");

        Button voltar = new Button("<< Menu");
        voltar.getStyleClass().add("botao-menu");
        voltar.setOnAction(e -> mostrarMenu());

        VBox raiz = new VBox(20, titulo, voltar);
        raiz.setAlignment(Pos.CENTER);
        raiz.getStyleClass().add("raiz");
        return raiz;
    }
}
