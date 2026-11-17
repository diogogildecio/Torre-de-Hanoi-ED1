package br.edu.ufersa.hanoi;
import br.edu.ufersa.hanoi.arquivo.RankingArquivo;
import br.edu.ufersa.hanoi.modelo.Ranking;
import br.edu.ufersa.hanoi.ui.Navegador;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application{

    @Override
    public void start(Stage stage) {
        RankingArquivo arquivo = new RankingArquivo();
        Ranking ranking;
        try {
            ranking = arquivo.carregar();
        } catch (IOException e) {
            System.err.println("Não foi possível ler o ranking: " + e.getMessage());
            ranking = new Ranking(); // começa vazio em vez de derrubar o app
        }

        Navegador navegador = new Navegador(stage, ranking, arquivo);
        stage.setTitle("Torre de Hanói");
        navegador.mostrarMenu();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
