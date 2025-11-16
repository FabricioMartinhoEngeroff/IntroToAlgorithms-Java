Strassen’s Algorithm for Matrix Multiplication
Contexto histórico e conceitual

Até os anos 60, acreditava-se que multiplicar matrizes era intrinsecamente Θ(n³) — porque tanto o método “ingênuo” quanto o “divide and conquer puro” (que quebra em submatrizes e faz 8 multiplicações recursivas) davam sempre o mesmo custo assintótico.

Em 1969, Volker Strassen mostrou que dava para reduzir esse número de multiplicações:
 ao invés de 8 multiplicações, ele conseguiu resolver com 7 multiplicações e várias somas/subtrações extras.


Essa troca alterou a recorrência do algoritmo para:

𝑇(𝑛)= 7 𝑇(𝑛 / 2) + Θ (𝑛 2) T(n)=7T(n/2)+Θ(n2)

Significado prático:

T(n)=Θ(n log 2​7)≈Θ(n2.81)

- Menos multiplicações (caras), mais somas/subtrações (baratas).
- Para matrizes pequenas, Strassen não compensa.
- Para matrizes grandes, Strassen ganha.

Foi o primeiro algoritmo a quebrar a barreira de n³.
Hoje existem até algoritmos mais rápidos (~Θ(n^2.37)), mas Strassen foi o marco inicial.

Aplicação no nosso caso

Em grafos, se A é a matriz de adjacência, então A ^ k [i] [j] representa o número de caminhos de comprimento k entre os vértices i e j.
Logo, ao usar MatrixPower junto com StrassenMultiplier, a gente consegue calcular, por exemplo, A^7 e descobrir quantos caminhos de 7 passos existem no grafo.

Como o código aplica esse conceito

  model 
   -Matrix.java

  service
   - MatrixMultiplier.java
   - SimpleMultiplier.java
   - StrassenMultiplier.java
   - MatrixPower.java

   Demo 
   - Man

 1 - model > Matrix.java

Um wrapper simples para uma matriz quadrada n×n.

Métodos principais:

size(), get(), set(), raw() → acesso básico.

add(), sub() → operações elemento a elemento, usadas dentro do Strassen.

identity(n) → matriz identidade, usada na exponenciação.

 - Ligação com a teoria: é a base que permite somar e subtrair blocos (A11+A22, B12−B22 etc.), igual ao que o Strassen precisa.

 2 - service > MatrixMultiplier.java

Interface que define o contrato do motor de multiplicação:

public interface MatrixMultiplier {
Matrix multiply(Matrix A, Matrix B);
}

 - Permite plugar implementações diferentes (SimpleMultiplier ou StrassenMultiplier) sem mudar quem consome.

 3 - service/SimpleMultiplier.java

Implementação clássica com 3 loops (O(n³)).

Boa para matrizes pequenas, porque não tem overhead de somas extras.

Ligação com a teoria: representa o “baseline” que o Strassen melhora.

 4 - service > StrassenMultiplier.java

Implementa o Strassen:
- Divide A e B em quadrantes (A11, A12, A21, A22).
- Calcula M1..M7 (7 multiplicações recursivas).

Combina para formar C11, C12, C21, C22:
C11 = M1 + M4 − M5 + M7
C12 = M3 + M5
C21 = M2 + M4
C22 = M1 − M2 + M3 + M6

Usa padding quando o tamanho não é potência de 2.
Tem um threshold: se a matriz for pequena, cai de volta no SimpleMultiplier.

 - Ligação com a teoria: é a implementação direta do algoritmo que aparece no CLRS, com os 7 produtos.

 5 - service > MatrixPower.java

Faz a exponenciação binária: Se k é ímpar → multiplica pelo resultado parcial.
Sempre substitui a base por base*base e divide k por 2.
Complexidade: O(log k) multiplicações de matriz.


 - Ligação com a teoria: CLRS também apresenta exponenciação rápida em outros contextos; aqui aplicamos às matrizes para calcular A^k.

 6 - app > Man.java
   Programa de kruskalmst: Cria uma matriz de adjacência A (exemplo 4×4).
- Calcula A^7 com SimpleMultiplier e com StrassenMultiplier.
- Imprime os dois resultados (iguais).

Mostra que: MatrixPower funciona (exponenciação rápida).
StrassenMultiplier e SimpleMultiplier chegam ao mesmo resultado.

Resultado esperado: Os dois motores (SimpleMultiplier e StrassenMultiplier) produzem a mesma matriz A^7, provando a correção.