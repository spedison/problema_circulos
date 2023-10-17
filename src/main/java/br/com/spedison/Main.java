package br.com.spedison;

import br.com.spedison.objetos.Caracteristicas;
import br.com.spedison.objetos.especificos.Circulos;
import br.com.spedison.objetos.gerais.Circulo;
import br.com.spedison.objetos.gerais.Curva;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        RealizaProcessamento rp = new RealizaProcessamento();
        double passo = 1.E-4;
        Circulos circulosInicio;
        Circulos circulosMeio;
        Circulos circulosFim;

        double inicio = 1.;
        double fim = 25;
        double meio = (inicio + fim) / 2.;
        Curva parabola = x -> x * x;
        do {
            circulosInicio = rp.calculaComRaioR(inicio);
            circulosFim = rp.calculaComRaioR(fim);
            circulosMeio = rp.calculaComRaioR(meio);

            if (circulosInicio.getCirculoMenorAbaixo().todosPontosDoCirculoEstaoAbaixoDaCurva(parabola, passo)) {
                System.out.println("Problema ao processar o menor círculo - Inicio indevido");
                return;
            }
            if (!circulosFim.getCirculoMenorAbaixo().todosPontosDoCirculoEstaoAbaixoDaCurva(parabola, passo)) {
                System.out.println("Problema ao processar o menor círculo - Fim indevido");
                return;
            }

            if (!circulosMeio.getCirculoMenorAbaixo().todosPontosDoCirculoEstaoAbaixoDaCurva(parabola, passo)) {
                inicio = meio;
            } else {
                fim = meio;
            }
            meio = (inicio + fim) / 2.;
        }
        while (Math.abs(fim - inicio) > Caracteristicas.ERRO && inicio < fim);

        Circulos resultados;
        if (circulosMeio.getCirculoMenorAbaixo().todosPontosDoCirculoEstaoAbaixoDaCurva(x -> x * x, passo) &&
                circulosMeio.isResultadoEncontrado())
            resultados = circulosMeio;
        else if (circulosFim.getCirculoMenorAbaixo().todosPontosDoCirculoEstaoAbaixoDaCurva(x -> x * x, passo) &&
                circulosFim.isResultadoEncontrado())
            resultados = circulosFim;
        else {
            System.out.println("sem Solução.");
            resultados = null;
        }

        if (Objects.isNull(resultados))
            return;

        System.out.println("""
                --------------------
                ::: Respostas :::
                %s 
                ------------------- """.formatted(resultados.toString()));

        String home = System.getProperty("user.home");
        imprimeCirculo(Paths.get(home, "circulo1.log").toString(), resultados.getCirculoMenorAcima());
        imprimeCirculo(Paths.get(home, "circulo2.log").toString(), resultados.getCirculoMenorAbaixo());
    }

    private static void imprimeCirculo(String nomeArquivo, Circulo circulo) {
        try (FileOutputStream out = new FileOutputStream(nomeArquivo)) {
            Double[] intervalos = circulo.intervaloX();
            out.write("## x\tC[0]\tC[1]\n".getBytes());
            double passoCirculo = (intervalos[1] - intervalos[0]) / 500.;
            for (double x = intervalos[0]; x <= intervalos[1]; x += passoCirculo) {
                Double[] pontoCirculo = circulo.calculaY(x);
                out.write("%f\t%f\t%f\n".formatted(
                        x, pontoCirculo[0], pontoCirculo[1]).replaceAll("[,]", ".").getBytes());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}