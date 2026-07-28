package io.github.liaerisson.indexing;

import io.github.liaerisson.document.Document;
import java.util.ArrayList;
import java.util.HashMap;

public class InvertedIndex {
    private HashMap<String, ArrayList<Integer>> terms;
    private HashMap<Integer, Document> documentsById;

    public InvertedIndex() {
        terms = new HashMap<>(); //word, which document IDs have this word
        documentsById = new HashMap<>(); //ID for each document
    }

    public void setDocumentsById(ArrayList<Document> documents) {
        for(Document document: documents) {
            documentsById.put(document.getId(), document);
        }
    }

    public void setTerms(ArrayList<Document> documents) {
        for(Document document : documents) {
            //need to refer to one class' method in another class
        }
    }
}
