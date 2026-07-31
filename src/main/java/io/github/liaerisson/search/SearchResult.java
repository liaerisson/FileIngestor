package io.github.liaerisson.search;

import io.github.liaerisson.document.Document;

public class SearchResult {
    private final Document document;
    private final int score;

    public SearchResult(Document document, int score) {
        this.document = document;
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
