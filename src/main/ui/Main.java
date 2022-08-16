package ui;

import java.io.FileNotFoundException;

// Code referenced from JsonSerializationDemo

//Begins the Quiz Application
public class Main {
    public static void main(String[] args) {
        try {
            new QuizApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
