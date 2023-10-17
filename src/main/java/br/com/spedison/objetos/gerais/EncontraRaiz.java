package br.com.spedison.objetos.gerais;

import br.com.spedison.objetos.Caracteristicas;

public class EncontraRaiz {

    private EncontraRaiz() {
    }

    public static Double newtonRapson1D(Curva curva, double xInicial, double xFinal, int maxPassos) {


        double xProcessado = (xFinal + xInicial) / 2.0;
        double ultimoXProcessado = xProcessado;
        double menorDiferenca = curva.calcula(xProcessado);
        int contaPassos = 0;

        while (Math.abs(menorDiferenca) > Caracteristicas.ERRO && xProcessado <= xFinal && xProcessado >= xInicial && contaPassos < maxPassos) {
            contaPassos++;

            double limiteDerivada = Caracteristicas.H_DERIVADA;
            double derivada;
            double proximoPonto;
            double contagem = 100;
            do {
                derivada = CalculaDerivadas.derivava1D(curva, xProcessado, limiteDerivada);
                proximoPonto = xProcessado - menorDiferenca / derivada;
                limiteDerivada *= 10;
                contagem--;
            } while ((Double.isInfinite(proximoPonto) || Double.isNaN(proximoPonto)) && contagem > 0);

            if (contagem == 0)
                return null;

            if (proximoPonto < xInicial)
                proximoPonto = xInicial;
            if (proximoPonto > xFinal)
                proximoPonto = xFinal;

            ultimoXProcessado = xProcessado;
            xProcessado = proximoPonto;
            menorDiferenca = curva.calcula(xProcessado);
            System.out.println("Diferença = " + menorDiferenca);

            if (Math.abs(ultimoXProcessado - xProcessado) < Caracteristicas.ERRO)
                break;
        }

        // Qualquer um dos critérios não valida a posicão x como válida.
        if (xProcessado < xInicial || xProcessado > xFinal || menorDiferenca > Caracteristicas.ERRO)
            return null;

        return xProcessado;
    }

    public static Double buscaUsandoBisseccao(Curva curva, double xInicial, double xFinal, int maxPassos) {
        Double p1 = curva.calcula(xInicial);
        Double px1 = xInicial;
        Double px2 = xFinal;
        Double xMedio = (px1 + px2) / 2.;
        Double pMedio = curva.calcula(xMedio);
        int contagem = 0;
        while (contagem++ < maxPassos && pMedio > Caracteristicas.ERRO && Math.abs(px1 - px2) > Caracteristicas.ERRO) {
            if (Math.abs(pMedio) < Math.abs(p1)) {
                px1 = xMedio;
            } else {
                px2 = xMedio;
            }
            xMedio = (px1 + px2) / 2.;
            pMedio = curva.calcula(xMedio);
            p1 = curva.calcula(px1);
        }
        return xMedio;
    }

}