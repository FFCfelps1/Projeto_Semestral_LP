import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class GuiSetQuiz extends JFrame {
    private JList<String> questionList;
    private DefaultListModel<String> questionListModel;
    private JButton saveQuizButton;
    private List<Question> questions;
    private List<Question> selectedQuestions;

    public GuiSetQuiz() {
        setTitle("Configurar Quiz");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Lista de perguntas
        questionListModel = new DefaultListModel<>();
        questionList = new JList<>(questionListModel);
        questionList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(questionList);

        // Botão de salvar
        saveQuizButton = new JButton("Salvar Quiz");

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(saveQuizButton, BorderLayout.SOUTH);

        add(mainPanel);

        // Carrega as perguntas do banco de dados
        loadQuestions();

        // Listener para salvar o quiz
        saveQuizButton.addActionListener(e -> saveQuiz());

        setVisible(true);
    }

    private void loadQuestions() {
        questions = CrudBD.getQuestions(); // Recupera as perguntas do banco de dados
        questionListModel.clear();
        for (Question question : questions) {
            questionListModel.addElement(question.getQuestion());
        }
    }

    private void saveQuiz() {
        int[] selectedIndices = questionList.getSelectedIndices();
        if (selectedIndices.length == 0) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione pelo menos uma pergunta para o quiz.");
            return;
        }

        selectedQuestions = new ArrayList<>();
        for (int index : selectedIndices) {
            selectedQuestions.add(questions.get(index));
        }

        // Salva o quiz no banco de dados
        CrudBD.saveQuiz(selectedQuestions);
        JOptionPane.showMessageDialog(this, "Quiz salvo com sucesso!");
        dispose();
    }
}