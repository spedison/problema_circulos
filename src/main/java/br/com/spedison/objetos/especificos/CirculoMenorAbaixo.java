package br.com.spedison.objetos.especificos;

import br.com.spedison.objetos.gerais.Circulo;

public class CirculoMenorAbaixo extends Circulo {
    CirculoMenorAcima circuloMenorAcima;

    public CirculoMenorAbaixo(CirculoMenorAcima circulo) {
        circuloMenorAcima = circulo;
        setRaio(circuloMenorAcima.getRaio());
        setPontoY(circuloMenorAcima.getRaio());
        double quadrado = Math.pow(circuloMenorAcima.getRaioMaior() - getRaio(), 2.);
        setPontoX(Math.sqrt(quadrado - Math.pow(getRaio(), 2.)));
    }

}
