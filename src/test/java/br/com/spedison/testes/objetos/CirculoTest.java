package br.com.spedison.testes.objetos;

import br.com.spedison.objetos.gerais.Circulo;
import br.com.spedison.objetos.gerais.Curva;
import br.com.spedison.objetos.gerais.Ponto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CirculoTest {

    @Test
    void pontosAcimaDaCurva(){
        Circulo c = new Circulo(0,30,3);
        Curva cr = (x) -> 11 + x*x;
        assertEquals(true,c.todosPontosDoCirculoEstaoAcimaDaCurva(cr,1.E-4));
        c.setPontoY(0.);
        assertEquals(true,c.todosPontosDoCirculoEstaoAbaixoDaCurva(cr,1.E-4));
    }

    @Test
    void testCruzaCirculoComCirculo() {
        Circulo c1 = new Circulo();
        c1.setRaio(10.);
        c1.setPontoY(0.);
        c1.setPontoX(10.);
        Circulo c2 = new Circulo();
        c2.setRaio(10.);
        c2.setPontoY(0.);
        c2.setPontoX(30.);
        Ponto[] x = c2.pontosInterseccao(c1);
        System.out.println("Ponto de intersercao = %s \n %s".formatted(x[0], x[1]));

    }
}