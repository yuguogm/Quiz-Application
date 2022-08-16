package ui;

import model.MyQuiz;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Code referenced from TellerApp.
// Code referenced from JsonSerializationDemo

//A quiz application
public class QuizApp {
    private static final String JSON_STORE = "./data/MyQuiz.json";
    private MyQuiz quiz;
    private String questionInput;
    private String answerInput;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // Method referenced from WorkRoomApp class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: Runs the quiz application
    public QuizApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runQuiz();
    }

    // EFFECTS: Processes input of user
    // MODIFIES: This
    // Referenced from Teller app
    private void runQuiz() {
        boolean continueRunning = true;
        String nextOperation = null;
        init();

        while (continueRunning) {
            showMenu();
            nextOperation = input.next();
            nextOperation = nextOperation.toLowerCase();

            if (nextOperation.equals("q")) {
                continueRunning = false;
            } else {
                processInput(nextOperation);
            }

        }
    }

    // EFFECTS: Initializes the quiz
    // MODIFIES: this
    // Code referenced from TellerApp
    public void init() {
        quiz = new MyQuiz("MyQuiz");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


    // Code referenced from TellerApp
    public void showMenu() {
        System.out.println("\n Options");
        System.out.println("\t Press e to Start Review");
        System.out.println("\t Press a to Add Question");
        System.out.println("\t Press s to save quiz to file");
        System.out.println("\t Press l to load quiz from file");
        System.out.println("\t Press q to quit");
    }


    // MODIFIES: this
    // EFFECTS: processes user command and takes care of invalid inputs. If "e" is pressed, the quiz begins.
    // if "a" is pressed, it allows the user to add a question and answer to the quiz. If "q" is pressed, the
    // app closes.
    // Code referenced from TellerApp
    public void processInput(String userInput) {
        if (userInput.equals("e")) {
            if (quiz.checkNotEmpty()) {
                System.out.println("Beginning Quiz");
                beginQuiz();
            } else {
                System.out.println("You have no questions to review");
            }
        } else if (userInput.equals("a")) {
            addQuestion();
        } else if (userInput.equals("l")) {
            loadMyQuiz();
        } else if (userInput.equals("s")) {
            saveMyQuiz();
        } else {
            System.out.println("Invalid Input");
        }
    }

    //MODIFIES: this
    //EFFECTS: Adds a question to the quiz
    public void addQuestion() {
        System.out.println("Input Question:");
        questionInput = input.next();
        System.out.println("Input Answer:");
        answerInput = input.next();
        quiz.addQuestion(questionInput, answerInput);
    }

    //MODIFIES: this
    //EFFECTS: Starts running the quiz. When the quiz stops running, it returns you to the menu.
    public void beginQuiz() {
        boolean quizRunning = true;
        String nextOperation = null;
        quiz.beginDisplayQuestion();
        while (quizRunning) {
            System.out.println(quiz.retrieveQuestion());
            System.out.println("Input your answer:");
            nextOperation = input.next();
            if (quiz.answerQuestion(nextOperation)) {
                System.out.println("Correct!");
            } else {
                System.out.println("Sorry that is incorrect." + "\n The answer is: " + quiz.retrieveAnswer());
            }
            navigateQuestions();
            quizRunning = checkComplete();
        }
    }

    //MODIFIES: this
    //EFFECTS: Checks if there is no next question. If there isn't it resets the quiz by
    // making the question position of the quiz zero.
    public boolean checkComplete() {
        if (quiz.checkFinished()) {
            System.out.println("You have finished your review");
            quiz.beginDisplayQuestion();
            return false;
        } else {
            return true;
        }
    }

    //MODIFIES: this
    //EFFECTS: Moves to next quiz question if "n" is pressed and moves to the previous question if "b" is pressed.
    public void navigateQuestions() {
        String nextOperation = null;
        System.out.println("n -> Next Question");
        System.out.println("b -> Previous Question");
        nextOperation = input.next();
        if (nextOperation.equals("n") || nextOperation.equals("b")) {
            quiz.navigateQuestions(nextOperation);
        } else {
            System.out.println("Invalid Input");
            navigateQuestions();
        }
    }


    // Method referenced from WorkRoomApp class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: saves the quiz to file
    private void saveMyQuiz() {
        try {
            jsonWriter.open();
            jsonWriter.write(quiz);
            jsonWriter.close();
            System.out.println("Saved " + quiz.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // Method referenced from WorkRoomApp class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads the quiz from file
    private void loadMyQuiz() {
        try {
            quiz = jsonReader.read();
            System.out.println("Loaded " + quiz.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}