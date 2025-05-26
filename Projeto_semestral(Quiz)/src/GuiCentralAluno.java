import java.awt.*;
import java.util.List;
import javax.swing.*;

public class GuiCentralAluno extends JFrame {
    private JButton playRandomButton;
    private JButton playQuizButton;
    private JButton viewResultsButton;
    private User user;

    public GuiCentralAluno(User user) {
        this.user = user;

        setTitle("Central do Aluno - " + user.getName());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));

        // Botões
        playRandomButton = new JButton("Jogar com 10 Perguntas Aleatórias");
        playQuizButton = new JButton("Jogar Quiz do Professor");
        viewResultsButton = new JButton("Ver Meus Resultados");

        mainPanel.add(playRandomButton);
        mainPanel.add(playQuizButton);
        mainPanel.add(viewResultsButton);

        add(mainPanel);

        // Listeners
        playRandomButton.addActionListener(e -> playRandomQuiz());
        playQuizButton.addActionListener(e -> playProfessorQuiz());
        viewResultsButton.addActionListener(e -> viewResults());

        setVisible(true);
    }

    private void playRandomQuiz() {
        List<Question> questions = CrudBD.getRandomQuestions(10); // Busca 10 perguntas aleatórias
        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há perguntas suficientes no banco de dados.");
            return;
        }
        new GuiQuestions(questions, user); // Abre a interface do quiz com as perguntas
    }

    private void playProfessorQuiz() {
        int quizId = 1; // Supondo que o ID do quiz configurado pelo professor seja 1
        List<Question> questions = CrudBD.getQuizQuestions(quizId); // Busca as perguntas do quiz configurado
        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum quiz configurado pelo professor no momento.");
            return;
        }
        new GuiQuestions(questions, user); // Abre a interface do quiz com as perguntas
    }

    private void viewResults() {
        List<String[]> results = CrudBD.getStudentResults(user.getName()); // Busca os resultados do aluno
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Você ainda não possui resultados.");
            return;
        }
        new GuiViewResults(results); // Abre a interface para exibir os resultados
    }

    public static void main(String[] args) {
        User user = new User("Aluno Teste"); // Exemplo de usuário
        new GuiCentralAluno(user);
    }
}