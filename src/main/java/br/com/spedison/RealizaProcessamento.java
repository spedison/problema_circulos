package br.com.spedison;

import br.com.spedison.objetos.*;
import br.com.spedison.objetos.especificos.CirculoMenorAbaixo;
import br.com.spedison.objetos.especificos.CirculoMenorAcima;
import br.com.spedison.objetos.especificos.Circulos;
import br.com.spedison.objetos.gerais.Circulo;
import br.com.spedison.objetos.gerais.Curva;

public class RealizaProcessamento {

    public Circulos calculaComRaioR(Double raioMaior) {

        Circulo maior = new Circulo(0, 0, raioMaior);
        boolean encontrei = true;
        double inicio = Caracteristicas.ERRO * 10;
        double fim = raioMaior * 0.48;
        double meio = (inicio + fim) / 2.;
        double passo = 1.E-4;
        Curva parabola = x -> x * x;


        CirculoMenorAcima circuloMenorAcimaInicio = new CirculoMenorAcima(raioMaior, inicio);
        CirculoMenorAcima circuloMenorAcimaFim = new CirculoMenorAcima(raioMaior, fim);
        CirculoMenorAcima circuloMenorAcimaMeio = new CirculoMenorAcima(raioMaior, meio);
        Boolean bInicio = circuloMenorAcimaInicio.todosPontosDoCirculoEstaoAcimaDaCurva(parabola, passo);
        Boolean bFim = circuloMenorAcimaFim.todosPontosDoCirculoEstaoAcimaDaCurva(parabola, passo);
        Boolean bMeio = circuloMenorAcimaMeio.todosPontosDoCirculoEstaoAcimaDaCurva(parabola, passo);

        while (Math.abs(fim - inicio) > Caracteristicas.ERRO) {
            System.out.println("Processando raios (" + raioMaior + " ; " + meio + ")");

            // Para se der um erro.
            if (!bInicio || bFim) {
                encontrei = false;
                break;
            }

            // Diminui os Ranges entre inicio e fim.
            if (bMeio) { // Se todos os pontos no meio das tentativas de raio está abaixo da curva
                inicio = meio;
            } else {
                fim = meio;
            }

            meio = (inicio + fim) / 2.;
            circuloMenorAcimaInicio.setRaio(inicio);
            circuloMenorAcimaFim.setRaio(fim);
            circuloMenorAcimaMeio.setRaio(meio);

            bInicio = circuloMenorAcimaInicio.todosPontosDoCirculoEstaoAcimaDaCurva(parabola, passo);
            bFim = circuloMenorAcimaFim.todosPontosDoCirculoEstaoAcimaDaCurva(parabola, passo);
            bMeio = circuloMenorAcimaMeio.todosPontosDoCirculoEstaoAcimaDaCurva(parabola, passo);
        }
        System.out.println("Encontrei (" + encontrei + ") um dos raios ->" + meio);

        CirculoMenorAcima menorAcima;
        if (encontrei) {

            if (bMeio)
                menorAcima = circuloMenorAcimaMeio;
            else
                menorAcima = circuloMenorAcimaInicio;

            return new Circulos(maior, menorAcima, new CirculoMenorAbaixo(menorAcima), true);
        }
        return new Circulos(maior, circuloMenorAcimaMeio, null, false);
    }
}