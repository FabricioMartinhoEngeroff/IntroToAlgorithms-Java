package huffman;

import java.util.LinkedHashMap;
import java.util.Map;


public class Main {

    public static void main(String[] args) {

        // Example frequencies (from textbook)
        Map<Character, Integer> freq = new LinkedHashMap<>();
        freq.put('a', 45000);
        freq.put('b', 13000);
        freq.put('c', 12000);
        freq.put('d', 16000);
        freq.put('e',  9000);
        freq.put('f',  5000);

        // Build Huffman Tree and generate codes
        HuffmanNode root = HuffmanCoding.build(freq);
        Map<Character, String> code = HuffmanCoding.codes(root);

        System.out.println("Huffman Codes:");
        code.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));

        // Theoretical total cost (bits)
        int cost = HuffmanCoding.costBits(root);
        System.out.println("\nTotal cost (bits): " + cost);

        // Compare with fixed-length code (3 bits per symbol)
        int fixed = 100_000 * 3;
        System.out.println("Fixed-length cost (3 bits): " + fixed);
        System.out.printf("Compression gain: %.1f%%%n", 100.0 * (fixed - cost) / fixed);

        // Encode and decode test
        String msg = "cafe";
        String bits = HuffmanCoding.encode(msg, code);
        String back = HuffmanCoding.decode(bits, root);

        System.out.println("\nMessage:  " + msg);
        System.out.println("Encoded:  " + bits);
        System.out.println("Decoded:  " + back);
    }
}
