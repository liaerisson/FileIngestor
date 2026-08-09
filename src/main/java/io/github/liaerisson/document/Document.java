package io.github.liaerisson.document;

import java.nio.file.Path;

public class Document {
    private final int id;
    private final String title;
    private final Path filePath;
    private final String content;

    public Document(int id, String title, Path filePath, String content) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.content = content;
    }

    public Document(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.filePath = null;
        this.content = content;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public Path getFilePath() {
        return this.filePath;
    }

    public String getContent() {
        return this.content;
    }
}
