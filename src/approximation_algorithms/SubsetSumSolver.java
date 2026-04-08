package approximation_algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Algoritmos para o Subset Sum Problem (CLRS seção 35.5).
 *
 * Versão exata (EXACT-SUBSET-SUM):
 *   Constrói incrementalmente a lista de todas as somas possíveis <= alvo.
 *   Retorna a maior soma encontrada.
 *   Complexidade: exponencial no pior caso.
 *
 * Versão aproximada FPTAS (APPROX-SUBSET-SUM):
 *   Igual à versão exata, mas aplica TRIMMING a cada iteração:
 *   remove somas muito próximas entre si, mantendo apenas representantes.
 *   Controla o erro máximo via parâmetro epsilon (eps).
 *   Complexidade: O(n^2 / eps)  — polinomial em n e 1/eps.
 *
 * Trimming com parâmetro delta:
 *   Remove y de uma lista se existe z <= y com y <= (1 + delta) * z.
 *   Mantém a lista pequena sem perder somas significativamente diferentes.
 *
 * Garantia FPTAS: resultado >= (1 - eps) * ótimo
 *
 * Contexto RouteOptix: carregar o caminhão com caixas o mais próximo possível
 * do limite de peso, sem ultrapassar.
 */
public class SubsetSumSolver {

    // ── VERSÃO EXATA ──────────────────────────────────────────────────────────

    /**
     * EXACT-SUBSET-SUM do CLRS (seção 35.5).
     *
     * @param items  pesos dos itens
     * @param names  nomes dos itens
     * @param target peso alvo (limite do caminhão)
     */
    public void solveExact(int[] items, String[] names, int target) {
        System.out.println("\n  ── EXACT-SUBSET-SUM (CLRS 35.5) ──");
        System.out.println("  Contexto: RouteOptix quer carregar caixas o mais próximo do limite de peso.");
        System.out.println("  Versão exata: constrói TODAS as somas possíveis.\n");

        System.out.println("  Itens disponíveis:");
        for (int i = 0; i < items.length; i++) {
            System.out.printf("    %-10s = %d kg%n", names[i], items[i]);
        }
        System.out.println("  Alvo: " + target + " kg\n");

        // lista de somas possíveis — começa apenas com 0
        List<Integer> sums = new ArrayList<>();
        sums.add(0);

        for (int i = 0; i < items.length; i++) {
            // novas somas = somas anteriores + items[i]
            List<Integer> newSums = new ArrayList<>();
            for (int s : sums) {
                int ns = s + items[i];
                if (ns <= target) newSums.add(ns);
            }
            // junta e remove duplicatas mantendo ordenado
            sums.addAll(newSums);
            sums = new ArrayList<>(new TreeSet<>(sums));
            System.out.printf("  Após %-10s: %s%n", names[i], sums);
        }

        int best = sums.stream().mapToInt(Integer::intValue).max().orElse(0);

        System.out.println("\n  ── RESULTADO ──");
        System.out.println("  Melhor soma encontrada : " + best + " kg");
        System.out.println("  Diferença para o alvo  : " + (target - best) + " kg");
        System.out.println("  Qualidade              : solução exata (ótima)");
    }

    // ── VERSÃO APROXIMADA — FPTAS ─────────────────────────────────────────────

    /**
     * APPROX-SUBSET-SUM do CLRS (seção 35.5) — FPTAS com trimming.
     *
     * @param items  pesos dos itens
     * @param names  nomes dos itens
     * @param target peso alvo
     * @param eps    erro máximo aceito (ex: 0.1 = 10%)
     */
    public void solveApprox(int[] items, String[] names, int target, double eps) {
        System.out.println("\n  ── APPROX-SUBSET-SUM — FPTAS (CLRS 35.5) ──");
        System.out.println("  Contexto: RouteOptix aceita solução próxima do alvo — mais rápido que a versão exata.");
        System.out.println("  Trimming: remove somas muito próximas entre si, reduzindo o tamanho da lista.\n");

        System.out.print("  Itens: ");
        for (int i = 0; i < items.length; i++) System.out.printf("%s=%dkg ", names[i], items[i]);
        System.out.printf("%n  Alvo: %d kg    eps=%.2f (erro <= %.0f%%)%n%n", target, eps, eps * 100);

        // delta de trimming por iteração: eps / (2n)
        double delta = eps / (2.0 * items.length);
        System.out.printf("  Parametro de trimming por iteracao: delta = eps/(2n) = %.4f%n%n", delta);

        // lista de somas possíveis — começa com 0
        List<Integer> sums = new ArrayList<>();
        sums.add(0);

        for (int i = 0; i < items.length; i++) {
            // gera novas somas
            List<Integer> newSums = new ArrayList<>();
            for (int s : sums) {
                int ns = s + items[i];
                if (ns <= target) newSums.add(ns);
            }
            sums.addAll(newSums);
            sums = new ArrayList<>(new TreeSet<>(sums));

            // aplica trimming: remove somas muito próximas umas das outras
            int beforeTrim = sums.size();
            sums = trim(sums, delta);

            System.out.printf("  Apos %-10s: %d somas → apos trim → %d somas  | lista: %s%n",
                    names[i], beforeTrim, sums.size(), sums);
        }

        int best = sums.stream().mapToInt(Integer::intValue).max().orElse(0);

        System.out.println("\n  ── RESULTADO ──");
        System.out.println("  Melhor soma encontrada : " + best + " kg");
        System.out.println("  Diferença para o alvo  : " + (target - best) + " kg");
        System.out.printf("  Garantia               : resultado >= (1 - %.2f) x ótimo  [FPTAS]%n", eps);
        System.out.printf("  Complexidade           : O(n^2 / eps) = O(%d)%n",
                (int)(items.length * items.length / eps));
    }

    // ── TRIM ─────────────────────────────────────────────────────────────────

    /**
     * Remove da lista ordenada os valores que estão muito próximos de um predecessor.
     * Mantém um valor y apenas se y > last * (1 + delta).
     *
     * @param sortedList lista de somas em ordem crescente
     * @param delta      parâmetro de proximidade
     * @return lista reduzida, mantendo apenas representantes relevantes
     */
    private List<Integer> trim(List<Integer> sortedList, double delta) {
        if (sortedList.isEmpty()) return sortedList;

        List<Integer> result = new ArrayList<>();
        double last = sortedList.get(0);
        result.add(sortedList.get(0));

        for (int s : sortedList) {
            // inclui apenas se for suficientemente maior que o último mantido
            if (s > last * (1.0 + delta)) {
                result.add(s);
                last = s;
            }
        }
        return result;
    }
}
