package io.github.liaerisson.api.dto;

public class DocumentRequest {
    private int id;
    private String title;
    private String content;

    public DocumentRequest() {}

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent() {
        this.content = content;
    }
}
