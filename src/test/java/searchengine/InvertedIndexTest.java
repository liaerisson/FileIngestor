package searchengine;

import static org.junit.jupiter.api.Assertions.*;

import io.github.liaerisson.document.Document;
import org.junit.jupiter.api.Test;
import io.github.liaerisson.indexing.InvertedIndex;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

public class InvertedIndexTest {
    InvertedIndex indexer;
    Document document;

    @BeforeEach
    void setUp() {
        indexer = new InvertedIndex();
        document = new Document(1, "test.txt", Path.of("test.txt"), "Inverted indexing in java test");
    }

    @Test
    void addsDocument() {
        indexer.addDocument(document);

        assertTrue(indexer.containsDocument(1));
        assertSame(document, indexer.getDocument(1));
    }

    @Test
    void indexesTerm() {
        indexer.addDocument(document);
        Map<Integer, Integer> count = indexer.getTermCounts("java");

        assertTrue(count.containsKey(1), "Key missing from map");
        assertEquals(1, count.get(1), "Value does not match");
    }

    @Test
    void countsFewTimes() {
        document = new Document(1, "test.txt", Path.of("test.txt"), "Inverted indexing in java java java test");
        indexer.addDocument(document);
        Map<Integer, Integer> count = indexer.getTermCounts("java");

        assertTrue(count.containsKey(1), "Key missing from map");
        assertEquals(3, count.get(1), "Value does not match");
    }

    @Test
    void indexesFewTerms() {
        indexer.addDocument(document);

        Map<Integer, Integer> count = indexer.getTermCounts("java");
        assertTrue(count.containsKey(1), "java missing from map");
        assertEquals(1, count.get(1), "Value for java is wrong");

        count = indexer.getTermCounts("indexing");
        assertTrue(count.containsKey(1), "indexing missing from map");
        assertEquals(1, count.get(1), "Value for indexing is wrong");

        count = indexer.getTermCounts("test");
        assertTrue(count.containsKey(1), "test missing from map");
        assertEquals(1, count.get(1), "Value for test is wrong");
    }

    @Test
    void indexesMultipleDocuments() {
        Document documentTwo = new Document(2, "testTwo.txt", Path.of("testTwo.txt"), "Inverted indexing in java java java test");
        indexer.addDocument(document);
        indexer.addDocument(documentTwo);
        Map<Integer, Integer> count = indexer.getTermCounts("java");

        assertTrue(count.containsKey(1), "First document missing from map");
        assertEquals(1, count.get(1), "Frequency for document one does not match");

        assertTrue(count.containsKey(2), "Second document from map");
        assertEquals(3, count.get(2), "Frequency for document two does not match");
    }

    @Test
    void handlesMissingTerm() {
        indexer.addDocument(document);
        Map<Integer, Integer> count = indexer.getTermCounts("python");

        assertSame(Collections.emptyMap(), count);
    }

    @Test
    void handlesDuplicateDocuments() {
        indexer.addDocument(document);
        Document documentTwo = new Document(1, "test.txt", Path.of("test.txt"), "Inverted indexing in java test");

        assertThrows(IllegalArgumentException.class, () -> {
                indexer.addDocument(documentTwo);
        });
    }

    @Test
    void handlesNoContent() {
        document = new Document(1, "test.txt", Path.of("test.txt"), "");
        indexer.addDocument(document);

        //need way to get full set of terms
    }
}
