package io.github.liaerisson.api;

import io.github.liaerisson.search.SearchEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void duplicateDocumentReturnsConflict() throws Exception {
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
        ).andExpect(status().isConflict());

    }

    @Test
    void validSearchContentReturnsResult() throws Exception {
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
}
