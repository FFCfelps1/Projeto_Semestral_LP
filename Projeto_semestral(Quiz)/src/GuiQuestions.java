import java.awt.*;
import java.util.List;
import javax.swing.*;

public class GuiQuestions extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel quizPanel;
    private JPanel questionPanel;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    private User user;

    public GuiQuestions(List<Question> questions, User user) {
        this.questions = questions;
        this.user = user;

        setTitle("Quiz App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createQuizPanel();

        mainPanel.add(quizPanel, "quiz");

        add(mainPanel);
        setVisible(true);

        showNextQuestion();
    }

    private void createQuizPanel() {
        quizPanel = new JPanel(new BorderLayout());
        questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        quizPanel.add(questionPanel, BorderLayout.CENTER);
    }

    private void showNextQuestion() {
        questionPanel.removeAll();

        if (currentQuestionIndex >= questions.size()) {
            endQuiz();
            return;
        }

        Question q = questions.get(currentQuestionIndex);

        JLabel questionLabel = new JLabel(q.getQuestion());
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionPanel.add(Box.createVerticalStrut(10));
        questionPanel.add(questionLabel);

        String[] options = q.getOptions();

        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (int i = 0; i < options.length; i++) {
            JButton btn = new JButton("<html><center>" + options[i] + "</center></html>");
            int answer = i;
            btn.setPreferredSize(new Dimension(200, 100));

            btn.addActionListener(e -> {
                if (q.isCorrect(answer)) {
                    correctAnswers++;
                    JOptionPane.showMessageDialog(this, "Resposta correta!");
                } else {
                    JOptionPane.showMessageDialog(this, "Resposta incorreta.");
                }
                currentQuestionIndex++;
                showNextQuestion();
            });

            optionsPanel.add(btn);
        }

        questionPanel.add(Box.createVerticalStrut(20));
        questionPanel.add(optionsPanel);

        questionPanel.revalidate();
        questionPanel.repaint();
    }

    public void finishQuiz(String studentName, String quizName, int correctAnswers, int totalQuestions) {
        int score = (int) ((double) correctAnswers / totalQuestions * 100);
        CrudBD.saveResult(studentName, quizName, score);
        JOptionPane.showMessageDialog(this, "Quiz finalizado! Sua pontuação: " + score + "%");
    }

    private void endQuiz() {
        int totalQuestions = questions.size();
        String quizName = "Quiz Configurado";
        String studentName = user.getName();

        finishQuiz(studentName, quizName, correctAnswers, totalQuestions);
        dispose();
    }
}