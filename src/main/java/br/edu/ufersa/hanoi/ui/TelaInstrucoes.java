package br.edu.ufersa.hanoi.ui;

import br.edu.ufersa.hanoi.modelo.Ranking;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class TelaInstrucoes extends VBox {

    private static final double LARGURA_TEXTO = 700;

    public TelaInstrucoes(Navegador navegador) {
        super(15);
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(20));
        getStyleClass().add("raiz");

        Label titulo = new Label("Instruções");
        titulo.getStyleClass().add("titulo");

        VBox conteudo = new VBox(18,
                criarSecao("Objetivo",
                        "Mover todas as argolas da primeira vareta (esquerda) para a última (direita), "
                                + "terminando com elas na mesma ordem: a maior embaixo e a menor em cima."),
                criarSecao("Regras",
                        "1. Só é possível mover uma argola por vez.\n"
                                + "2. Sempre se move a argola que está no topo de uma vareta.\n"
                                + "3. Uma argola maior nunca pode ficar sobre uma menor."),
                criarSecao("Como jogar",
                        "Clique na vareta de origem (ela fica destacada) e depois clique na vareta de destino. "
                                + "Para cancelar a seleção, clique novamente na mesma vareta. "
                                + "O tempo começa a contar no primeiro movimento válido."),
                criarSecao("Botões",
                        "Desfazer: volta o último movimento, mas ele conta como mais um movimento.\n"
                                + "Reiniciar: recomeça o mesmo nível do zero."),
                criarSecao("Níveis e pontuação",
                        "Há níveis de " + Ranking.NIVEL_MINIMO + " a " + Ranking.NIVEL_MAXIMO
                                + " argolas. Com n argolas, o mínimo possível é 2ⁿ − 1 movimentos.\n"
                                + "★★★ = igualou o mínimo  |  ★★☆ = até 1,5× o mínimo  |  ★☆☆ = acima disso.\n"
                                + "Ao vencer, salve seu nome para entrar no top "
                                + Ranking.LIMITE_TOP + " do nível. Menos movimentos ficam melhor colocados.")
        );
        conteudo.setAlignment(Pos.TOP_LEFT);
        conteudo.setMaxWidth(LARGURA_TEXTO);
        conteudo.setPadding(new Insets(5, 10, 5, 10));

        VBox centralizado = new VBox(conteudo);
        centralizado.setAlignment(Pos.TOP_CENTER);

        // ScrollPane de segurança: se o texto crescer, a janela não corta o conteúdo
        ScrollPane rolagem = new ScrollPane(centralizado);
        rolagem.setFitToWidth(true);
        rolagem.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rolagem.getStyleClass().add("scroll-instrucoes");
        VBox.setVgrow(rolagem, javafx.scene.layout.Priority.ALWAYS);

        Button voltar = new Button("<< Menu");
        voltar.getStyleClass().add("botao-voltar");
        voltar.setOnAction(e -> navegador.mostrarMenu());

        getChildren().addAll(titulo, rolagem, voltar);
    }

    private VBox criarSecao(String cabecalho, String texto) {
        Label lblCabecalho = new Label(cabecalho);
        lblCabecalho.getStyleClass().add("instrucao-cabecalho");

        Label lblTexto = new Label(texto);
        lblTexto.getStyleClass().add("instrucao-texto");
        lblTexto.setWrapText(true);
        lblTexto.setMaxWidth(LARGURA_TEXTO - 20);

        return new VBox(4, lblCabecalho, lblTexto);
    }
}