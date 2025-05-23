import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GuiEditQuestions extends JFrame {
    private JList<String> questionList;
    private DefaultListModel<String> questionListModel;
    private JButton addButton, editButton, removeButton;
    private List<Question> questions;

    public GuiEditQuestions() {
        setTitle("Editar Perguntas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Lista de perguntas
        questionListModel = new DefaultListModel<>();
        questionList = new JList<>(questionListModel);
        JScrollPane scrollPane = new JScrollPane(questionList);

        // Botões
        addButton = new JButton("Adicionar");
        editButton = new JButton("Alterar");
        removeButton = new JButton("Remover");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Carrega as perguntas do banco de dados
        loadQuestions();

        // Listeners
        addButton.addActionListener(e -> addQuestion());
        editButton.addActionListener(e -> editQuestion());
        removeButton.addActionListener(e -> removeQuestion());

        setVisible(true);
    }

    private void loadQuestions() {
        questions = CrudBD.getQuestions(); // Recupera as perguntas do banco de dados
        questionListModel.clear();
        for (Question question : questions) {
            questionListModel.addElement(question.getQuestion());
        }
    }

    private void addQuestion() {
        QuestionForm form = new QuestionForm(this, null);
        Question newQuestion = form.getQuestion();
        if (newQuestion != null) {
            CrudBD.addQuestion(newQuestion); // Adiciona a nova pergunta ao banco de dados
            loadQuestions(); // Recarrega a lista de perguntas
        }
    }

    private void editQuestion() {
        int selectedIndex = questionList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma pergunta para alterar.");
            return;
        }

        Question selectedQuestion = questions.get(selectedIndex);
        QuestionForm form = new QuestionForm(this, selectedQuestion);
        Question updatedQuestion = form.getQuestion();
        if (updatedQuestion != null) {
            CrudBD.updateQuestion(updatedQuestion); // Atualiza a pergunta no banco de dados
            loadQuestions(); // Recarrega a lista de perguntas
        }
    }

    private void removeQuestion() {
        int selectedIndex = questionList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma pergunta para remover.");
            return;
        }

        Question selectedQuestion = questions.get(selectedIndex);
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover esta pergunta?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            CrudBD.removeQuestion(selectedQuestion); // Remove a pergunta do banco de dados
            loadQuestions(); // Recarrega a lista de perguntas
        }
    }

    public static void main(String[] args) {
        new GuiEditQuestions();
    }
}