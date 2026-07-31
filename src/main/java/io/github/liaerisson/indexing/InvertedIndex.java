package io.github.liaerisson.indexing;

import io.github.liaerisson.document.Document;

import java.util.*;

public class InvertedIndex {
    private HashMap<String, HashMap<Integer, Integer>> terms;
    private HashMap<Integer, Document> documentsById;
    private Tokenizer tokenizer;

    public InvertedIndex() {
        terms = new HashMap<>(); //word, which document IDs have this word
        documentsById = new HashMap<>(); //ID for each document
        tokenizer = new Tokenizer();
    }

    public void addDocument(Document document) {
        int id = document.getId();
        if(containsDocument(id)) {
            throw new IllegalArgumentException("Document is already indexed.");
        }

        documentsById.put(id, document);
        String[] words = tokenizer.tokenize(document.getContent());

        for(String word: words) {
            if(!terms.containsKey(word)) {
                HashMap<Integer, Integer> documentCounts = new HashMap<>();
                terms.put(word, documentCounts);
            }

            HashMap<Integer, Integer> documentCounts = terms.get(word);
            if(documentCounts.get(id) == null) {
                documentCounts.put(id, 1);
            } else {
                documentCounts.put(id, documentCounts.get(id) + 1);
            }
            terms.put(word, documentCounts);
        }
    }

    public Map<Integer, Integer> getTermCounts(String term) {
        if(!terms.containsKey(term)) {
            return Collections.emptyMap();
        }

        return terms.get(term);
    }

    public Document getDocument(int docID) {
        return documentsById.get(docID);
    }

    public boolean containsDocument(int documentID) {
        return documentsById.containsKey(documentID);
    }

}
