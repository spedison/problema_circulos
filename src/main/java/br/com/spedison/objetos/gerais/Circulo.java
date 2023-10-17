package br.com.spedison.objetos.gerais;


import br.com.spedison.objetos.Caracteristicas;

import java.util.Objects;

public class Circulo {
    private double pontoX;
    private double pontoY;
    private double raio;

    public Circulo() {
    }

    public Circulo(double pontoX, double pontoY, double raio) {
        this.pontoX = pontoX;
        this.pontoY = pontoY;
        this.raio = raio;
    }

    public double getPontoX() {
        return pontoX;
    }

    public void setPontoX(double pontoX) {
        this.pontoX = pontoX;
    }

    public double getPontoY() {
        return pontoY;
    }

    public void setPontoY(double pontoY) {
        this.pontoY = pontoY;
    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    public Double[] calculaY(double x) {
        // R² = ( x-x0 ) ² + ( y - y0 ) ²
        // R² - ( x-x0 ) ²  =   ( y - y0 ) ²
        // R² - ( x-x0 ) ²  =    y² + y0² - 2*y*y0
        // R² - ( x-x0 ) ² - y0² = y² - 2*y*y0
        // ------------------------------
        // || C = R² - ( x-x0 ) ² - y0² || // Constante
        // ------------------------------
        // y² - 2*y0*y - C = 0
        // delta = sqrt( (-2*y0)² + 4 * C )
        // Para 1 ponto o delta deve ser > 0
        //  x[0,1] = -B +- sqrt(delta) / 2

        final double constante = raio * raio - Math.pow(x - pontoX, 2.) - (pontoY * pontoY);
        final double constanteB = -2 * pontoY;
        final double deltaAoQuadrado = Math.pow(constanteB, 2.) + 4 * constante;
        if (deltaAoQuadrado < 0.)
            return new Double[2];

        final double delta = Math.sqrt(deltaAoQuadrado);
        Double[] ret = new Double[2];
        ret[0] = (-constanteB + delta) / 2.;
        ret[1] = (-constanteB - delta) / 2.;
        return ret;
    }

    public double[] distanciaYCurva(Curva curva, double x) {
        var yCurva = curva.calcula(x);
        var yCirculo = calculaY(x);
        return new double[]{yCirculo[0] - yCurva, yCirculo[1] - yCurva};
    }

    public Double[] intervaloX() {
        Double[] ret = new Double[2];
        // Porque somente e subtrair o ERRO aqui ?
        // Como não estou usando o espaço paramétrico, estou usando somente o X e Y em pontos
        // muito próximos das laterais direita e esquerda devido erros de arredonamento (sobretudo para números pequenos)
        // então a adição e remoção do ERRO garante que os pontos processados ficarão dentro do círculo
        ret[0] = (getPontoX() - getRaio()) + (Caracteristicas.ERRO / 10.);
        ret[1] = (getPontoX() + getRaio()) - (Caracteristicas.ERRO / 10.);
        return ret;
    }

    public boolean todosPontosDoCirculoEstaoAcimaDaCurva(Curva curva, double passo) {
        Double[] range = intervaloX();

        double inicio = range[0];
        double fim = range[1];
        while (Math.abs(fim - inicio) > Caracteristicas.ERRO && fim > inicio) {
            Double[] p = calculaY(inicio);
            if (Objects.isNull(p[0]) || Objects.isNull(p[1]))
                throw new RuntimeException("Problemas ao definir o ponto para x = " + inicio);

            double c = curva.calcula(inicio);
            if (p[0] < c || p[1] < c)
                return false;

            p = calculaY(fim);
            if (Objects.isNull(p[0]) || Objects.isNull(p[1]))
                throw new RuntimeException("Problemas ao definir o ponto para x = " + fim);

            c = curva.calcula(fim);
            if (p[0] < c || p[1] < c)
                return false;
            inicio += passo;
            fim -= passo;
        }
        return true;
    }

    public boolean todosPontosDoCirculoEstaoAbaixoDaCurva(Curva curva, double passo) {
        Double[] range = intervaloX();
        double inicio = range[0];
        double fim = range[1];
        while (Math.abs(fim - inicio) > Caracteristicas.ERRO && fim > inicio) {
            Double[] p = calculaY(inicio);

            if (Objects.isNull(p[0]) || Objects.isNull(p[1]))
                throw new RuntimeException("Problemas ao definir o ponto para x = " + inicio);

            double c = curva.calcula(inicio);
            if (p[0] > c || p[1] > c)
                return false;

            p = calculaY(fim);
            if (Objects.isNull(p[0]) || Objects.isNull(p[1]))
                throw new RuntimeException("Problemas ao definir o ponto para x = " + fim);

            c = curva.calcula(fim);
            if (p[0] > c || p[1] > c)
                return false;
            inicio += passo;
            fim -= passo;
        }
        return true;
    }

    public Double distanciaCirculoComCirculo(Circulo outroCirculo, double xInicial, double xFinal, int maxPassos) {


        Ponto[] cruzamentos = this.pontosInterseccao(outroCirculo);
        if (Objects.nonNull(cruzamentos)) {

            Curva calculaDiferenca = x -> {
                Double[] pontoNaCurva = outroCirculo.calculaY(x);
                Double[] pontosNoCirculo = this.calculaY(x);

                if (Objects.isNull(pontoNaCurva))
                    return Double.POSITIVE_INFINITY;

                if (Objects.isNull(pontosNoCirculo))
                    return Double.POSITIVE_INFINITY;

                return
                        Math.min(
                                Math.min(Math.abs(pontoNaCurva[0] - pontosNoCirculo[0]), Math.abs(pontoNaCurva[0] - pontosNoCirculo[1])),
                                Math.min(Math.abs(pontoNaCurva[1] - pontosNoCirculo[0]), Math.abs(pontoNaCurva[1] - pontosNoCirculo[1]))
                        );
            };

            return EncontraRaiz.newtonRapson1D(calculaDiferenca, xInicial, xFinal, maxPassos);

        } else {
            return 0.0;
        }

    }

    // Método para calcular a menor distância entre dois círculos
    public double calcularMenorDistancia(Circulo outroCirculo) {

        if (Objects.nonNull(pontosInterseccao(outroCirculo))) {
            return 0.0;
        }

        double distanciaCentros = Math.sqrt(Math.pow(this.pontoX - outroCirculo.getPontoX(), 2) +
                Math.pow(this.pontoY - outroCirculo.getPontoY(), 2));


        if (estaContidoEm(outroCirculo) || outroCirculo.estaContidoEm(this)) {
            return Math.max(this.getRaio(), outroCirculo.getRaio()) - distanciaCentros - Math.min(this.getRaio(), outroCirculo.getRaio());
        }

        return distanciaCentros - (this.raio + outroCirculo.getRaio());
    }

    public Double cruzaComCurva(Curva curva, double xInicial, double xFinal, int maxPassos) {
        double xProcessado = xInicial;
        Double[] pontosNoCirculoTeste = calculaY(xProcessado);

        if (Objects.isNull(pontosNoCirculoTeste))
            return null;

        xProcessado = xFinal;
        pontosNoCirculoTeste = calculaY(xProcessado);

        if (Objects.isNull(pontosNoCirculoTeste))
            return null;

        Curva calculaDiferenca = x -> {
            double pontoNaCurva = curva.calcula(x);
            Double[] pontosNoCirculo = this.calculaY(x);
            return Math.min(Math.abs(pontoNaCurva - pontosNoCirculo[0]), Math.abs(pontoNaCurva - pontosNoCirculo[1]));
        };

        return EncontraRaiz.newtonRapson1D(calculaDiferenca, xInicial, xFinal, maxPassos);
    }


    // Verifica se este círculo está completamente contido em outro círculo
    public boolean estaContidoEm(Circulo outroCirculo) {
        double distanciaCentros = Math.sqrt(Math.pow(this.pontoX - outroCirculo.getPontoX(), 2) +
                Math.pow(this.pontoY - outroCirculo.getPontoY(), 2));

        return distanciaCentros + this.raio <= outroCirculo.getRaio();
    }

    public Ponto[] pontosInterseccao(Circulo outroCirculo) {
        Ponto[] ret = new Ponto[2];
        double d = Math.sqrt(Math.pow(getPontoX() - outroCirculo.getPontoX(), 2) +
                Math.pow(getPontoY() - outroCirculo.getPontoY(), 2));

        // Verificar se os círculos não têm interseção
        if (d > (getRaio() + outroCirculo.getRaio()) || d < Math.abs(getRaio() - outroCirculo.getRaio())) {
            System.out.println("Os círculos não têm interseção.");
            return ret;
        } else {
            // Calcular a distância entre os pontos de interseção
            double a = (getRaio() * getRaio() - outroCirculo.getRaio() * outroCirculo.getRaio() + d * d) / (2 * d);
            double h = Math.sqrt(getRaio() * getRaio() - a * a);

            // Calcular os pontos de interseção
            double x3 = getPontoX() + a * (outroCirculo.getPontoX() - getPontoX()) / d + h * (outroCirculo.getPontoY() - getPontoY()) / d;
            double y3 = getPontoY() + a * (outroCirculo.getPontoY() - getPontoY()) / d - h * (outroCirculo.getPontoX() - getPontoX()) / d;

            double x4 = getPontoX() + a * (outroCirculo.getPontoX() - getPontoX()) / d - h * (outroCirculo.getPontoY() - getPontoY()) / d;
            double y4 = getPontoY() + a * (outroCirculo.getPontoY() - getPontoY()) / d + h * (outroCirculo.getPontoX() - getPontoX()) / d;

            ret[0] = new Ponto(x3, y3);
            ret[1] = new Ponto(x4, y4);
            System.out.println("Pontos de interseção: (" + x3 + ", " + y3 + ") e (" + x4 + ", " + y4 + ")");
            return ret;
        }
    }


    @Override
    public String toString() {
        return "Circulo{" +
                "  pontoX=" + pontoX +
                ", pontoY=" + pontoY +
                ", raio=" + raio + '}';
    }
}
