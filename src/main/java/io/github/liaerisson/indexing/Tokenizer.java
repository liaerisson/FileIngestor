package io.github.liaerisson.indexing;

public class Tokenizer {

    public String[] tokenize(String content) {
        if(content == null) {
            return null;
        }
        content = content.toLowerCase().replaceAll("[^a-z0-9\\s]", "");
        String[] words = content.split(" ");

        return words;
    }
    public static void main(String[] args) {

        String practice = "A prActice ...String ##to toKenize!";
        String lowerCase = practice.toLowerCase();
        System.out.println(lowerCase);
        String improvedPractice = lowerCase.replaceAll("[^a-z0-9\\s]", "");
        System.out.println(improvedPractice);

        //requires a delimiter --> where to split the string up (every space)
        String delimiter = " ";
        String[] words = improvedPractice.split(delimiter); //split is a Java library method
        for(String word : words) {
            System.out.println(word);
        }
    }
}