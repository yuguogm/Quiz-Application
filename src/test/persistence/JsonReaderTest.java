package persistence;

import model.MyQuiz;
import model.QuestionAndAnswer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Code referenced from JsonSerializationDemo

public class JsonReaderTest extends JsonTest {

    // Test referenced from JsonReaderTest class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MyQuiz quiz = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    // Test referenced from JsonReaderTest class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testReaderQuiz() {
        JsonReader reader = new JsonReader("./data/TestReaderEmptyQuiz.json");
        try {
            MyQuiz quiz = reader.read();
            assertEquals("MyQuiz", quiz.getName());
            assertEquals(0, quiz.numQuestions());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    // Test referenced from JsonReaderTest class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testReaderGeneralQuiz() {
        JsonReader reader = new JsonReader("./data/TestReaderGeneralQuiz.json");
        try {
            MyQuiz quiz = reader.read();
            assertEquals("MyQuiz", quiz.getName());
            List<QuestionAndAnswer> questionAndAnswers = quiz.getAllQuizQuestions();
            assertEquals(2, questionAndAnswers.size());
            checkQuestionAndAnswer("1", "1", questionAndAnswers.get(0));
            checkQuestionAndAnswer("2", "2", questionAndAnswers.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
