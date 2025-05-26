import java.awt.*;
import java.util.List;
import javax.swing.*;

public class GuiViewResults extends JFrame {
    private JTable resultsTable;
    private JScrollPane scrollPane;

    public GuiViewResults(List<String[]> results) {
        setTitle("Meus Resultados");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Tabela de resultados
        String[] columnNames = {"Quiz", "Pontuação"};
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Você ainda não possui resultados.");
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