package br.com.spedison.objetos.especificos;

import br.com.spedison.objetos.gerais.Circulo;

public class Circulos {
    Circulo circuloMaior;
    CirculoMenorAcima circuloMenorAcima;
    CirculoMenorAbaixo circuloMenorAbaixo;

    boolean resultadoEncontrado;

    public Circulos(Circulo circuloMaior, CirculoMenorAcima circuloMenorAcima, CirculoMenorAbaixo circuloMenorAbaixo, boolean resultadoEncontrado) {
        this.circuloMaior = circuloMaior;
        this.circuloMenorAcima = circuloMenorAcima;
        this.circuloMenorAbaixo = circuloMenorAbaixo;
        this.resultadoEncontrado = resultadoEncontrado;
    }

    public Circulo getCirculoMaior() {
        return circuloMaior;
    }

    public CirculoMenorAcima getCirculoMenorAcima() {
        return circuloMenorAcima;
    }

    public CirculoMenorAbaixo getCirculoMenorAbaixo() {
        return circuloMenorAbaixo;
    }

    public boolean isResultadoEncontrado() {
        return resultadoEncontrado;
    }

    @Override
    public String toString() {
        return "\n" +
                "circuloMaior=" + circuloMaior + "\n" +
                "circuloMenorAcima=" + circuloMenorAcima + "\n" +
                "circuloMenorAbaixo=" + circuloMenorAbaixo + "\n" +
                "resultadoEncontrado=" + resultadoEncontrado + "\n";
    }
}
