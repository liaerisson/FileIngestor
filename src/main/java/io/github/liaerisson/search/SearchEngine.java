package io.github.liaerisson.search;

import io.github.liaerisson.document.Document;
import io.github.liaerisson.indexing.InvertedIndex;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchEngine {
    private final InvertedIndex index;
    private final QueryProcessor queryProcessor;

    public SearchEngine() {
        index = new InvertedIndex();
        queryProcessor = new QueryProcessor(index);
    }

    public void addDocument(Document document) {
        index.addDocument(document);
    }

    public List<SearchResult> search(String query) {
        return queryProcessor.processQuery(query);
    }

    public void addDocuments(List<Document> documents) {
        for(Document document:documents) {
            index.addDocument(document);
        }
    }
}
