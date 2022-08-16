package persistence;

import model.QuestionAndAnswer;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Code referenced from JsonReaderTest class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {

    // Test referenced from JsonReaderTest class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    protected void checkQuestionAndAnswer(String question, String answer, QuestionAndAnswer qa) {
        assertEquals(question, qa.getQuestion());
        assertEquals(answer, qa.getAnswer());
    }
}
