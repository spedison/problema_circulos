package br.com.spedison.objetos.gerais;

import br.com.spedison.objetos.Caracteristicas;

public class CalculaDerivadas {

    private CalculaDerivadas(){}

    public static double derivava1D(Curva curva, double pontoX, double h) {
        return (curva.calcula(pontoX + h) - curva.calcula(pontoX)) / h;
    }

    public static double derivava1D(Curva curva, double pontoX) {
        return derivava1D(curva, pontoX, Caracteristicas.H_DERIVADA);
    }

    public static double derivava1DMenorErro(Curva curva, double pontoX, double h) {
        return (curva.calcula(pontoX + h) - curva.calcula(pontoX - h)) / (2 * h);
    }

    public static double derivava1DMenorErro(Curva curva, double pontoX) {
        return derivava1DMenorErro(curva, pontoX, Caracteristicas.H_DERIVADA);
    }

}
