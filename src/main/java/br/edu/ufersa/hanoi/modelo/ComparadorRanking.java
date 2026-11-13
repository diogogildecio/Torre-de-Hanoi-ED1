package br.edu.ufersa.hanoi.modelo;

import java.util.Comparator;

public class ComparadorRanking implements Comparator<RegistroRanking> {

    @Override
    public int compare(RegistroRanking a, RegistroRanking b) {
        int porMovimentos = Integer.compare(a.getMovimentos(), b.getMovimentos());
        if (porMovimentos != 0) {
            return porMovimentos; // menos movimentos fica na frente
        }
        return Long.compare(a.getTempoMs(), b.getTempoMs()); // empate: menor tempo na frente
    }
}
