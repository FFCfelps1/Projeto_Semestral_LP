import java.util.List;

public class QuizApp {
    public static void main(String[] args) {
        List<Question> questions = CrudBD.getQuestions();

        // Limita a lista de perguntas a no máximo 10
        if (questions.size() > 10) {
            questions = questions.subList(0, 10);
        }

        // Cada rodada de pergunta deve ter no máximo 10 perguntas
        if (!questions.isEmpty()) {
            new Gui(questions); // Passa a lista de perguntas para o construtor de Gui
        } else {
            System.out.println("Nenhuma pergunta encontrada no banco de dados.");
        }
    }
}