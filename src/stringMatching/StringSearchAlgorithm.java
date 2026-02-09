package stringMatching;

public interface StringSearchAlgorithm {
    SearchResult search(String text, String pattern);

    String name();
}
