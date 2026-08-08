package io.github.liaerisson.api;

import io.github.liaerisson.search.SearchEngine;
import io.github.liaerisson.search.SearchResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    private final SearchEngine searchEngine;

    public SearchController(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    @GetMapping("/search")
    public List<SearchResult> search(@RequestParam String query) {

    }
    
}
