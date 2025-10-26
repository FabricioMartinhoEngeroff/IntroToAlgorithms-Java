package huffman;

import java.util.*;

/**
 * Implements Huffman Coding algorithm (Greedy strategy)
 */
public class HuffmanCoding {

    // Build the Huffman Tree
    public static HuffmanNode build(Map<Character, Integer> freq) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.freq));

        for (var e : freq.entrySet()) {
            pq.add(new HuffmanNode(e.getKey(), e.getValue()));
        }

        if (pq.size() == 1) {
            HuffmanNode only = pq.poll();
            pq.add(new HuffmanNode(only.freq, only, null));
        }

        while (pq.size() > 1) {
            HuffmanNode x = pq.poll();
            HuffmanNode y = pq.poll();
            HuffmanNode z = new HuffmanNode(x.freq + y.freq, x, y);
            pq.add(z);
        }

        return pq.poll();
    }

    // Generate Huffman Codes
    public static Map<Character, String> codes(HuffmanNode root) {
        Map<Character, String> map = new HashMap<>();
        buildCodes(root, "", map);
        return map;
    }

    private static void buildCodes(HuffmanNode node, String path, Map<Character, String> map) {
        if (node == null) return;

        if (node.left == null && node.right == null) {
            map.put(node.ch, path.length() > 0 ? path : "0");
            return;
        }

        buildCodes(node.left, path + "0", map);
        buildCodes(node.right, path + "1", map);
    }

    // Encode a message
    public static String encode(String text, Map<Character, String> code) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(code.get(c));
        }
        return sb.toString();
    }

    // Decode binary back into text
    public static String decode(String bits, HuffmanNode root) {
        StringBuilder out = new StringBuilder();
        HuffmanNode cur = root;

        for (int i = 0; i < bits.length(); i++) {
            cur = (bits.charAt(i) == '0') ? cur.left : cur.right;

            if (cur.left == null && cur.right == null) {
                out.append(cur.ch);
                cur = root;
            }
        }

        return out.toString();
    }

    // Calculate total cost in bits
    public static int costBits(HuffmanNode root) {
        return costBits(root, 0);
    }

    private static int costBits(HuffmanNode node, int depth) {
        if (node == null) return 0;
        if (node.left == null && node.right == null) {
            return node.freq * depth;
        }
        return costBits(node.left, depth + 1) + costBits(node.right, depth + 1);
    }
}