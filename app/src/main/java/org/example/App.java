package org.example;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class HuffmanNode {
    char c;
    int freq;
    HuffmanNode left, right;

    HuffmanNode(char c, int freq) {
        this.c = c;
        this.freq = freq;
    }

    HuffmanNode(int freq, HuffmanNode left, HuffmanNode right) {
        this.c = '\0'; // Non-leaf node
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
}

public class App {
    static Map<Character, String> huffmanCodes = new HashMap<>();
    static Map<String, Character> reverseCodes = new HashMap<>();

    public static void main(String[] args) {
        String text = "Hello World";
        System.out.println("Original Text: " + text);

        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        HuffmanNode root = buildTree(freqMap);
        buildCodes(root, "");

        String compressed = compress(text);
        System.out.println("Compressed: " + compressed);

        String decompressed = decompress(compressed);
        System.out.println("Decompressed: " + decompressed);
    }

    private static HuffmanNode buildTree(Map<Character, Integer> freqMap) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.freq));
        for (var entry : freqMap.entrySet()) {
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode merged = new HuffmanNode(left.freq + right.freq, left, right);
            pq.add(merged);
        }

        return pq.poll();
    }

    private static void buildCodes(HuffmanNode node, String code) {
        if (node == null)
            return;
        if (node.c != '\0') {
            huffmanCodes.put(node.c, code);
            reverseCodes.put(code, node.c);
        }
        buildCodes(node.left, code + "0");
        buildCodes(node.right, code + "1");
    }

    public static String compress(String text) {
        StringBuilder compressed = new StringBuilder();
        for (char c : text.toCharArray()) {
            compressed.append(huffmanCodes.get(c));
        }
        return compressed.toString();
    }

    public static String decompress(String compressed) {
        StringBuilder result = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();

        for (char bit : compressed.toCharArray()) {
            currentCode.append(bit);
            if (reverseCodes.containsKey(currentCode.toString())) {
                result.append(reverseCodes.get(currentCode.toString()));
                currentCode.setLength(0);
            }
        }
        return result.toString();
    }
}
