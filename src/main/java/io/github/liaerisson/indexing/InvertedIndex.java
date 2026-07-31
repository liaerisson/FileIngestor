package io.github.liaerisson.indexing;

import io.github.liaerisson.document.Document;
import java.util.ArrayList;
import java.util.HashMap;

public class InvertedIndex {
    private HashMap<String, HashMap<Integer, Integer>> terms;
    private HashMap<Integer, Document> documentsById;

    public InvertedIndex() {
        terms = new HashMap<>(); //word, which document IDs have this word
        documentsById = new HashMap<>(); //ID for each document
    }

    public void addDocument(Document document) {
        int id = document.getId();
        if(documentsById.containsKey(id)) {
            return;
        }
    }

    public ArrayList<Integer> search(String word) {

    }

}
