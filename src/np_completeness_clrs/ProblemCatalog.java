package np_completeness_clrs;

/**
 * Catálogo de instâncias concretas de problemas NP-Completos com contexto real.
 *
 * Tema: Empresa de Logística e Entregas "FastLog"
 *
 * Cada problema é uma instância pequena o suficiente para entender,
 * mas que já representa a explosão combinatória real.
 */
public class ProblemCatalog {

    // ── TSP — Rota do Entregador ──────────────────────────────────────────────
    public Problem buildTSP() {
        return new Problem(
            ProblemType.TSP,

            // cenário real
            "A FastLog tem 5 clientes para entregar hoje: depósito (D), " +
            "Alfa (A), Beta (B), Gama (G) e Delta (Δ). " +
            "O entregador deve sair do depósito, visitar TODOS os clientes " +
            "exatamente uma vez e retornar ao depósito. " +
            "Existe uma rota com custo total ≤ 85 km?",

            // versão de decisão
            "Dado o grafo de distâncias entre os 5 pontos, " +
            "existe um ciclo hamiltoniano com custo ≤ 85 km?",

            // por que é difícil
            "Para n cidades, o número de rotas possíveis é (n-1)!/2. " +
            "Com 5 cidades: (5-1)!/2 = 12 rotas. " +
            "Com 20 cidades: mais de 60 trilhões de rotas. " +
            "Não existe algoritmo polinomial conhecido que garanta a solução ótima.",

            // como verificar (NP membership)
            "Dada uma sequência de cidades como certificado: " +
            "1) verificar que todos os 5 pontos aparecem exatamente uma vez; " +
            "2) somar as distâncias da rota; " +
            "3) checar se a soma ≤ 85 km. " +
            "Isso é feito em O(n) — polinomial!",

            // tamanho do brute force
            "(n-1)!/2 rotas  →  4!/2 = 12 rotas para n=5  |  19!/2 ≈ 60 trilhões para n=20",

            5  // tamanho da instância
        );
    }

    // ── SUBSET SUM — Carga do Caminhão ────────────────────────────────────────
    public Problem buildSubsetSum() {
        return new Problem(
            ProblemType.SUBSET_SUM,

            // cenário real
            "O caminhão da FastLog comporta exatamente 45 kg de carga útil. " +
            "Existem 6 pacotes disponíveis com pesos: " +
            "P1=10kg, P2=20kg, P3=15kg, P4=25kg, P5=5kg, P6=8kg. " +
            "É possível selecionar pacotes que somem exatamente 45 kg " +
            "para aproveitar a capacidade total?",

            // versão de decisão
            "Dado o conjunto S = {10, 20, 15, 25, 5, 8} e alvo t = 45, " +
            "existe um subconjunto de S cuja soma é exatamente 45?",

            // por que é difícil
            "Para n itens, existem 2^n subconjuntos possíveis. " +
            "Com 6 itens: 2^6 = 64 subconjuntos. " +
            "Com 40 itens: 2^40 > 1 trilhão de subconjuntos. " +
            "Busca por força bruta fica inviável rapidamente.",

            // como verificar
            "Dado um subconjunto como certificado: " +
            "1) verificar que todos os elementos pertencem a S; " +
            "2) somar os pesos; " +
            "3) checar se a soma = 45. " +
            "Verificação em O(n) — polinomial!",

            "2^n subconjuntos  →  2^6 = 64 para n=6  |  2^40 > 1 trilhão para n=40",

            6
        );
    }

