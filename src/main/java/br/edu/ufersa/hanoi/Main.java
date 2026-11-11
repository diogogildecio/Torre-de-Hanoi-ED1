package br.edu.ufersa.hanoi;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage stage) {
        StackPane raiz = new StackPane(new Label("Torre de Hanói"));
        stage.setTitle("Torre de Hanói");
        stage.setScene(new Scene(raiz, 900, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
