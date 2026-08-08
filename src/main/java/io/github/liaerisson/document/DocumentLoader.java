package io.github.liaerisson.document;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class DocumentLoader {
    public List<Document> loadDocuments(Path directory) {
        ArrayList<Path> files = getFiles(directory);
        ArrayList<Document> documents = new ArrayList<>();

        for(int i = 0; i < files.size(); i++) {
            Path file = files.get(i);
            documents.add(makeDocument(file, i + 1));
        }

        return documents;
    }

    private ArrayList<Path> getFiles(Path directory) {
        ArrayList<Path> files = new ArrayList<>();

        try(Stream<Path> paths = Files.walk(directory)) { //returns a file stream
            paths
                    .filter(Files::isRegularFile) //is a file, not a folder or other non-file structure
                    .filter(file -> file.toString().endsWith(".txt")) //filter to ensure each file ends with .txt
                    .sorted()
                    .forEach(files::add);

        } catch (IOException e) {
            throw new RuntimeException("Count not read directory: " + directory, e);
        }

        return files;
    }

    private Document makeDocument(Path filePath, int id) {
        String fileName = filePath.getFileName().toString(); //Get name
        StringBuilder content = new StringBuilder();

        try(BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;

            while ((line = reader.readLine()) != null) { // This check exists to ensure the reader stops at the end of the file
                content.append(line);
                content.append(System.lineSeparator()); //line split
            }

        } catch (IOException e) {
            throw new RuntimeException("Count not read file: " + filePath, e);
        }

        return new Document(id, fileName, filePath, content.toString());
    }

}
