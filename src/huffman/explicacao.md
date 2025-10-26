Explicação simples — Algoritmo de Huffman

O algoritmo de Huffman é usado para comprimir dados.
Ele cria códigos binários (com 0 e 1) mais curtos para os símbolos que aparecem com mais frequência e códigos mais longos para os menos frequentes.
Dessa forma, o texto ocupa menos espaço quando convertido em bits.

O método é chamado de estratégia gulosa (greedy) porque, em cada passo, ele escolhe os dois menores valores disponíveis e os combina em um novo nó, repetindo isso até restar apenas um nó — a raiz da árvore.

⚙Como o código funciona

HuffmanNode
Representa cada nó da árvore.
Guarda o caractere (ch), sua frequência (freq), e os filhos (left, right).

HuffmanCoding

build() → cria a árvore combinando os dois nós de menor frequência.

codes() → gera os códigos binários de cada caractere.

encode() → transforma o texto em bits usando a tabela de códigos.

decode() → reconstrói o texto original a partir dos bits.

costBits() → calcula o total de bits usados pela codificação.

Main
Cria as frequências de exemplo, monta a árvore, gera os códigos, mostra o custo total e testa a codificação e decodificação de uma palavra (ex: "cafe").

Exemplo simples
Letra	Frequência	Código
a	45	0
b	13	101
c	12	100
d	16	111
e	9	1101
f	5	1100

O texto é codificado usando esses códigos e depois decodificado pela árvore de Huffman.