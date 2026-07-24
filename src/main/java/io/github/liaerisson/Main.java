package io.github.liaerisson;


import io.github.liaerisson.document.Document;
import io.github.liaerisson.document.DocumentLoader;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }


        String practice = "A practice string to tokenize!";
        //requires a delimiter --> where to split the string up (every space)
        String delimiter = " ";

        String[] words = practice.split(delimiter); //split is a Java library method
        System.out.println(practice);

        for(int i = 0; i < words.length; i++) {
            System.out.println(words[i]);
        }

        DocumentLoader loader = new DocumentLoader();
        ArrayList<Document> documents = loader.fileLoader();
        for(Document doc: documents) {
            System.out.println(doc.getTitle());
            System.out.println(doc.getContent());
        }
    }
}