package searchengine;

import io.github.liaerisson.document.Document;
import io.github.liaerisson.search.SearchEngine;
import io.github.liaerisson.search.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class SearchEngineTest {
    SearchEngine engine;

    @BeforeEach
    void setUp() {
        engine = new SearchEngine();
    }

    @Test
    void getsCorrectResultsAndOrder() {
        Document docOne = new Document(1, "File One", Path.of("file-one.txt"), "Contents of file one are of little importance");
        Document docTwo = new Document(2, "File Two", Path.of("file-two.txt"), "Of course file two contains no mention of java...oops!");
        Document docThree = new Document(3, "File Three", Path.of("file-three.txt"), "File three is simply quite plain java.");
        List<Document> documents = List.of(docOne, docTwo, docThree);

        engine.addDocuments(documents);
        List<SearchResult> results = engine.search("Of java");
        assertEquals(3, results.size());

        SearchResult first = results.get(0);
        SearchResult second = results.get(1);
        SearchResult third = results.get(2);


        assertEquals(3, first.getScore());
        assertSame(docTwo, first.getDocument());

        assertSame(docOne, second.getDocument());
        assertEquals(2, second.getScore());

        assertSame(docThree, third.getDocument());
        assertEquals(1, third.getScore());
    }
}
