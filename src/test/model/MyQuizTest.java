package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyQuizTest {
    private MyQuiz testQuiz;

    @BeforeEach
    void runBefore() {
        testQuiz = new MyQuiz("MyQuiz");
    }

    @Test
    void testGetQuizName() {
        assertEquals("MyQuiz", testQuiz.getName());
    }

    @Test
    void testConstructor() {
        assertEquals(0, testQuiz.getAllQuizQuestions().size());
    }

    @Test
    void beginDisplayQuestionTest() {
        testQuiz.beginDisplayQuestion();
        assertEquals(0, testQuiz.getQuestionPosition());
    }

    @Test
    void beginDisplayQuestionTestAfterReset() {
        testQuiz.addQuestion("1", "1");
        testQuiz.addQuestion("2", "2");
        testQuiz.navigateQuestions("n");
        testQuiz.beginDisplayQuestion();
        assertEquals(0, testQuiz.getQuestionPosition());
        testQuiz.navigateQuestions("n");
        testQuiz.navigateQuestions("n");
        testQuiz.beginDisplayQuestion();
        assertEquals(0, testQuiz.getQuestionPosition());

    }

    @Test
    void addQuestionTest() {
        testQuiz.addQuestion("NootNoot?", "NootNoot");
        assertEquals(1, testQuiz.getAllQuizQuestions().size());
    }

    @Test
    void addTwoQuestionsTest() {
        testQuiz.addQuestion("NootNoot?", "NootNoot");
        testQuiz.addQuestion("dfsfsfsd", "sfsdf");
        assertEquals(2, testQuiz.getAllQuizQuestions().size());
    }

    @Test
    void navigateQuestionsTest() {
        testQuiz.addQuestion("NootNoot?", "NootNoot");
        testQuiz.addQuestion("dfsfsfsd", "sfsdf");
        assertEquals("NootNoot?", testQuiz.getAllQuizQuestions().get(0).getQuestion());
        testQuiz.navigateQuestions("n");
        assertEquals("dfsfsfsd", testQuiz.getAllQuizQuestions().get(1).getQuestion());
        testQuiz.navigateQuestions("b");
        assertEquals("NootNoot?", testQuiz.getAllQuizQuestions().get(0).getQuestion());
    }

    @Test
    void navigateBackOnQuestionOne() {
        testQuiz.addQuestion("NootNoot?", "NootNoot");
        testQuiz.addQuestion("dfsfsfsd", "sfsdf");
        assertEquals("NootNoot?", testQuiz.getAllQuizQuestions().get(0).getQuestion());
        testQuiz.navigateQuestions("n");
        assertEquals("dfsfsfsd", testQuiz.getAllQuizQuestions().get(1).getQuestion());
        testQuiz.navigateQuestions("b");
        testQuiz.navigateQuestions("b");
        assertEquals("NootNoot?", testQuiz.getAllQuizQuestions().get(0).getQuestion());
    }

    @Test
    void retrieveFirstQuestionTest() {
        testQuiz.addQuestion("Noot Noot?", "Noot Noot");
        assertEquals("Noot Noot?", testQuiz.retrieveQuestion());
    }

    @Test
    void retrieveNextQuestionsTest() {
        testQuiz.addQuestion("Noot Noot?", "Noot Noot");
        testQuiz.addQuestion("1", "2");
        testQuiz.navigateQuestions("n");
        assertEquals("1", testQuiz.retrieveQuestion());
    }

    @Test
    void retrieveFirstAnswerTest() {
        testQuiz.addQuestion("Noot Noot?", "Noot Noot");
        assertEquals("Noot Noot", testQuiz.retrieveAnswer());
    }


    @Test
    void retrieveNextAnswersTest() {
        testQuiz.addQuestion("Noot Noot?", "Noot Noot");
        testQuiz.addQuestion("1", "2");
        testQuiz.navigateQuestions("n");
        assertEquals("2", testQuiz.retrieveAnswer());
    }

    @Test
    void checkEmptyTest() {
        assertFalse(testQuiz.checkNotEmpty());
    }

    @Test
    void checkNotEmptyTest() {
        assertFalse(testQuiz.checkNotEmpty());
        testQuiz.addQuestion("1", "2");
        assertTrue(testQuiz.checkNotEmpty());
    }


    @Test
    void quizFinishedNoTest() {
        assertTrue(testQuiz.checkFinished());
    }

    @Test
    void quizFinishedWithOneQuestionTest() {
        testQuiz.addQuestion("1", "1");
        assertFalse(testQuiz.checkFinished());
        testQuiz.navigateQuestions("n");
        assertTrue(testQuiz.checkFinished());
    }

    @Test
    void answerQuestionTest() {
        testQuiz.addQuestion("1", "1");
        assertTrue(testQuiz.answerQuestion("1"));
    }

    @Test
    void answerNextAndPreviousQuestionTest() {
        testQuiz.addQuestion("1", "1");
        testQuiz.addQuestion("2", "2");
        assertTrue(testQuiz.answerQuestion("1"));
        testQuiz.navigateQuestions("n");
        assertTrue(testQuiz.answerQuestion("2"));
        testQuiz.navigateQuestions("b");
        assertTrue(testQuiz.answerQuestion("1"));
    }

    @Test
    void returnQuestionAndAnswerStringTest() {
        testQuiz.addQuestion("1", "1");
        assertEquals("Question 1   : 1       Answer: 1", testQuiz.returnQuestionString().get(0));
        testQuiz.addQuestion("2", "2");
        assertEquals("Question 2   : 2       Answer: 2", testQuiz.returnQuestionString().get(1));
    }

    @Test
    void clearQuestionAndAnswerTest() {
        testQuiz.addQuestion("1", "1");
        testQuiz.clearAllQuestions();
        assertEquals(0, testQuiz.getAllQuizQuestions().size());
        testQuiz.addQuestion("1", "1");
        testQuiz.addQuestion("1", "1");
        testQuiz.clearAllQuestions();
        assertEquals(0, testQuiz.getAllQuizQuestions().size());
    }
}

