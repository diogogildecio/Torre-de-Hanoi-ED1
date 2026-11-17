package br.edu.ufersa.hanoi.arquivo;

import br.edu.ufersa.hanoi.modelo.Ranking;
import br.edu.ufersa.hanoi.modelo.RegistroRanking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RankingArquivo {

    public static final String CAMINHO_PADRAO = "ranking.csv";

    private final String caminho;

    public RankingArquivo() {
        this(CAMINHO_PADRAO);
    }

    public RankingArquivo(String caminho) {
        this.caminho = caminho;
    }

    // Grava o ranking inteiro: nome;nivel;movimentos;tempoMs (uma linha por registro)
    public void salvar(Ranking ranking) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(
                new FileWriter(caminho, StandardCharsets.UTF_8))) {

            for (int nivel = Ranking.NIVEL_MINIMO; nivel <= Ranking.NIVEL_MAXIMO; nivel++) {
                for (int i = 0; i < ranking.tamanho(nivel); i++) {
                    RegistroRanking r = ranking.get(nivel, i);
                    escritor.write(r.getNome() + ";" + r.getNivel() + ";"
                            + r.getMovimentos() + ";" + r.getTempoMs());
                    escritor.newLine();
                }
            }
        }
    }

    // Lê o arquivo e devolve um Ranking. Arquivo inexistente = ranking vazio.
    public Ranking carregar() throws IOException {
        Ranking ranking = new Ranking();
        File arquivo = new File(caminho);

        if (!arquivo.exists()) {
            return ranking;
        }

        try (BufferedReader leitor = new BufferedReader(
                new FileReader(arquivo, StandardCharsets.UTF_8))) {

            String linha;
            while ((linha = leitor.readLine()) != null) {
                RegistroRanking registro = converterLinha(linha);
                if (registro != null) {
                    ranking.adicionar(registro);
                }
            }
        }
        return ranking;
    }

    // Devolve null se a linha for inválida (ela é simplesmente ignorada)
    private RegistroRanking converterLinha(String linha) {
        if (linha.isBlank()) {
            return null;
        }

        String[] campos = linha.split(";");
        if (campos.length != 4) {
            return null;
        }

        try {
            String nome = campos[0];
            int nivel = Integer.parseInt(campos[1].trim());
            int movimentos = Integer.parseInt(campos[2].trim());
            long tempoMs = Long.parseLong(campos[3].trim());

            if (nivel < Ranking.NIVEL_MINIMO || nivel > Ranking.NIVEL_MAXIMO
                    || movimentos < 0 || tempoMs < 0) {
                return null;
            }
            return new RegistroRanking(nome, nivel, movimentos, tempoMs);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}