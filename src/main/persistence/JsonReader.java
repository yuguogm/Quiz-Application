package persistence;

import model.MyQuiz;
import model.QuestionAndAnswer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Code referenced from JsonSerializationDemo
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // Method referenced from JsonReader class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // Method referenced from JsonReader class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads quiz from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MyQuiz read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMyQuiz(jsonObject);
    }

    // Method referenced from JsonReader class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // Method referenced from JsonReader class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: parses the quiz from JSON object and returns it
    private MyQuiz parseMyQuiz(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        MyQuiz quiz = new MyQuiz(name);
        addQuestionAndAnswers(quiz, jsonObject);
        return quiz;
    }

    // Method referenced from JsonReader class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: quiz
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addQuestionAndAnswers(MyQuiz quiz, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("QuestionAndAnswers");
        for (Object json : jsonArray) {
            JSONObject nextQuestionAndAnswer = (JSONObject) json;
            addQuestionAndAnswer(quiz, nextQuestionAndAnswer);
        }
    }

    // Method referenced from JsonReader class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: quiz
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addQuestionAndAnswer(MyQuiz quiz, JSONObject jsonObject) {
        String question = jsonObject.getString("question");
        String answer = jsonObject.getString("answer");
        quiz.addQuestion(question, answer);

    }
}
