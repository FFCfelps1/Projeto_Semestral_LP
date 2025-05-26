
public class QuizApp {
    public static void main(String[] args) {
        // Exibe a tela de login
        GuiUser guiUser = new GuiUser(null);
        String userName = guiUser.getUserName();
        String senha = guiUser.getSenha();
        boolean isTeacher = guiUser.isTeacher();

        if (userName == null || userName.isEmpty()) {
            System.out.println("Login cancelado.");
            return; // Encerra o programa se o login for cancelado
        }

        // Recupera ou cria o usuário no banco de dados
        User user = CrudBD.getUser(userName, senha);
        if (user == null) {
            user = new User(userName, senha);
            CrudBD.saveUser(user, senha); // Salva o novo usuário no banco de dados
        }

        // Fluxo para professores
        if (isTeacher) {
            System.out.println("Bem-vindo, Professor " + user.getName() + "!");
            new GuiCentralProfessor(); // Abre a central do professor
            return; // Encerra o fluxo principal após abrir a central do professor
        }

        // Fluxo para alunos
        System.out.println("Bem-vindo, Aluno " + user.getName() + "!");
        new GuiCentralAluno(user); // Abre a central do aluno
    }
}
//Fim da sessão de códigos usando MySQL