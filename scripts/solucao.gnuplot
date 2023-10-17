# Variáveis dos resultados do processamento colocadas no inicio deste script para facilitar os testes.
# As 2 variáveis que deveriam ser alteradas para testar as condições do problema sáo "MaiorRaio" e "MenorAcimaRaio"
# todas as demais foram derivadas destas.

MaiotpontoX=0.0
MaiorpontoY=0.0

MaiorRaio=2.0956225228030227
##        2.0956225156784054
MenorAcimaRaio=0.5689315464062967
##             0.568931541603422

quadradoTemp = MaiorRaio - MenorAcimaRaio
quadradoTemp = quadradoTemp * quadradoTemp
quadradoTemp = quadradoTemp - (MenorAcimaRaio*MenorAcimaRaio)
## MenorAcimaPontoY=1.416722355047303 (Esse resultado é o calculado, mas como o objetivo é somente definir os 2 raios, vamos comentar)
MenorAcimaPontoY=sqrt(quadradoTemp)
MenorAcimaPontoX=MenorAcimaRaio

MenorAbaixoPontoX=MenorAcimaPontoY
MenorAbaixoPontoY=MenorAcimaPontoX
MenorAbaixoRaio=MenorAcimaRaio

# Define as caracteristicas do gráfico.
set title "Resolucão do Exercicio"
limite = int(MaiorRaio*10.)/10. + 0.1
set xrange [0:limite]
set yrange [0:limite]
set xtics int(limite)/10.0
set ytics int(limite)/10.0
set size square
set parametric
set trange [0:2*pi]
set grid
set xlabel "Posicao X"
set ylabel "Posicao Y"

# Impressão da parábola usando a forma paramétrica.
plot t,t*t title "parábola" lt rgb "cyan" lw 2

#Funcao do Circulo que será usada de forma paramétrica para evitar as descontinuidades das bordas direta e esqueda do circulo.
fx(t,r1,x01) = r1*cos(t) + x01
fy(t,r1,y01) = r1*sin(t) + y01

# Impressão dos círculos usando a função paramétrica definida com os devidos labels.
replot fx(t,MaiorRaio,0.0), fy(t,MaiorRaio,0.0) title "raio maior" lt rgb "blue" lw 2
replot fx(t,MenorAcimaRaio,MenorAcimaPontoX), fy(t,MenorAcimaRaio,MenorAcimaPontoY) title "raio menor " lt rgb "red" lw 2
replot fx(t,MenorAbaixoRaio,MenorAbaixoPontoX), fy(t,MenorAbaixoRaio,MenorAbaixoPontoY) notitle lt rgb "red" lw 2

# Impressão dos centros de cada um dos circulos menores
replot '+' using (MenorAcimaPontoX):(MenorAcimaPontoY) lt rgb "red" lw 2 notitle
replot '+' using (MenorAcimaPontoY):(MenorAcimaPontoX) lt rgb "red" lw 2 notitle

# Ao usar o replot com uma nova função ou dados, é possível adicionar novos gráficos sem apagar o anterios
# ou fazer linhas muito longas com várias funções / dados para imprimir.

# Para não fechar a janela com o gráfico
pause -1 "teste"

# Salva em um png. (Se necessário)
##set term png
##set output "resultado.png"
##replot
##set term x11