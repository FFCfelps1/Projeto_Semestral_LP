import java.awt.*;
import javax.swing.*;

public class GuiCentralProfessor extends JFrame {
    private JButton manageQuestionsButton;
    private JButton setQuizButton;
    private JButton viewResultsButton;

    public GuiCentralProfessor() {
        setTitle("Central do Professor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));

        // Botões
        manageQuestionsButton = new JButton("Gerenciar Perguntas");
        setQuizButton = new JButton("Configurar Quiz");
        viewResultsButton = new JButton("Ver Resultados");

        mainPanel.add(manageQuestionsButton);
        mainPanel.add(setQuizButton);
        mainPanel.add(viewResultsButton);

        add(mainPanel);

        // Listeners
        manageQuestionsButton.addActionListener(e -> openManageQuestions());
        setQuizButton.addActionListener(e -> openSetQuiz());
        viewResultsButton.addActionListener(e -> openViewResults());

        setVisible(true);
    }

    private void openManageQuestions() {
        new GuiEditQuestions(); // Abre a interface para gerenciar perguntas
    }

    private void openSetQuiz() {
        new GuiSetQuiz(); // Abre a interface para configurar um quiz
    }

    private void openViewResults() {
        new GuiViewResultsProfessor(); // Abre a interface para visualizar os resultados
    }

    public static void main(String[] args) {
        new GuiCentralProfessor();
    }
}