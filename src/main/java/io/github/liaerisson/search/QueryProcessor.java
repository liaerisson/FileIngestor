package io.github.liaerisson.search;

import io.github.liaerisson.document.Document;
import io.github.liaerisson.indexing.InvertedIndex;
import io.github.liaerisson.indexing.Tokenizer;

import java.util.*;

public class QueryProcessor {
    private final InvertedIndex index;

    public QueryProcessor(InvertedIndex index) {
        this.index = index;
    }

    public List<SearchResult> processQuery(String query) {
        String[] queryTerms = Tokenizer.tokenize(query);

        Set<String> uniqueTerms = new HashSet<>();
        for(String term : queryTerms) {
            uniqueTerms.add(term);
        }

        Map<Integer, Integer> scoreByDocument = new HashMap<>();
        for(String term : queryTerms) {
            processTerm(term, scoreByDocument);
        }

        List<SearchResult> results = new ArrayList<>();
        for(Integer id : scoreByDocument.keySet()) {
            int score = scoreByDocument.get(id);

            Document doc = index.getDocument(id);
            SearchResult result = new SearchResult(doc, score);
            results.add(result);
        }

        sortResults(results);
        return results;
    }


    public void processTerm(String term, Map<Integer, Integer> scoreByDocument) {
        Map<Integer, Integer> frequencies = index.getTermCounts(term);

        for(Map.Entry<Integer, Integer> entry = frequencies.entrySet()) {
            int docID = entry.getKey();
            int frequency = entry.getValue();

            if(!scoreByDocument.containsKey(docID)) {
                scoreByDocument.put(docID, frequency);
            } else {
                scoreByDocument.put(docID, scoreByDocument.get(docID) + frequency);
            }
        }
    }

    private void sortResults(List<SearchResult> results) {
        results.sort(Comparator.comparingInt(SearchResult::getScore).reversed());
    }

}
