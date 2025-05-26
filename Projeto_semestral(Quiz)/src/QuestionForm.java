import java.awt.*;
import javax.swing.*;

public class QuestionForm extends JDialog {
    private JTextField questionField;
    private JTextField[] optionFields;
    private JComboBox<Integer> correctAnswerBox;
    private JButton saveButton, cancelButton;
    private Question question;

    public QuestionForm(Frame parent, Question question) {
        super(parent, "Formulário de Pergunta", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        this.question = question;

        JPanel mainPanel = new JPanel(new GridLayout(7, 1));

        // Campo para a pergunta
        mainPanel.add(new JLabel("Pergunta:"));
        questionField = new JTextField();
        mainPanel.add(questionField);

        // Campos para as opções
        optionFields = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            mainPanel.add(new JLabel("Opção " + (i + 1) + ":"));
            optionFields[i] = new JTextField();
            mainPanel.add(optionFields[i]);
        }

        // Campo para a resposta correta
        mainPanel.add(new JLabel("Resposta Correta (1-4):"));
        correctAnswerBox = new JComboBox<>(new Integer[]{0, 1, 2, 3});
        mainPanel.add(correctAnswerBox);

        // Botões
        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Salvar");
        cancelButton = new JButton("Cancelar");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Preenche os campos se estiver editando
        if (question != null) {
            questionField.setText(question.getQuestion());
            String[] options = question.getOptions();
            for (int i = 0; i < options.length; i++) {
                optionFields[i].setText(options[i]);
            }
            correctAnswerBox.setSelectedIndex(question.getCorrectAnswer() - 1);
        }

        // Listeners
        saveButton.addActionListener(e -> saveQuestion());
        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void saveQuestion() {
        String questionText = questionField.getText().trim();
        String[] options = new String[4];
        for (int i = 0; i < 4; i++) {
            options[i] = optionFields[i].getText().trim();
        }
        int correctAnswer = correctAnswerBox.getSelectedIndex() + 1;

        if (questionText.isEmpty() || options[0].isEmpty() || options[1].isEmpty() || options[2].isEmpty() || options[3].isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
            return;
        }

        question = new Question(questionText, options, correctAnswer);
        dispose();
    }

    public Question getQuestion() {
        return question;
    }
}