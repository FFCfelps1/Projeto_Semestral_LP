import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

public class GuiQuestions extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel quizPanel;
    private JPanel questionPanel;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int totalScore = 0;
    private User user;
    private int acertos = 0; // Contador de acertos

    // Timer
    private Timer timer;
    private long startTime;
    private final int TIME_LIMIT = 40; // segundos por pergunta
    private JLabel timerLabel;

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

        if (timer != null) timer.stop();

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

        // Timer label
        timerLabel = new JLabel("Tempo: " + TIME_LIMIT + "s");
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionPanel.add(Box.createVerticalStrut(10));
        questionPanel.add(timerLabel);

        // Timer logic
        startTime = System.currentTimeMillis();
        timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long elapsed = (System.currentTimeMillis() - startTime) / 1000;
                int remaining = TIME_LIMIT - (int) elapsed;
                timerLabel.setText("Tempo: " + remaining + "s");
                if (remaining <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(GuiQuestions.this, "Tempo esgotado!");
                    currentQuestionIndex++;
                    showNextQuestion();
                }
            }
        });
        timer.start();

        // Cores e ícones estilo Kahoot
        Color[] colors = {
            new Color(220, 20, 60),    // Vermelho
            new Color(30, 144, 255),   // Azul
            new Color(255, 215, 0),    // Amarelo
            new Color(34, 139, 34)     // Verde
        };
        ShapeIcon[] icons = {
            new ShapeIcon(ShapeIcon.Shape.TRIANGLE, 32, 32, Color.WHITE),
            new ShapeIcon(ShapeIcon.Shape.DIAMOND, 32, 32, Color.WHITE),
            new ShapeIcon(ShapeIcon.Shape.CIRCLE, 32, 32, Color.WHITE),
            new ShapeIcon(ShapeIcon.Shape.SQUARE, 32, 32, Color.WHITE)
        };

        for (int i = 0; i < options.length; i++) {
            JButton btn = new JButton("<html><center>" + options[i] + "</center></html>");
            int answer = i;
            btn.setPreferredSize(new Dimension(200, 100));
            btn.setBackground(colors[i]);
            btn.setForeground(Color.BLACK);
            btn.setIcon(icons[i]);
            btn.setFocusPainted(false);

            btn.addActionListener(e -> {
                timer.stop();
                long elapsedMillis = System.currentTimeMillis() - startTime;
                int score = 0;
                if (q.isCorrect(answer)) {
                    // Pontuação: 1000 - (tempo gasto em segundos * 1000 / TIME_LIMIT)
                    score = Math.max(0, 1000 - (int)((elapsedMillis * 1000) / (TIME_LIMIT * 1000)));
                    JOptionPane.showMessageDialog(this, "Resposta correta!\nPontuação: " + score);
                    acertos ++;
                } else {
                    JOptionPane.showMessageDialog(this, "Resposta incorreta.\nPontuação: 0");
                }
                totalScore += score;
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

    public void finishQuiz(int studentName, String quizName, int totalScore) {
        CrudBD.saveResult(studentName, quizName, totalScore);
        JOptionPane.showMessageDialog(this, "Quiz finalizado! Sua pontuação: " + totalScore + " de 1000 ");
    }

    private void endQuiz() {
        String quizName = "Quiz Configurado";
        int studentName = user.getUser_id();

        totalScore = totalScore / acertos;

        finishQuiz(studentName, quizName, totalScore);
        dispose();
    }
}