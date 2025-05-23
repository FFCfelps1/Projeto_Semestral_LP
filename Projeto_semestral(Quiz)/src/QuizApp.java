import java.util.List;

public class QuizApp {
    public static void main(String[] args) {
        // Exibe a tela de login
        GuiUser guiUser = new GuiUser(null);
        String userName = guiUser.getUserName();
        boolean isTeacher = guiUser.isTeacher();

        if (userName == null || userName.isEmpty()) {
            System.out.println("Login cancelado.");
            return; // Encerra o programa se o login for cancelado
        }

        // Recupera ou cria o usuário no banco de dados
        User user = CrudBD.getUser(userName);
        if (user == null) {
            user = new User(userName);
            CrudBD.saveUser(user); // Salva o novo usuário no banco de dados
        }

        if (isTeacher) {
            System.out.println("Bem-vindo, Professor " + user.getName() + "!");
            new GuiEditQuestions(); // Abre a interface de edição de perguntas para professores
        } else {
            System.out.println("Bem-vindo, Aluno " + user.getName() + "!");
            List<Question> questions = CrudBD.getQuestions();

            // Limita a lista de perguntas a no máximo 10
            if (questions.size() > 10) {
                questions = questions.subList(0, 10);
            }

            // Cada rodada de pergunta deve ter no máximo 10 perguntas
            if (!questions.isEmpty()) {
                new GuiQuestions(questions); // Passa a lista de perguntas para o construtor de GuiQuestions
            } else {
                System.out.println("Nenhuma pergunta encontrada no banco de dados.");
            }
        }
    }
}