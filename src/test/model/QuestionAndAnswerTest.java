package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionAndAnswerTest {
    private QuestionAndAnswer testQuestionAndAnswer;

    @BeforeEach
    void runBefore() {
        testQuestionAndAnswer = new QuestionAndAnswer("a", "b");
    }

    @Test
    void testConstructor() {
        assertEquals("a",testQuestionAndAnswer.getQuestion());
        assertEquals("b",testQuestionAndAnswer.getAnswer());
    }
}
