import java.awt.*;
import java.util.List;
import javax.swing.*;

public class GuiViewResultsProfessor extends JFrame {
    private JTable resultsTable;
    private JScrollPane scrollPane;

    public GuiViewResultsProfessor() {
        setTitle("Resultados dos Quizzes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Tabela de resultados
        String[] columnNames = {"Aluno", "Quiz", "Pontuação"};
        List<String[]> results = CrudBD.getResults(); // Recupera os resultados do banco de dados
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum resultado encontrado.");
            return;
        }
        String[][] data = results.toArray(new String[0][]);

        resultsTable = new JTable(data, columnNames);
        scrollPane = new JScrollPane(resultsTable);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
    }
}