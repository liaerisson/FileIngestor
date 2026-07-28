package io.github.liaerisson.indexing;

public class Tokenizer {

    public String[] tokenizeWords(String content) {
        if(content == null) {
            return null;
        }
        content = content.toLowerCase().replaceAll("[^a-z0-9\\s]", "");
        String[] words = content.split(" +");

        return words;
    }
}