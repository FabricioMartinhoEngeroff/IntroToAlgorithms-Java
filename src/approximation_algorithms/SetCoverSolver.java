package approximation_algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Algoritmo GREEDY-SET-COVER — O(ln n)-aproximação (CLRS seção 35.3).
 *
 * Estratégia gulosa:
 *   Enquanto ainda existirem elementos não cobertos:
 *     1. Escolhe o subconjunto Si que cobre o MAIOR número de elementos restantes
 *     2. Adiciona Si à solução
 *     3. Marca todos os elementos de Si como cobertos
 *
 * Garantia: |solução| <= H(max_size) * |ótimo|, onde H(k) = ln(k) + 1
 *   (no máximo ~ln(n) vezes o ótimo)
 *
 * Intuição: o primeiro conjunto cobre ao menos 1/k* dos elementos restantes
 * a cada passo, onde k* é o tamanho ótimo. Isso garante o fator logarítmico.
 *
 * Contexto RouteOptix: quais equipes de serviço (subconjuntos) cobrem todos
 * os tipos de entrega (universo)?
 */
public class SetCoverSolver {

    /**
     * Executa GREEDY-SET-COVER e imprime cada passo.
     *
     * @param universe conjunto universo (nomes dos elementos a cobrir)
     * @param subsets  lista de subconjuntos disponíveis
     * @param names    nomes dos subconjuntos
     */
    public void solve(String[] universe, List<Set<String>> subsets, String[] names) {
        System.out.println("\n  ── GREEDY-SET-COVER (CLRS 35.3) ──");
        System.out.println("  Contexto: RouteOptix quer cobrir todos os tipos de entrega com o menor número de equipes.");
        System.out.println("  Estratégia: a cada passo, escolhe a equipe que cobre mais tipos ainda descobertos.\n");

        System.out.print("  Universo (" + universe.length + " elementos): { ");
        for (String e : universe) System.out.print(e + " ");
        System.out.println("}");

        System.out.println("  Subconjuntos disponíveis:");
        for (int i = 0; i < subsets.size(); i++) {
            System.out.printf("    %-15s → %s%n", names[i], subsets.get(i));
        }
        System.out.println();

        Set<String> uncovered = new LinkedHashSet<>(Arrays.asList(universe));
        List<Integer> chosen  = new ArrayList<>();
        int step = 1;

        while (!uncovered.isEmpty()) {
            // encontra o subconjunto com maior cobertura de elementos restantes
            int bestIdx   = -1;
            int bestCount = 0;
            Set<String> bestNew = null;

            for (int i = 0; i < subsets.size(); i++) {
                // interseção com os elementos ainda não cobertos
                Set<String> newCovered = new HashSet<>(subsets.get(i));
                newCovered.retainAll(uncovered);
                if (newCovered.size() > bestCount) {
                    bestCount = newCovered.size();
                    bestIdx   = i;
                    bestNew   = newCovered;
                }
            }

            if (bestIdx == -1) break; // sem mais cobertura possível

            chosen.add(bestIdx);
            uncovered.removeAll(bestNew);

            System.out.printf("  Passo %d: escolhe %-15s → cobre %d novo(s): %s%n",
                    step++, names[bestIdx], bestCount, bestNew);
            System.out.printf("            restam %d elemento(s) sem cobertura%n", uncovered.size());
        }

        System.out.println("\n  ── RESULTADO ──");
        System.out.print("  Equipes escolhidas: { ");
        for (int i : chosen) System.out.print(names[i] + " ");
        System.out.println("}");
        System.out.println("  Total de subconjuntos: " + chosen.size());
        System.out.printf("  Garantia: tamanho <= ln(%d) * ótimo ~= %.1f * ótimo  (log-aproximação)%n",
                universe.length, Math.log(universe.length) + 1);
    }
}
