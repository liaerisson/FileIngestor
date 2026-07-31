package searchengine;
import static org.junit.jupiter.api.Assertions.*;

import io.github.liaerisson.indexing.Tokenizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TokenizerTest {
    @Test
    void convertsTextToLowerCase() {
        String[] result = Tokenizer.tokenize("HELLO WORLD");

        assertArrayEquals(new String[]{"hello", "world"}, result);
    }

    @Test
    void removesAllSpaces() {
        String[] result = Tokenizer.tokenize("hello      world    !");

        assertArrayEquals(new String[]{"hello", "world"}, result);
    }

    @Test
    void handlesLineChars() {
        String[] result = Tokenizer.tokenize("   hello   world\nbreak\ttest");

        assertArrayEquals(new String[]{"hello", "world", "break", "test"}, result);
    }

    @Test
    void handlesConnectedWords() {
        String[] result = Tokenizer.tokenize("hello...world!");

        assertArrayEquals(new String[]{"hello", "world"}, result);
    }

/*
    @Test
    void splitsDashedWords() {
        String[] result = Tokenizer.tokenize("hello-world! hello—world! hello–world!");

        assertArrayEquals(new String[]{"hello", "world"}, result);
    }

 */
}
