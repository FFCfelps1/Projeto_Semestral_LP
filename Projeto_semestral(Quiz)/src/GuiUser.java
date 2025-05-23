import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiUser extends JDialog {
    private JTextField nameField;
    private JRadioButton studentButton;
    private JRadioButton teacherButton;
    private JTextField codeField;
    private JButton loginButton;

    private static final String TEACHER_CODE = "20comer70correr"; // Código de confirmação para professores
    private String userName;
    private boolean isTeacher;

    public GuiUser(Frame parent) {
        super(parent, "Cadastro de Usuário", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1));

        // Campo de nome
        JLabel nameLabel = new JLabel("Digite seu nome:");
        nameField = new JTextField();

        // Botões de seleção (Aluno ou Professor)
        studentButton = new JRadioButton("Aluno");
        teacherButton = new JRadioButton("Professor");
        ButtonGroup group = new ButtonGroup();
        group.add(studentButton);
        group.add(teacherButton);

        // Campo para código de professor
        JLabel codeLabel = new JLabel("Código de confirmação (somente para professores):");
        codeField = new JTextField();
        codeField.setEnabled(false); // Desabilitado por padrão

        // Botão de login
        loginButton = new JButton("Entrar");

        // Adiciona componentes ao painel principal
        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(studentButton);
        mainPanel.add(teacherButton);
        mainPanel.add(codeLabel);
        mainPanel.add(codeField);

        add(mainPanel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);

        // Listeners
        teacherButton.addActionListener(e -> codeField.setEnabled(true)); // Habilita o campo de código
        studentButton.addActionListener(e -> codeField.setEnabled(false)); // Desabilita o campo de código

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(GuiUser.this, "Por favor, insira seu nome.");
                    return;
                }

                if (teacherButton.isSelected()) {
                    String code = codeField.getText().trim();
                    if (!code.equals(TEACHER_CODE)) {
                        JOptionPane.showMessageDialog(GuiUser.this, "Código de confirmação inválido.");
                        return;
                    }
                    isTeacher = true;
                } else if (studentButton.isSelected()) {
                    isTeacher = false;
                } else {
                    JOptionPane.showMessageDialog(GuiUser.this, "Por favor, selecione se você é Aluno ou Professor.");
                    return;
                }

                userName = name;
                dispose(); // Fecha a janela de login
            }
        });

        setVisible(true);
    }

    public String getUserName() {
        return userName;
    }

    public boolean isTeacher() {
        return isTeacher;
    }
}