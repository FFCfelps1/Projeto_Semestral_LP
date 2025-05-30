import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class GuiViewResultsProfessor extends JFrame {
    private JTable resultsTable;
    private JScrollPane scrollPane;

    public GuiViewResultsProfessor() {
        setTitle("Resultados dos Quizzes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color fundo = new Color(245, 245, 245);
        Color header = new Color(230, 230, 230);
        Font fonte = new Font("Segoe UI", Font.PLAIN, 14);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(fundo);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Tabela de resultados
        String[] columnNames = {"Aluno", "Quiz", "Pontuação"};
        List<String[]> results = CrudBD.getResults(); // Recupera os resultados do banco de dados
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum resultado encontrado.");
            return;
        }

        String[][] data = results.toArray(new String[0][]);

        resultsTable = new JTable(data, columnNames);
        resultsTable.setFont(fonte);
        resultsTable.setRowHeight(28);
        resultsTable.setGridColor(new Color(220, 220, 220));
        resultsTable.setBackground(Color.WHITE);
        resultsTable.setShowGrid(true);

        // Centraliza o conteúdo
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < resultsTable.getColumnCount(); i++) {
            resultsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Cabeçalho estilizado
        JTableHeader tableHeader = resultsTable.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableHeader.setBackground(header);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setReorderingAllowed(false);

        scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);

        setVisible(true);
    }
}