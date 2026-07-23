package io.github.liaerisson.document;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class DocumentLoader {

    public ArrayList<Path> getFiles() {
        String path = "/Users/liaer/GitHub/JavaIngestor/src/main/java/io/github/liaerisson/documents";
        ArrayList<Path> fileSet = new ArrayList<>();

        try(Stream<Path> paths = Files.walk(Paths.get(path))) { //returns a file stream
            paths
                    .filter(Files::isRegularFile) //is a file, not a folder or other non-file structure
                    .filter(file -> file.toString().endsWith(".txt")) //filter to ensure each file ends with .txt
                    .forEach(fileSet::add);

        } catch (IOException e) {
            throw new RuntimeException("Count not read documents folder", e);
        }

        return fileSet;
    }

    public ArrayList<Document> fileLoader() {
        ArrayList<Path> files = getFiles();
        ArrayList<Document> documents = new ArrayList<>();

        for(int i = 0; i < files.size(); i++) {
            Path file = files.get(i);
            documents.add(makeDocument(file, i + 1));
        }

        return documents;
    }

    public Document makeDocument(Path filePath, int id) {
        String fileName = filePath.getFileName().toString(); //Get name
        String content = "";

        try(BufferedReader reader = Files.newBufferedReader(filePath)) {
            //System.out.println("This file exists!"); -- Used this to test it works :)
            String line;

            while ((line = reader.readLine()) != null) { // This check exists to ensure the reader stops at the end of the file
                content = content.concat(line);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Count not locate file");
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

        Document doc = new Document(id, fileName, filePath, content);
        return doc;
    }

}
