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

    public GuiQuestions(List<Question> questions) {
        this.questions = questions;

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
            JOptionPane.showMessageDialog(this, "Parabéns! Você terminou o quiz." + "\nResposta corretas: " + currentQuestionIndex);
            System.exit(0);
            return;
        }

        Question q = questions.get(currentQuestionIndex);

        JLabel questionLabel = new JLabel(q.getQuestion());
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionPanel.add(Box.createVerticalStrut(10));
        questionPanel.add(questionLabel);

        String[] options = q.getOptions();

        for (int i = 0; i < options.length; i++) {
            JButton btn = new JButton(options[i]);
            int answer = i;
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.addActionListener(e -> {
                System.out.println("Opção selecionada: " + answer);
                if (q.isCorrect(answer)) {
                    currentQuestionIndex++;
                    JOptionPane.showMessageDialog(this, "Resposta correta!");
                    showNextQuestion();
                } else {
                    JOptionPane.showMessageDialog(this, "Resposta incorreta. Tente novamente.");
                }
            });
            questionPanel.add(Box.createVerticalStrut(10));
            questionPanel.add(btn);
        }


    
        questionPanel.revalidate();
        questionPanel.repaint();
    }
}
