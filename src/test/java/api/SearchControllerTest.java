package io.github.liaerisson.api;

import io.github.liaerisson.exception.DocumentAleadyExistsException;
import io.github.liaerisson.document.Document;
import io.github.liaerisson.search.SearchEngine;
import io.github.liaerisson.search.SearchResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.doThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(SearchController.class)
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SearchEngine searchEngine;

    @Test
    void addValidDocumentReturnsCreated() throws Exception {
        mockMvc.perform(
                post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""" 
                            {
                                "id": 1,
                                "title": "Java Notes",
                                "content": "Java java indexing search"
                            }
                            """)
                )
                .andExpect(status().isCreated());
    }

    @Test
    void addBlankTitleDocumentReturnsBadRequest() throws Exception {
        mockMvc.perform(
                post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""" 
                            {
                                "id": 1,
                                "title": "",
                                "content": "Java java indexing search"
                            }
                            """)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void addNullTitleDocumentReturnsBadRequest() throws Exception {
        mockMvc.perform(
                post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""" 
                            {
                                "id": 1,
                                "title": ,
                                "content": "Java java indexing search"
                            }
                            """)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void addNullContentDocumentReturnsBadRequest() throws Exception {
        mockMvc.perform(
                post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""" 
                            {
                                "id": 1,
                                "title": "Java Notes",
                                "content":
                            }
                            """)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void correctStatusReturnedForDocumentAlreadyExistsException() throws Exception {
        //Mockito setup
        doThrow(new DocumentAleadyExistsException(1))
                .when(searchEngine)
                .addDocument(any(Document.class));

        mockMvc.perform(
                post("/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""" 
                            {
                                "id": 1,
                                "title": "Java Notes",
                                "content": "Hello there, these are some java notes"
                            }
                            """)).andExpect(status().isConflict());
    }

    @Test
    void validSearchContentReturnsResult() throws Exception {
        Document document = new Document(1, "Java Notes", "java java java");
        SearchResult result = new SearchResult(document, 3);

        //Mockito setup
        when(searchEngine.search("java")).thenReturn(List.of(result));

        mockMvc.perform(
                get("/search?query=java")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].score").value(3))
                .andExpect(jsonPath("$[0].document.title").value("Java Notes"));
    }

    @Test
    void nonMatchingResultReturnsEmptyArray() throws Exception {
        //Mockito setup
        when(searchEngine.search("missing")).thenReturn(List.of());

        mockMvc.perform(
                        get("/search?query=missing")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
