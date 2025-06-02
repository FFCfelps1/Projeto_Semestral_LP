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

        // Cores e fontes para visual moderno
        Color fundo = new Color(245, 245, 245);
        Color botao = new Color(230, 230, 230);
        Font fonte = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(fundo);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Campo para o nome do quiz
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBackground(fundo);
        JLabel quizNameLabel = new JLabel("Nome do Quiz:");
        quizNameLabel.setFont(fonte);

        quizNameField = new JTextField();
        quizNameField.setFont(fonte);
        quizNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        topPanel.add(quizNameLabel, BorderLayout.WEST);
        topPanel.add(quizNameField, BorderLayout.CENTER);

        // Painel de perguntas com checkboxes
        questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setBackground(fundo);

        JScrollPane scrollPane = new JScrollPane(questionsPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Selecione as perguntas"));
        scrollPane.getViewport().setBackground(fundo);

        // Botão salvar
        saveQuizButton = new JButton("Salvar Quiz");
        saveQuizButton.setFont(fonte);
        saveQuizButton.setBackground(botao);
        saveQuizButton.setFocusPainted(false);
        saveQuizButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(fundo);
        buttonPanel.add(saveQuizButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        loadQuestions();
        saveQuizButton.addActionListener(e -> saveQuiz());

        setVisible(true);
    }

    private void loadQuestions() {
        questions = CrudBD.getQuestions();
        questionsPanel.removeAll();
        questionCheckBoxes = new ArrayList<>();

        Font fonte = new Font("Segoe UI", Font.PLAIN, 14);

        for (Question question : questions) {
            JCheckBox checkBox = new JCheckBox(question.getQuestion());
            checkBox.setFont(fonte);
            checkBox.setBackground(new Color(245, 245, 245));
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