package number_theory_algorithms.lab.core;

import java.util.ArrayList;
import java.util.List;

// Armazena linhas de resultado para exibir formatado
public class AlgorithmResult {
    private final String title;
    private final List<String> lines = new ArrayList<>();

    public AlgorithmResult(String title) {
        this.title = title;
    }

    public AlgorithmResult addLine(String line) {
        lines.add(line);
        return this; // permite encadear: res.addLine("a").addLine("b")
    }

    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== ").append(title).append(" ===\n");
        for (String l : lines) {
            sb.append("• ").append(l).append("\n"); // bullet point limpo
        }
        return sb.toString();
    }
}