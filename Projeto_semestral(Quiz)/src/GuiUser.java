import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GuiUser extends JDialog {

    private JTextField nameField;
    private JRadioButton studentButton;
    private JRadioButton teacherButton;
    private JPasswordField passwordField;
    private JButton loginButton;

    private static final String TEACHER_PASSWORD = "12345";
    private String userName;
    private String senha;
    private boolean isTeacher;

    public GuiUser(Frame parent) {
        super(parent, "Cadastro de Usuário", true);
        setSize(450, 350);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Cores e fontes
        Color fundo = new Color(245, 245, 245);
        Color botao = new Color(230, 230, 230);
        Font fonte = new Font("Segoe UI", Font.PLAIN, 15);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(fundo);

        JLabel observacaoLabel = new JLabel("<html><b>Cadastro:</b> Digite seu nome, senha e selecione seu perfil.</html>");
        observacaoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Painel de campos
        JPanel fieldsPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        fieldsPanel.setBackground(fundo);

        nameField = new JTextField();
        nameField.setFont(fonte);

        passwordField = new JPasswordField();
        passwordField.setFont(fonte);

        studentButton = new JRadioButton("Aluno");
        teacherButton = new JRadioButton("Professor");

        studentButton.setBackground(fundo);
        teacherButton.setBackground(fundo);
        studentButton.setFont(fonte);
        teacherButton.setFont(fonte);

        ButtonGroup group = new ButtonGroup();
        group.add(studentButton);
        group.add(teacherButton);

        // Adiciona os componentes
        fieldsPanel.add(new JLabel("Nome:", SwingConstants.LEFT));
        fieldsPanel.add(nameField);
        fieldsPanel.add(new JLabel("Senha:", SwingConstants.LEFT));
        fieldsPanel.add(passwordField);

        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rolePanel.setBackground(fundo);
        rolePanel.add(studentButton);
        rolePanel.add(teacherButton);
        fieldsPanel.add(rolePanel);

        loginButton = new JButton("Entrar");
        loginButton.setFont(fonte);
        loginButton.setBackground(botao);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        mainPanel.add(observacaoLabel, BorderLayout.NORTH);
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(loginButton, BorderLayout.SOUTH);

        add(mainPanel);

        // Listener de botão
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String senhaInput = new String(passwordField.getPassword()).trim();

                String[] teacher = {"Matheus", "Calvetti", "Felipe", "Jonas", "Fernando", "Ruthe"};
                boolean teacherName = false;

                for (String teach : teacher) {
                    if (teach.equalsIgnoreCase(name)) {
                        teacherName = true;
                        break;
                    }
                }

                if (name.isEmpty() || senhaInput.isEmpty()) {
                    JOptionPane.showMessageDialog(GuiUser.this, "Por favor, insira nome e senha.");
                    return;
                }

                if (teacherButton.isSelected()) {
                    if (!senhaInput.equals(TEACHER_PASSWORD) && !teacherName) {
                        JOptionPane.showMessageDialog(GuiUser.this, "Login de professor inválido.");
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
                dispose();
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