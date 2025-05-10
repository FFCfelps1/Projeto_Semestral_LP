package src.ui;

import src.service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaLogin extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private UsuarioService usuarioService;

    public TelaLogin() {
        usuarioService = new UsuarioService();

        setTitle("Login - Quiz");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(3, 2));

        painel.add(new JLabel("Email:"));
        campoEmail = new JTextField();
        painel.add(campoEmail);

        painel.add(new JLabel("Senha:"));
        campoSenha = new JPasswordField();
        painel.add(campoSenha);

        JButton btnLogin = new JButton("Entrar");
        btnLogin.addActionListener(this::login);
        painel.add(btnLogin);

        add(painel);
    }

    private void login(ActionEvent e) {
        String email = campoEmail.getText();
        char[] senhaArray = campoSenha.getPassword();

        try {
            // Validação de entrada
            if (email == null || email.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "O campo de email não pode estar vazio.");
                return;
            }
            if (senhaArray.length == 0) {
                JOptionPane.showMessageDialog(this, "O campo de senha não pode estar vazio.");
                return;
            }

            // Autenticação
            if (usuarioService.autenticar(email, new String(senhaArray))) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                // Aqui você pode abrir a próxima tela
            } else {
                JOptionPane.showMessageDialog(this, "Email ou senha inválidos.");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            // Limpeza do array de senha por segurança
            java.util.Arrays.fill(senhaArray, '\0');
        }
    }
}