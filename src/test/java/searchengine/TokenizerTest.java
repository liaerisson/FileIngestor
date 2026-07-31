package searchengine;
import static org.junit.jupiter.api.Assertions.*;

import io.github.liaerisson.indexing.Tokenizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TokenizerTest {
    private Tokenizer tokenizer;

    @BeforeEach
    void setUp() {
        tokenizer = new Tokenizer();
    }


    @Test
    void convertsTextToLowerCase() {
        String[] result = tokenizer.tokenize("HELLO WORLD");

        assertArrayEquals(new String[]{"hello", "world"}, result);
    }

    @Test
    void removesAllSpaces() {
        String[] result = tokenizer.tokenize("hello      world    !");

        assertArrayEquals(new String[]{"hello", "world"}, result);
    }

    @Test
    void handlesLineChars() {
        String[] result = tokenizer.tokenize("   hello   world\nbreak\ttest");

        assertArrayEquals(new String[]{"hello", "world", "break", "test"}, result);
    }

    /* Future test, ideally
    @Test
    void splitsDashedWords() {
        String[] result = tokenizer.tokenize("hello-world! hello—world! hello–world!");

        assertArrayEquals(new String[]{"hello", "world"}, result);
    }
     */
}
