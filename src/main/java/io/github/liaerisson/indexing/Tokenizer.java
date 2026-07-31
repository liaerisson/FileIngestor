package io.github.liaerisson.indexing;

public class Tokenizer {

    public String[] tokenizeWords(String content) {
        if(content == null) {
            return null;
        }

        content = content
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .trim(); //for the spaces-first edge case

        if(content.isEmpty()) {
            return new String[0];
        }

        String[] words = content.split("\\s+");
        return words;
    }
}