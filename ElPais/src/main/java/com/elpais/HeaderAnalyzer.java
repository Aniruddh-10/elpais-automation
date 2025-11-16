package com.elpais;

import java.util.*;

public class HeaderAnalyzer {
    public void analyze(List<String> headers) {
        Map<String, Integer> wordCount = new HashMap<>();

        for (String header : headers) {
            String[] words = header.toLowerCase().replaceAll("[^a-z ]", "").split("\\s+");
            for (String word : words) {
                if (word.length() > 2) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }
        }

        System.out.println("\nRepeated Words in Translated Headers:");
        wordCount.entrySet().stream()
                .filter(e -> e.getValue() > 2)
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
    }
}
