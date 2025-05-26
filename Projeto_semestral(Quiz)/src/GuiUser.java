import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiUser extends JDialog {
    private JTextField nameField;
    private JRadioButton studentButton;
    private JRadioButton teacherButton;
    private JTextField codeField;
    private JPasswordField passwordField; 
    private JButton loginButton;

    private static final String TEACHER_CODE = "20comer70correr"; // Código de confirmação para professores
    private String userName;
    private String senha;
    private boolean isTeacher;

    public GuiUser(Frame parent) {
        super(parent, "Cadastro de Usuário", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 1));

        // Campo de nome
        JLabel nameLabel = new JLabel("Digite seu nome:");
        nameField = new JTextField();

        // Campo da senha
        JLabel passwordLabel = new JLabel("Digite sua senha:"); 
        passwordField = new JPasswordField(); 

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
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
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
                String senhaInput = new String(passwordField.getPassword()).trim();
                if (name.isEmpty() || senhaInput.isEmpty()) {
                    JOptionPane.showMessageDialog(GuiUser.this, "Por favor, insira nome e senha.");
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
                senha = senhaInput;
                dispose(); // Fecha a janela de login
            }
        });

        setVisible(true);
    }

    public String getUserName() {
        return userName;
    }
    public String getSenha() {
        return senha;
    }
    public boolean isTeacher() {
        return isTeacher;
    }
}