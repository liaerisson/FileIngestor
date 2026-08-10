package io.github.liaerisson.api;

import io.github.liaerisson.api.dto.DocumentRequest;
import io.github.liaerisson.document.Document;
import io.github.liaerisson.search.SearchEngine;
import io.github.liaerisson.search.SearchResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
public class SearchController {

    private final SearchEngine searchEngine;

    public SearchController(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    @GetMapping("/search")
    public List<SearchResult> search(@RequestParam String query) {
        return searchEngine.search(query);
    }

    @PostMapping("/documents")
    @ResponseStatus(HttpStatus.CREATED)
    public void addDocument(@Valid @RequestBody DocumentRequest request) {
        Document newDoc = new Document(request.getId(), request.getTitle(), request.getContent());
        searchEngine.addDocument(newDoc);
    }
    
}
