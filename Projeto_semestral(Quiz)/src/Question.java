public class Question {
  private int id; // ID da pergunta no banco de dados
  private String question;
  private String[] options;
  private int correctAnswer;

  public Question(String question, String[] options, int correctAnswer) {
      this.question = question;
      this.options = options;
      this.correctAnswer = correctAnswer;
  }

  // Construtor adicional para incluir o ID
  public Question(int id, String question, String[] options, int correctAnswer) {
      this.id = id;
      this.question = question;
      this.options = options;
      this.correctAnswer = correctAnswer;
  }

  public int getId() {
      return id;
  }

  public String getQuestion() {
      return question;
  }

  public String[] getOptions() {
      return options;
  }

  public int getCorrectAnswer() {
      return correctAnswer;
  }

  public boolean isCorrect(int answer) {
      return answer == correctAnswer;
  }
}