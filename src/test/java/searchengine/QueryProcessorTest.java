package searchengine;

import io.github.liaerisson.document.Document;
import io.github.liaerisson.indexing.InvertedIndex;
import io.github.liaerisson.search.QueryProcessor;
import io.github.liaerisson.search.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryProcessorTest {
    InvertedIndex index;

    @BeforeEach
    void setup() {
        index = new InvertedIndex();
    }

    @Test
    void returnsCorrectDocAndScoreSingleTermQuery() {
        Document document = new Document(1, "Java notes", Path.of("Java-note.txt"), "you need to know java to make a java based search");

        index.addDocument(document);
        QueryProcessor processor = new QueryProcessor(index);

        List<SearchResult> results = processor.processQuery("java");
        assertEquals(1, results.size());

        SearchResult result = results.get(0);
        assertEquals(document, result.getDocument());
        assertEquals(2, result.getScore());
    }

    @Test
    void returnsCorrectDocAndScoreMultiTermQuery() {
        Document document = new Document(1, "Java notes", Path.of("Java-note.txt"), "you need to know java to make a java based search");

        index.addDocument(document);
        QueryProcessor processor = new QueryProcessor(index);

        List<SearchResult> results = processor.processQuery("java to");
        List<SearchResult> javaResults = processor.processQuery("java");
        List<SearchResult> toResults = processor.processQuery("to");

        assertEquals(1, results.size());

        SearchResult result = results.get(0);
        SearchResult javaResult = javaResults.get(0);
        SearchResult toResult = toResults.get(0);

        assertEquals(document, result.getDocument());
        assertEquals(4, result.getScore());

        assertEquals(4, javaResult.getScore() + toResult.getScore());

    }

    @Test
    void returnsCorrectScoreForMultipleDocuments() {
        Document docOne = new Document(2, "File Two", Path.of("filetwo.txt"), "search search search search");
        Document docTwo = new Document(1, "File One", Path.of("fileone.txt"), "java java search");
        Document docThree = new Document(3, "File Three", Path.of("filethree.txt"), "java engine");

        index.addDocument(docOne);
        index.addDocument(docTwo);
        index.addDocument(docThree);
        QueryProcessor processor = new QueryProcessor(index);

        List<SearchResult> results = processor.processQuery("java search");
        SearchResult first = results.get(0);
        SearchResult second = results.get(1);
        SearchResult third = results.get(2);

        assertEquals(4, first.getScore());
        assertEquals(3, second.getScore());
        assertEquals(1, third.getScore());
    }

    @Test
    void returnsInDescendingOrder() {
        Document docOne = new Document(2, "File Two", Path.of("filetwo.txt"), "search search search search");
        Document docTwo = new Document(1, "File One", Path.of("fileone.txt"), "java java search");
        Document docThree = new Document(3, "File Three", Path.of("filethree.txt"), "java engine");

        index.addDocument(docThree);
        index.addDocument(docOne);
        index.addDocument(docTwo);
        QueryProcessor processor = new QueryProcessor(index);

        List<SearchResult> results = processor.processQuery("java search");
        SearchResult first = results.get(0);
        SearchResult second = results.get(1);
        SearchResult third = results.get(2);

        assertEquals(4, first.getScore());
        assertEquals(3, second.getScore());
        assertEquals(1, third.getScore());
    }

    @Test
    void returnsNoResultWhenNoTermMatches() {
        Document document = new Document(1, "Java notes", Path.of("Java-note.txt"), "you need to know java to make a java based search");

        index.addDocument(document);
        QueryProcessor processor = new QueryProcessor(index);

        List<SearchResult> results = processor.processQuery("python");
        assert(results.isEmpty());
    }

    @Test
    void returnsMatchesWithPartiallyMatchingQuery() {
        Document document = new Document(1, "Java notes", Path.of("Java-note.txt"), "you need to know java to make a java based search");

        index.addDocument(document);
        QueryProcessor processor = new QueryProcessor(index);

        List<SearchResult> results = processor.processQuery("java python");
        SearchResult result = results.get(0);

        assertEquals(2, result.getScore());
    }

    @Test
    void handlesQueryNormalizing() {
        Document document = new Document(1, "Java notes", Path.of("Java-note.txt"), "you need to know java to make a java based search");

        index.addDocument(document);
        QueryProcessor processor = new QueryProcessor(index);

        List<SearchResult> results = processor.processQuery("JAVA!!\n");
        SearchResult result = results.get(0);

        assertEquals(2, result.getScore());
    }

    @Test
    void handlesEmptyQuery() {
        Document document = new Document(1, "Java notes", Path.of("Java-note.txt"), "you need to know java to make a java based search");

        index.addDocument(document);
        QueryProcessor processor = new QueryProcessor(index);

        List<SearchResult> results = processor.processQuery("");
        assert(results.isEmpty());
    }

    @Test
    void handlesWhiteSpaceQuery() {
        Document document = new Document(1, "Java notes", Path.of("Java-note.txt"), "you need to know java to make a java based search");

        index.addDocument(document);
        QueryProcessor processor = new QueryProcessor(index);

        List<SearchResult> results = processor.processQuery("   ");
        assert(results.isEmpty());
    }

    @Test
    void handlesRepeatTermsInQuery() {
        Document document = new Document(1, "Java notes", Path.of("Java-note.txt"), "you need to know java to make a java java based search");

        index.addDocument(document);
        QueryProcessor processor = new QueryProcessor(index);

        List<SearchResult> results = processor.processQuery("java java");
        SearchResult result = results.get(0);

        assertEquals(3, result.getScore());
    }
}
