package model;


import org.json.JSONObject;
import persistence.Writable;

//Represents a question and answer in a quiz
public class QuestionAndAnswer implements Writable {
    private String question;
    private String answer;

    //REQUIRES: The question and answer must be non-empty strings.
    //EFFECTS: Creates a question and answer pair.
    public QuestionAndAnswer(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }


    @Override
    // Method referenced from Thingy class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: turns the question and answer into a json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("question", question);
        json.put("answer", answer);
        return json;
    }

}
