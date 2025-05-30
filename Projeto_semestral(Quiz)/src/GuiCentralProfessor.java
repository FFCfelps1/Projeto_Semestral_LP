import java.awt.*;
import javax.swing.*;

public class GuiCentralProfessor extends JFrame {
    private JButton manageQuestionsButton;
    private JButton setQuizButton;
    private JButton viewResultsButton;

    public GuiCentralProfessor() {
        setTitle("Central do Professor");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fonte e cores
        Font fonte = new Font("Segoe UI", Font.PLAIN, 16);
        Color fundo = new Color(245, 245, 245);
        Color corBotao = new Color(230, 230, 230);

        // Painel principal com margem e título
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(fundo);

        JLabel titleLabel = new JLabel("Painel do Professor", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 50));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Painel com botões
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        buttonPanel.setBackground(fundo);

        manageQuestionsButton = criarBotao("Gerenciar Perguntas", fonte, corBotao);
        setQuizButton = criarBotao("Configurar Quiz", fonte, corBotao);
        viewResultsButton = criarBotao("Ver Resultados", fonte, corBotao);

        buttonPanel.add(manageQuestionsButton);
        buttonPanel.add(setQuizButton);
        buttonPanel.add(viewResultsButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Listeners
        manageQuestionsButton.addActionListener(e -> openManageQuestions());
        setQuizButton.addActionListener(e -> openSetQuiz());
        viewResultsButton.addActionListener(e -> openViewResults());

        setVisible(true);
    }

    private JButton criarBotao(String texto, Font fonte, Color corFundo) {
        JButton botao = new JButton(texto);
        botao.setFont(fonte);
        botao.setBackground(corFundo);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        return botao;
    }

    private void openManageQuestions() {
        new GuiEditQuestions();
    }

    private void openSetQuiz() {
        new GuiSetQuiz();
    }

    private void openViewResults() {
        new GuiViewResultsProfessor();
    }

    public static void main(String[] args) {
        new GuiCentralProfessor();
    }
}
