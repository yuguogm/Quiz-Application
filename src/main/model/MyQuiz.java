package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a quiz containing questions and answers.
public class MyQuiz implements Writable {
    private String name;
    private ArrayList<QuestionAndAnswer> allQuizQuestions;
    private int onWhichQuestion;

    // EFFECTS: Creates a quiz with no questions in it.
    public MyQuiz(String name) {
        this.name = name;
        allQuizQuestions = new ArrayList<>();
    }

    // Code referenced from AlarmSystem
    // REQUIRES: Input must be non-empty strings.
    // MODIFIES: this
    // EFFECTS: Adds a question to the quiz and logs the event
    public void addQuestion(String question, String answer) {
        QuestionAndAnswer newQuestion = new QuestionAndAnswer(question, answer);
        allQuizQuestions.add(newQuestion);
        EventLog.getInstance().logEvent(new Event("Added question and answers."));
    }

    // MODIFIES: this
    // EFFECTS: Sets the index used for retrieving questions from QuestionAndAnswer to 0
    public void beginDisplayQuestion() {
        onWhichQuestion = 0;
    }

    //EFFECTS: Retrieves the question that needs to be displayed
    public String retrieveQuestion() {
        return allQuizQuestions.get(onWhichQuestion).getQuestion();
    }

    //REQUIRES: Must be a non-empty string.
    //EFFECTS: Determines if the answer given is equal (non-case sensitive) to the answer of the question.
    public boolean answerQuestion(String userAnswer) {
        return allQuizQuestions.get(onWhichQuestion).getAnswer().equalsIgnoreCase(userAnswer);
    }

    //EFFECTS: Returns true if the quiz has one or more elements in it.
    public boolean checkNotEmpty() {
        return (allQuizQuestions.size() > 0);
    }

    //EFFECTS: Retrieves the question that needs to be displayed
    public String retrieveAnswer() {
        return allQuizQuestions.get(onWhichQuestion).getAnswer();
    }

    //EFFECTS: Returns true if the last question has been reached.
    public Boolean checkFinished() {
        return onWhichQuestion == allQuizQuestions.size();
    }

    //MODIFIES: this
    //EFFECTS: Adds or subtracts (if onWhichQuestion is greater than 0) 1 to number of onWhichQuestion to move
    // to the next question or back to the previous one.
    public void navigateQuestions(String s) {
        if (s.equals("n")) {
            onWhichQuestion += 1;
        } else if (onWhichQuestion == 0) {
            onWhichQuestion = 0;
        } else {
            onWhichQuestion -= 1;
        }
    }

    public int getQuestionPosition() {
        return onWhichQuestion;
    }

    public ArrayList<QuestionAndAnswer> getAllQuizQuestions() {
        return allQuizQuestions;
    }

    public String getName() {
        return name;
    }

    @Override
    // Method referenced from WorkRoom class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: turns the quiz into a Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("QuestionAndAnswers", questionAndAnswerToJson());
        return json;
    }

    //EFFECTS: returns a string of the question and answer.
    public ArrayList<String> returnQuestionString() {
        ArrayList<String> questionStrings = new ArrayList<>();
        int i = 1;
        for (QuestionAndAnswer q : allQuizQuestions) {
            String combined = "Question " + i + "   : " + q.getQuestion() + "       " + "Answer: " + q.getAnswer();
            questionStrings.add(combined);
            i++;
        }
        return questionStrings;
    }

    // Code referenced from AlarmSystem
    // MODIFIES: this
    // EFFECTS: Removes all question and answers in the quiz and logs the event
    public void clearAllQuestions() {
        allQuizQuestions.clear();
        EventLog.getInstance().logEvent(new Event("Cleared questions and answers."));
    }

    // EFFECTS: returns the number of questions in the quiz
    public int numQuestions() {
        return allQuizQuestions.size();
    }

    // Method referenced from WorkRoom class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: Turns the question and answers into a Json object
    private JSONArray questionAndAnswerToJson() {
        JSONArray jsonArray = new JSONArray();

        for (QuestionAndAnswer questionAndAnswer : allQuizQuestions) {
            jsonArray.put(questionAndAnswer.toJson());
        }

        return jsonArray;
    }

}
