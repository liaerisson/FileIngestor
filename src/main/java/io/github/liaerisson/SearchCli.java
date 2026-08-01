package io.github.liaerisson;

import io.github.liaerisson.document.Document;
import io.github.liaerisson.document.DocumentLoader;
import io.github.liaerisson.search.SearchEngine;
import io.github.liaerisson.search.SearchResult;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class SearchCli {
    private final Scanner scanner;
    private final DocumentLoader documentLoader;
    private final SearchEngine searchEngine;

    public SearchCli() {
        scanner = new Scanner(System.in);
        documentLoader = new DocumentLoader();
        searchEngine = new SearchEngine();
    }


    public static void main(String[] args) {
        SearchCli cli = new SearchCli();
        cli.run();
    }

    public void run() {
        printWelcomeMessage();

        Path directory = promptForDirectory();
        List<Document> documents = documentLoader.loadDocuments(directory);

        searchEngine.addDocuments(documents);

        System.out.println(documents.size() + " documents indexed.");

        runSearchLoop();

        scanner.close();
        System.out.println("Thanks for stopping by!");
    }

    private void runSearchLoop() {
        while(true) {
            System.out.print("\nSearch: ");
            String query = scanner.nextLine();

            if(query.equalsIgnoreCase("exit")) {
                break;
            }

            List<SearchResult> results = searchEngine.search(query);
            printResults(results);
        }
    }

    private void printResults(List<SearchResult> results) {
        if(results.isEmpty()) {
            System.out.println("No matching documents found.");
            return;
        }

        System.out.println(results.size() + " matching document(s)");

        for(int i = 0; i < results.size(); i++) {
            SearchResult result = results.get(i);
            Document document = result.getDocument();

            System.out.println();
            System.out.println((i + 1) + ". " + document.getTitle());
            System.out.println("   Path: " + document.getFilePath());
            System.out.println("   Score: " + result.getScore());
        }
    }

    private Path promptForDirectory() {
        while(true) {
            System.out.print("Enter a directory path: ");
            String input = scanner.nextLine();

            try {
                Path directory = Path.of(input);

                if(!Files.exists(directory)) {
                    System.out.println("That path does not exist.");
                } else if(!Files.isDirectory(directory)) {
                    System.out.println("That path is not a directory.");
                } else {
                    return directory;
                }
            } catch (InvalidPathException e) {
                System.out.println("That is not a valid path.");
            }
        }
    }

    private void printWelcomeMessage() {
        System.out.println("==========================");
        System.out.println("  Document Search Engine  ");
        System.out.println("==========================");
        System.out.println("Enter a folder containing .txt files.");
        System.out.println("Type \"exit\" to stop searching.");
        System.out.println();
    }
}
