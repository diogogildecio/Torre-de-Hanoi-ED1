package br.edu.ufersa.hanoi.ui;

import br.edu.ufersa.hanoi.estrutura.Lista;
import br.edu.ufersa.hanoi.modelo.Ranking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class TelaRanking extends VBox {

    private final Navegador navegador;

    public TelaRanking(Navegador navegador) {
        super(15);
        this.navegador = navegador;
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(20));
        getStyleClass().add("raiz");

        Label titulo = new Label("Ranking");
        titulo.getStyleClass().add("titulo");

        TabPane abas = new TabPane();
        abas.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // sem o "x" nas abas
        for (int nivel = Ranking.NIVEL_MINIMO; nivel <= Ranking.NIVEL_MAXIMO; nivel++) {
            Tab aba = new Tab(nivel + " argolas", criarConteudoAba(nivel));
            abas.getTabs().add(aba);
        }

        Button voltar = new Button("<< Menu");
        voltar.getStyleClass().add("botao-voltar");
        voltar.setOnAction(e -> navegador.mostrarMenu());

        getChildren().addAll(titulo, abas, voltar);
    }

    // ---------- Montagem de uma aba ----------

    private VBox criarConteudoAba(int nivel) {
        TextField campoBusca = new TextField();
        campoBusca.setPromptText("Buscar jogador pelo nome");
        campoBusca.setMaxWidth(320);
        campoBusca.getStyleClass().add("campo-nome");

        TableView<LinhaRanking> tabela = criarTabela();
        tabela.setPlaceholder(new Label("Nenhum registro."));
        tabela.setItems(montarLinhas(nivel, ""));

        // a cada letra digitada, refaz a tabela com o resultado da busca
        campoBusca.textProperty().addListener((obs, antigo, novo) ->
                tabela.setItems(montarLinhas(nivel, novo)));

        VBox conteudo = new VBox(10, campoBusca, tabela);
        conteudo.setAlignment(Pos.TOP_CENTER);
        conteudo.setPadding(new Insets(15));
        return conteudo;
    }

    private TableView<LinhaRanking> criarTabela() {
        TableView<LinhaRanking> tabela = new TableView<>();
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tabela.setPrefHeight(330);

        TableColumn<LinhaRanking, Integer> colPosicao = new TableColumn<>("Posição");
        colPosicao.setCellValueFactory(new PropertyValueFactory<>("posicao"));
        colPosicao.setMaxWidth(90);
        colPosicao.setMinWidth(90);

        TableColumn<LinhaRanking, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<LinhaRanking, Integer> colMovimentos = new TableColumn<>("Movimentos");
        colMovimentos.setCellValueFactory(new PropertyValueFactory<>("movimentos"));
        colMovimentos.setMaxWidth(130);
        colMovimentos.setMinWidth(130);

        TableColumn<LinhaRanking, String> colTempo = new TableColumn<>("Tempo");
        colTempo.setCellValueFactory(new PropertyValueFactory<>("tempo"));
        colTempo.setMaxWidth(110);
        colTempo.setMinWidth(110);

        tabela.getColumns().add(colPosicao);
        tabela.getColumns().add(colNome);
        tabela.getColumns().add(colMovimentos);
        tabela.getColumns().add(colTempo);
        return tabela;
    }

    // ---------- Dados ----------

    // Texto vazio = ranking inteiro; com texto = só os encontrados (mantendo a posição original)
    private ObservableList<LinhaRanking> montarLinhas(int nivel, String texto) {
        Ranking ranking = navegador.getRanking();
        ObservableList<LinhaRanking> linhas = FXCollections.observableArrayList();

        Lista<Integer> indices = ranking.buscarPorNome(nivel, texto);
        for (int i = 0; i < indices.tamanho(); i++) {
            int indice = indices.get(i);
            linhas.add(new LinhaRanking(indice + 1, ranking.get(nivel, indice)));
        }
        return linhas;
    }
}