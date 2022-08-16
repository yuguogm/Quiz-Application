package ui;

import model.EventLog;
import model.MyQuiz;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Code referenced from JsonSerializationDemo
//GUI of a Quiz application
public class QuizGui extends JFrame implements ActionListener {
    private static final int WIDTH = 750;
    private static final int HEIGHT = 750;
    private MyQuiz quiz;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/MyQuiz.json";
    private JPanel menuPanel = new JPanel();
    private JPanel questionsPanel = new JPanel();
    private JFrame addFrame = new JFrame();
    private JFrame questionAddedFrame = new JFrame();
    private JButton viewQuestionsButton = new JButton("View Questions");
    private JButton addQuestionButton = new JButton("Add Questions");
    private JButton loadQuestionButton = new JButton("Load Questions");
    private JButton saveQuestionButton = new JButton("Save Questions");
    private JButton backToMenuButton = new JButton("Back to Menu");
    private JButton submitQuestionButton = new JButton("Submit Question and Answer");
    private JButton clearQuestionButton = new JButton("Clear Questions");
    private JTextField questionField = new JTextField();
    private JTextField answerField = new JTextField();

    //Begins the quiz application gui
    public static void main(String[] args) {
        try {
            new QuizGui();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }

    // SOURCE: https://docs.oracle.com/javase/tutorial/uiswing/layout/border.html
    //         https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html
    //         https://stackoverflow.com/questions/60516720/java-how-to-print-message-when-a-jframe-is-closed
    //         Code referenced from AlarmSystem
    // EFFECTS: Runs the quiz GUI
    public QuizGui() throws FileNotFoundException {
        super("Quiz Application");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        quiz = new MyQuiz("My Quiz");
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setSize(HEIGHT,WIDTH);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                for (model.Event event : EventLog.getInstance()) {
                    System.out.println(event.toString());
                }
                System.exit(0);
            }
        });
        this.setVisible(true);
        createMenuPanel();
        createQuestionsPanel();
        createAddQuestionFrame();
        createQuestionAddedFrame();
    }

    //SOURCE: https://docs.oracle.com/javase/tutorial/uiswing/components/panel.html
    // MODIFIES: this
    // EFFECTS: Creates the question panel
    public void createQuestionsPanel() {
        this.add(questionsPanel);
        questionsPanel.setLayout(new FlowLayout());
        questionsPanel.add(backToMenuButton);
        updateQuestionPanel();
        questionsPanel.setVisible(false);
    }


    //referenced: https://www.geeksforgeeks.org/convert-an-arraylist-of-string-to-a-string-array-in-java/
    //MODIFIES: this
    //EFFECTS: removes the components of the current panel and replaces it with an updated one
    private void updateQuestionPanel() {
        questionsPanel.removeAll();
        questionsPanel.add(backToMenuButton);
        ArrayList<String> questionStrings = quiz.returnQuestionString();

        String[] displayStrings = new String[questionStrings.size()];

        for (int i = 0; i < questionStrings.size(); i++) {
            displayStrings[i] = questionStrings.get(i);
        }
        JList display = new JList(displayStrings);
        JScrollPane displayPanel = new JScrollPane(display);
        displayPanel.setPreferredSize(new Dimension(HEIGHT - 50,WIDTH / 2));
        questionsPanel.add(displayPanel);
    }

    //MODIFIES: this
    //EFFECTS: Hides the menu and displays the question panel
    public void viewQuestions() {
        menuPanel.setVisible(false);
        questionsPanel.setVisible(true);
        updateQuestionPanel();
    }

    //MODIFIES: this
    //EFFECTS: Hides the questions panel and displays the menu panel
    public void backToMenu(JPanel panel) {
        panel.setVisible(false);
        menuPanel.setVisible(true);
    }

    //SOURCE: https://docs.oracle.com/javase/tutorial/uiswing/components/panel.html
    //MODIFIES: this
    //EFFECTS: creates the menu panel
    public void createMenuPanel() {
        menuPanel.setVisible(true);
        menuPanel.setSize(HEIGHT, WIDTH);
        this.add(menuPanel);
        menuPanel.setBackground(Color.cyan);
        menuPanel.setLayout(new GridLayout(4, 4));
        JLabel label = new JLabel("Quiz Maker");
        label.setFont(new Font("SansSerif", Font.BOLD, 50));
        menuPanel.add(label);
        addListener();
        addButtons();
    }

    //SOURCE: https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html
    //MODIFIES: this
    //EFFECTS: Creates the frame where questions and answers are inputted
    public void createAddQuestionFrame() {
        addFrame.setResizable(false);
        addFrame.setLayout(new FlowLayout());
        addFrame.setPreferredSize(new Dimension(500,150));
        addFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        addFrame.add(submitQuestionButton);
        addFrame.add(questionField);
        addFrame.add(answerField);
        addFrame.setVisible(false);
    }

    //SOURCE: https://docs.oracle.com/javase/7/docs/api/javax/swing/ImageIcon.html
    //MODIFIES: this
    //EFFECTS: Creates the frame that is displayed when a question and answer has been added
    public void createQuestionAddedFrame() {
        questionAddedFrame.setResizable(false);
        ImageIcon icon = new ImageIcon("src/main/model/Image.png");
        JLabel text = new JLabel("");
        text.setIcon(icon);
        JPanel questionAddedPanel = new JPanel();
        questionAddedPanel.add(text);
        questionAddedFrame.setPreferredSize(new Dimension(380,250));
        questionAddedFrame.add(questionAddedPanel);
        questionAddedFrame.pack();
        questionAddedFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        addFrame.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: adds the buttons on to the menu panel
    public void addButtons() {
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(Color.cyan);
        menuPanel.add(emptyPanel);
        menuPanel.add(viewQuestionsButton);
        menuPanel.add(addQuestionButton);
        menuPanel.add(clearQuestionButton);
        menuPanel.add(saveQuestionButton);
        menuPanel.add(loadQuestionButton);
    }

    // SOURCE: https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html
    // MODIFIES: this
    // EFFECTS: Adds the necessary action listeners to the buttons
    public void addListener() {
        viewQuestionsButton.addActionListener(e -> viewQuestions());
        addQuestionButton.addActionListener(this);
        loadQuestionButton.addActionListener(e -> loadMyQuiz());
        saveQuestionButton.addActionListener(e -> saveMyQuiz());
        backToMenuButton.addActionListener(e -> backToMenu(questionsPanel));
        submitQuestionButton.addActionListener(this);
        clearQuestionButton.addActionListener(e -> clearQuestions());
    }

    // MODIFIES: this
    // EFFECTS: Removes all the questions in the quiz
    public void clearQuestions() {
        quiz.clearAllQuestions();
    }


    //SOURCE: https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html
    //MODIFIES: this
    //EFFECTS: Performs the necessary action depending on what is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addQuestionButton) {
            addQuestion();
        }
        if (e.getSource() == submitQuestionButton) {
            quiz.addQuestion(questionField.getText(), answerField.getText());
            questionAddedFrame.setVisible(true);
        }
    }

    //MODIFIES: this
    //EFFECTS: Displays the frame for adding questions and answers
    public void addQuestion() {
        addFrame.setVisible(true);
        answerField.setText("Answer Here");
        questionField.setText("Question Here");
        submitQuestionButton.setPreferredSize(new Dimension(200,50));
        answerField.setPreferredSize(new Dimension(100,50));
        questionField.setPreferredSize(new Dimension(100,50));
        addFrame.pack();
    }

    // Method referenced from WorkRoomApp class in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: saves the quiz to file
    private void saveMyQuiz() {
        try {
            jsonWriter.open();
            jsonWriter.write(quiz);
            jsonWriter.close();
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
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
