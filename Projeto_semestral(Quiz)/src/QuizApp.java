
import javax.swing.JOptionPane;

public class QuizApp {

    public static void main(String[] args) {
        // Exibe a tela de login
        GuiUser guiUser = new GuiUser(null);
        String userName = guiUser.getUserName();
        String senha = guiUser.getSenha();
        boolean isTeacher = guiUser.isTeacher();

        if (userName == null || userName.isEmpty() || senha == null || senha.isEmpty()) {
            System.out.println("Login cancelado.");
            return;
        }

        // Tenta buscar o usuário com nome e senha
        User user = CrudBD.getUser(userName, senha);

        if (user == null) {
            // Verifica se o nome já existe com outra senha
            User existente = CrudBD.getUser(userName); // Esse método busca só pelo nome
            if (existente != null) {
                // Nome existe mas senha está errada
                JOptionPane.showMessageDialog(null, "Senha incorreta para o usuário '" + userName + "'");
                return;
            }

            // Nome não existe, então podemos criar novo usuário
            user = new User(userName, senha);
            CrudBD.saveUser(user, senha);
        }

        // Fluxo para professores
        if (isTeacher) {
            System.out.println("Bem-vindo, Professor " + user.getName() + "!");
            new GuiCentralProfessor();
            return;
        }

        // Fluxo para alunos
        System.out.println("Bem-vindo, Aluno " + user.getName() + "!");
        new GuiCentralAluno(user);
    }
}
