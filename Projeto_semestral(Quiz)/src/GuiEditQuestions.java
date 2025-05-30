import javax.swing.*;
import java.awt.*;
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

        // Cores e fontes para estilo Windows 11
        Color fundo = new Color(245, 245, 245);
        Color botao = new Color(230, 230, 230);
        Font fonte = new Font("Segoe UI", Font.PLAIN, 15);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(fundo);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Lista de perguntas
        questionListModel = new DefaultListModel<>();
        questionList = new JList<>(questionListModel);
        questionList.setFont(fonte);
        questionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        questionList.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JScrollPane scrollPane = new JScrollPane(questionList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Perguntas Cadastradas"));

        // Painel de botões
        addButton = new JButton("Adicionar");
        editButton = new JButton("Alterar");
        removeButton = new JButton("Remover");

        JButton[] botoes = { addButton, editButton, removeButton };
        for (JButton btn : botoes) {
            btn.setFont(fonte);
            btn.setBackground(botao);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180)),
                    BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(fundo);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);

        // Montagem
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Dados e ações
        loadQuestions();

        addButton.addActionListener(e -> addQuestion());
        editButton.addActionListener(e -> editQuestion());
        removeButton.addActionListener(e -> removeQuestion());

        setVisible(true);
    }

    private void loadQuestions() {
        questions = CrudBD.getQuestions();
        questionListModel.clear();
        for (Question question : questions) {
            questionListModel.addElement(question.getQuestion());
        }
    }

    private void addQuestion() {
        QuestionForm form = new QuestionForm(this, null);
        Question newQuestion = form.getQuestion();
        if (newQuestion != null) {
            CrudBD.addQuestion(newQuestion);
            loadQuestions();
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
            CrudBD.updateQuestion(updatedQuestion);
            loadQuestions();
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
            CrudBD.removeQuestion(selectedQuestion);
            loadQuestions();
        }
    }

    public static void main(String[] args) {
        new GuiEditQuestions();
    }
}