    // ── VERTEX COVER — Cobertura de Rotas ────────────────────────────────────
    public Problem buildVertexCover() {
        return new Problem(
            ProblemType.VERTEX_COVER,

            // cenário real
            "A FastLog tem 5 cidades (A, B, C, D, E) conectadas por rotas. " +
            "Rotas existentes: A-B, A-C, B-D, C-D, D-E, B-E. " +
            "Para instalar câmeras de monitoramento, precisa cobrir TODAS as rotas. " +
            "Câmeras ficam nas cidades. Uma câmera cobre todas as rotas que " +
            "passam por aquela cidade. É possível cobrir tudo com ≤ 2 câmeras?",

            // versão de decisão
            "Dado o grafo G=(V,E) com |V|=5, |E|=6, " +
            "existe um vertex cover de tamanho ≤ 2?",

            // por que é difícil
            "O número de subconjuntos de vértices com tamanho k é C(n,k). " +
            "Com n=5 e k=2: C(5,2) = 10 candidatos. " +
            "Com n=50 e k=10: C(50,10) ≈ 10 bilhões de candidatos. " +
            "O problema é NP-Completo — reduz de CLIQUE via grafo complementar.",

            // como verificar
            "Dado um conjunto de vértices como certificado: " +
            "1) verificar que o conjunto tem ≤ 2 elementos; " +
            "2) para cada aresta (u,v), checar se u OU v está no conjunto; " +
            "3) se todas as arestas forem cobertas → certificado válido. " +
            "Verificação em O(|E|) — polinomial!",

            "C(n,k) candidatos  →  C(5,2)=10 para n=5,k=2  |  C(50,10)≈10B para n=50,k=10",

            5
        );
    }

    // ── SAT — Regras de Serviço ───────────────────────────────────────────────
    public Problem buildSAT() {
        return new Problem(
            ProblemType.SAT,

            // cenário real
            "O sistema da FastLog tem 3 serviços: " +
            "Rastreamento (R), Notificação (N) e Backup (B). " +
            "Regras de negócio: " +
            "(R OU N) — pelo menos um deve estar ativo; " +
            "(NÃO R OU B) — se Rastreamento ativo, Backup obrigatório; " +
            "(NÃO N OU NÃO B) — Notificação e Backup não podem estar ativos juntos. " +
            "Existe uma configuração que satisfaz TODAS as regras?",

            // versão de decisão
            "Dada a fórmula em 3-CNF: " +
            "(R ∨ N) ∧ (¬R ∨ B) ∧ (¬N ∨ ¬B), " +
            "existe uma atribuição booleana que satisfaz a fórmula?",

            // por que é difícil
            "Para n variáveis booleanas, existem 2^n atribuições possíveis. " +
            "Com 3 variáveis: 2^3 = 8 atribuições. " +
            "Com 50 variáveis: 2^50 > 1 quadrilhão de atribuições. " +
            "SAT foi o PRIMEIRO problema provado NP-Completo (Teorema de Cook, 1971).",

            // como verificar
            "Dada uma atribuição R=true/false, N=true/false, B=true/false: " +
            "1) substituir os valores em cada cláusula; " +
            "2) avaliar cada cláusula como OR dos literais; " +
            "3) checar se TODAS as cláusulas são verdadeiras. " +
            "Verificação em O(n × cláusulas) — polinomial!",

            "2^n atribuições  →  2^3 = 8 para n=3  |  2^50 > 1 quadrilhão para n=50",

            3
        );
    }

    // ── SCHEDULING — Escala de Funcionários ──────────────────────────────────
    public Problem buildScheduling() {
        return new Problem(
            ProblemType.SCHEDULING,

            // cenário real
            "A FastLog tem 4 turnos a cobrir: Manhã (M), Tarde (T), Noite (N), Final-de-semana (FDS). " +
            "Funcionários disponíveis: " +
            "Ana (pode: M, T), Bruno (pode: T, N), Carlos (pode: N, FDS), Diana (pode: M, FDS). " +
            "Cada turno precisa de exatamente 1 funcionário. " +
            "É possível montar uma escala completa usando apenas 2 funcionários?",

            // versão de decisão
            "Dado um conjunto de turnos T, funcionários F com disponibilidades, " +
            "existe uma atribuição que cobre todos os turnos usando ≤ 2 funcionários?",

            // por que é difícil
            "Para n funcionários escolhendo k deles, há C(n,k) combinações. " +
            "Depois, para cada combinação, há k^n formas de distribuir turnos. " +
            "O problema é NP-Completo — reduz de 3-CNF-SAT mapeando " +
            "variáveis → funcionários e cláusulas → grupos de turnos.",

            // como verificar
            "Dada uma atribuição funcionário→turno como certificado: " +
            "1) verificar que no máximo k funcionários distintos foram usados; " +
            "2) para cada turno, checar se tem exatamente 1 funcionário; " +
            "3) checar se o funcionário tem disponibilidade para aquele turno. " +
            "Verificação em O(turnos + funcionários) — polinomial!",

            "C(n,k) × k^m combinações  →  C(4,2)×2^4 = 96 para n=4,k=2,m=4",

            4
        );
    }
}
