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
        List<String[]> quizzes = CrudBD.getAllQuizzes(); // Recupera todos os quizzes disponíveis
        if (quizzes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum quiz configurado pelo professor no momento.");
            return;
        }
    
        // Exibe uma lista de quizzes para o aluno escolher
        String[] quizNames = quizzes.stream().map(q -> q[1]).toArray(String[]::new);
        String selectedQuiz = (String) JOptionPane.showInputDialog(
                this,
                "Selecione um quiz para jogar:",
                "Quizzes Disponíveis",
                JOptionPane.PLAIN_MESSAGE,
                null,
                quizNames,
                quizNames[0]
        );
    
        if (selectedQuiz == null) {
            return; // O aluno cancelou a seleção
        }
    
        // Recupera o ID do quiz selecionado
        int quizId = Integer.parseInt(quizzes.stream()
                .filter(q -> q[1].equals(selectedQuiz))
                .findFirst()
                .get()[0]);
    
        // Busca as perguntas do quiz selecionado
        List<Question> questions = CrudBD.getQuizQuestions(quizId);
        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O quiz selecionado não possui perguntas.");
            return;
        }
    
        // Inicia o quiz
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
        User user = new User("Aluno Teste", "1234"); // Exemplo de usuário
        new GuiCentralAluno(user);
    }
}