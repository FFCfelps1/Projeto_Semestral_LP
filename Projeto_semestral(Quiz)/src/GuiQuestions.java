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
    private int correctAnswers = 0; // Adicionado para rastrear respostas corretas
    private User user; // Adicionado para armazenar o usuário atual

    public GuiQuestions(List<Question> questions, User user) { // Atualizado para aceitar o objeto User
        this.questions = questions;
        this.user = user; // Inicializa o campo user

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
            endQuiz(); // Finaliza o quiz quando todas as perguntas forem respondidas
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
                    correctAnswers++; // Incrementa o número de respostas corretas
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

    public void finishQuiz(String studentName, String quizName, int correctAnswers, int totalQuestions) {
        int score = (int) ((double) correctAnswers / totalQuestions * 100); // Calcula a pontuação em porcentagem
        CrudBD.saveResult(studentName, quizName, score); // Salva o resultado no banco de dados
        JOptionPane.showMessageDialog(this, "Quiz finalizado! Sua pontuação: " + score + "%");
    }

    private void endQuiz() {
        int totalQuestions = questions.size(); // Total de perguntas no quiz
        String quizName = "Quiz Configurado"; // Nome do quiz (pode ser dinâmico)
        String studentName = user.getName(); // Nome do aluno (recuperado do objeto `User`)

        finishQuiz(studentName, quizName, correctAnswers, totalQuestions); // Salva o resultado
        dispose(); // Fecha a janela do quiz
    }
}