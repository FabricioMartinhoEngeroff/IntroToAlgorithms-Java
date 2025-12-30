Biblioteca de Algoritmos de Matrizes inspirada no CLRS

IDEIA GERAL DO PROJETO

O livro Introduction to Algorithms (CLRS) ensina que resolver problemas de algebra linear nao significa apenas fazer contas, mas organizar transformacoes matematicas de forma estruturada.

Este projeto segue exatamente essa ideia.
Cada conceito matematico importante vira uma classe.
Cada algoritmo vira um modulo separado.
Nenhuma matematica fica espalhada ou misturada com interface ou entrada de dados.

O objetivo e transformar teoria em engenharia de software.

ENTIDADES MATEMATICAS BASICAS

2.1 Classe Matrix

Conceito no livro:
No CLRS, a matriz e o objeto central de um sistema linear. Ela representa uma transformacao linear.

Responsabilidade da classe:
A classe Matrix representa uma matriz como um objeto de primeira classe, nao como um array solto.

Ela e responsavel por:

armazenar os valores da matriz

fornecer acesso controlado aos elementos

garantir encapsulamento dos dados

permitir operacoes basicas como transposicao

permitir copia segura da matriz

Importante:
A classe Matrix nao implementa algoritmos complexos.
Ela apenas representa a estrutura matematica de forma segura.

2.2 Classe Vector

Conceito no livro:
No CLRS, vetores representam termos independentes e solucoes de sistemas do tipo Ax = b.

Responsabilidade da classe:
A classe Vector representa vetores coluna usados em sistemas lineares.

Ela e responsavel por:

armazenar valores do vetor

permitir leitura e escrita controlada

representar semanticamente um vetor matematico

Assim como Matrix, Vector nao contem algoritmos, apenas dados bem modelados.

DECOMPOSICAO DE MATRIZES

3.1 Decomposicao LU

Conceito no livro:
A decomposicao LU escreve uma matriz A como o produto de duas matrizes triangulares:

A = L vezes U

Onde:

L e triangular inferior

U e triangular superior

Ideia do CLRS:
Resolver um sistema linear diretamente e caro.
Separando em L e U, o problema vira dois sistemas mais simples.

Classe LUDecomposition:

Responsabilidade:

receber uma matriz A

calcular as matrizes L e U

armazenar L e U separadamente

Limitacao conceitual:

nao usa pivotamento

pode falhar se aparecer divisao por zero

serve principalmente para entender o algoritmo

3.2 Decomposicao LUP

Conceito no livro:
O CLRS mostra que a decomposicao LU pura nao e estavel numericamente.
A solucao e introduzir uma permutacao de linhas.

A decomposicao vira:

P vezes A = L vezes U

Onde:

P e uma matriz de permutacao

L e triangular inferior

U e triangular superior

Classe LUPDecomposition:

Responsabilidade:

copiar a matriz original para nao altera-la

escolher pivots estaveis

construir o vetor de permutacao

gerar as matrizes L e U

Importancia no CLRS:

evita divisao por zero

melhora estabilidade numerica

e a base para resolver sistemas, inverter matrizes e minimos quadrados

Esta classe e o coracao do projeto.

RESOLUCAO DE SISTEMAS LINEARES

4.1 Substituicao Direta

Conceito no livro:
Para resolver L y = b, onde L e triangular inferior, usa-se substituicao direta.

Classe ForwardSubstitution:

Responsabilidade:

resolver sistemas triangulares inferiores

receber L e b

produzir o vetor y

4.2 Substituicao Reversa

Conceito no livro:
Para resolver U x = y, onde U e triangular superior, usa-se substituicao reversa.

Classe BackSubstitution:

Responsabilidade:

resolver sistemas triangulares superiores

receber U e y

produzir o vetor x

4.3 Solver de Sistema Linear

Conceito no livro:
Resolver Ax = b usando LUP envolve tres etapas:

1 aplicar permutacao em b
2 resolver L y = P b
3 resolver U x = y

Classe LinearSystemSolver:

Responsabilidade:

coordenar todas essas etapas

esconder a complexidade do algoritmo

entregar apenas o vetor solucao x

INVERSAO DE MATRIZES

Conceito no livro:
O CLRS ensina que inverter uma matriz nao e uma operacao basica.
Ela pode ser feita resolvendo varios sistemas lineares.

Para cada coluna da identidade I, resolve-se:

A x = e_i

Classe MatrixInverter:

Responsabilidade:

usar LUP para fatorar A

resolver n sistemas lineares

montar a matriz inversa coluna por coluna

MINIMOS QUADRADOS

Conceito no livro:
Quando o sistema Ax = b tem mais equacoes que incognitas, ele pode nao ter solucao exata.

O CLRS mostra que o problema pode ser transformado em:

A transposta vezes A vezes x = A transposta vezes b

Classe LeastSquaresSolver:

Responsabilidade:

calcular A transposta

montar as equacoes normais

resolver o novo sistema usando LUP

Resultado:

uma solucao aproximada que minimiza o erro

CLASSE UTILITARIA

Classe MatrixUtils:

Responsabilidade:

multiplicacao matriz por matriz

multiplicacao matriz por vetor

Importante:

mantida separada para nao poluir as classes matematicas

usada apenas para validacao e calculos auxiliares

CLASSE MAIN

Conceito geral:

A classe Main funciona como uma interface de linha de comando didatica.

Ela permite:

escolher algoritmos

visualizar matrizes e vetores

verificar resultados numericamente

entender o fluxo completo do CLRS

A classe Main nao implementa matematica.
Ela apenas orquestra chamadas aos algoritmos.

FILOSOFIA DO PROJETO

cada classe tem uma unica responsabilidade

cada conceito do livro vira um modulo

nenhuma logica matematica fica misturada com interface

o codigo reflete diretamente a teoria

Este projeto nao e apenas um conjunto de algoritmos.
Ele e uma traducao fiel do pensamento do CLRS para software.