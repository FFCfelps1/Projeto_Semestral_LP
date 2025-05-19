public class Question {
  private String question;
  private String[] options;
  private int correctAnswer;

  public Question(String question, String[] options, int correctAnswer) {
      this.question = question;
      this.options = options;
      this.correctAnswer = correctAnswer;
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
    System.out.println("Resposta fornecida: " + answer);
    System.out.println("Resposta correta: " + correctAnswer);
    return answer == correctAnswer;
  }
}
