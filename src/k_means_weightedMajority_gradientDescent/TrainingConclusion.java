package k_means_weightedMajority_gradientDescent;

/**
 * Conclusão gerada pelo algoritmo após análise da sessão de treino.
 */
public class TrainingConclusion {

    public boolean isHomeostasisBroken;   // homeostase foi quebrada?
    public boolean isJunkVolume;          // volume é morto/inútil?
    public boolean techniqueFailed;       // falha técnica detectada?
    public double  adaptationScore;       // score de adaptação gerado (0-10)
    public double  systemicFatigue;       // fadiga sistêmica calculada
    public double  suggestedLoadNext;     // carga sugerida para próxima sessão
    public String  recommendation;        // texto de conclusão

    @Override
    public String toString() {
        return "\n╔══════════════════════════════════════════╗" +
                "\n║         CONCLUSÃO DO TREINO              ║" +
                "\n╠══════════════════════════════════════════╣" +
                "\n║ Homeostase quebrada : " + fmt(isHomeostasisBroken) +
                "\n║ Volume morto        : " + fmt(isJunkVolume) +
                "\n║ Falha técnica       : " + fmt(techniqueFailed) +
                "\n║ Score de adaptação  : " + String.format("%.2f / 10", adaptationScore) +
                "\n║ Fadiga sistêmica    : " + String.format("%.2f / 10", systemicFatigue) +
                "\n║ Carga sugerida      : " + String.format("%.1f kg", suggestedLoadNext) +
                "\n╠══════════════════════════════════════════╣" +
                "\n║ " + recommendation +
                "\n╚══════════════════════════════════════════╝";
    }

    private String fmt(boolean b) {
        return (b ? "SIM ✓" : "NÃO ✗") + "                    ║";
    }
}