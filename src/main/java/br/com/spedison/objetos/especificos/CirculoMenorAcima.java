package br.com.spedison.objetos.especificos;

import br.com.spedison.objetos.gerais.Circulo;

public class CirculoMenorAcima extends Circulo {
    double raioMaior;

    public CirculoMenorAcima(double raioMaior, double raioAtual) {
        this(raioMaior);
        setRaio(raioAtual);
    }

    public CirculoMenorAcima(double raioMaior) {
        this.raioMaior = raioMaior;
    }

    public void setRaioMaior(double raioMaior) {
        this.raioMaior = raioMaior;
    }

    public double getRaioMaior() {
        return raioMaior;
    }

    @Override
    public void setRaio(double raio) {
        super.setRaio(raio);
        super.setPontoX(raio);
        super.setPontoY(Math.sqrt(Math.pow(raioMaior, 2.) - 2 * raioMaior * getRaio()));
        if (Double.isNaN(getPontoY()))
            throw new RuntimeException("Problemas ao definir a relação R / r");
    }

}
