import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class GuiSetQuiz extends JFrame {
    private JTextField quizNameField;
    private JButton saveQuizButton;
    private List<Question> questions;
    private List<Question> selectedQuestions;
    private JPanel questionsPanel;
    private List<JCheckBox> questionCheckBoxes;

    public GuiSetQuiz() {
        setTitle("Configurar Quiz");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Campo para o nome do quiz
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel quizNameLabel = new JLabel("Nome do Quiz:");
        quizNameField = new JTextField();
        topPanel.add(quizNameLabel, BorderLayout.WEST);
        topPanel.add(quizNameField, BorderLayout.CENTER);

        // Painel de perguntas com checkboxes
        questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(questionsPanel);

        saveQuizButton = new JButton("Salvar Quiz");

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(saveQuizButton, BorderLayout.SOUTH);

        add(mainPanel);

        loadQuestions();

        saveQuizButton.addActionListener(e -> saveQuiz());

        setVisible(true);
    }

    private void loadQuestions() {
        questions = CrudBD.getQuestions();
        questionsPanel.removeAll();
        questionCheckBoxes = new ArrayList<>();
        for (Question question : questions) {
            JCheckBox checkBox = new JCheckBox(question.getQuestion());
            questionCheckBoxes.add(checkBox);
            questionsPanel.add(checkBox);
        }
        questionsPanel.revalidate();
        questionsPanel.repaint();
    }

    private void saveQuiz() {
        String quizName = quizNameField.getText().trim();
        if (quizName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um nome para o quiz.");
            return;
        }

        selectedQuestions = new ArrayList<>();
        for (int i = 0; i < questionCheckBoxes.size(); i++) {
            if (questionCheckBoxes.get(i).isSelected()) {
                selectedQuestions.add(questions.get(i));
            }
        }

        if (selectedQuestions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione pelo menos uma pergunta para o quiz.");
            return;
        }

        CrudBD.saveQuiz(quizName, selectedQuestions);
        JOptionPane.showMessageDialog(this, "Quiz '" + quizName + "' salvo com sucesso!");
        dispose();
    }
}