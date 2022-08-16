package persistence;

import model.MyQuiz;
import model.QuestionAndAnswer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Code referenced from JsonWriterTest class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest {

    // Test referenced from JsonWriterTest class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testWriterInvalidFile() {
        try {
            MyQuiz quiz = new MyQuiz("Test Quiz");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    // Test referenced from JsonWriterTest class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testWriterEmptyQuiz() {
        try {
            MyQuiz quiz = new MyQuiz("Test Quiz");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyQuiz.json");
            writer.open();
            writer.write(quiz);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyQuiz.json");
            quiz = reader.read();
            assertEquals("Test Quiz", quiz.getName());
            assertEquals(0, quiz.numQuestions());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // Test referenced from JsonWriterTest class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testWriterGeneralQuiz() {
        try {
            MyQuiz quiz = new MyQuiz("Test Quiz");
            quiz.addQuestion("1", "1");
            quiz.addQuestion("2", "2");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralQuiz.json");
            writer.open();
            writer.write(quiz);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralQuiz.json");
            quiz = reader.read();
            assertEquals("Test Quiz", quiz.getName());
            List<QuestionAndAnswer> questionAndAnswers = quiz.getAllQuizQuestions();
            assertEquals(2, questionAndAnswers.size());
            checkQuestionAndAnswer("1", "1", questionAndAnswers.get(0));
            checkQuestionAndAnswer("2","2", questionAndAnswers.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
