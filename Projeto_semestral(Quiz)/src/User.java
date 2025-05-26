public class User {
  private String name;
  private int score;
  private String senha;

  public User(String name, String senha) {
      this.name = name;
      this.senha = senha;
      // Inicializa a pontuação do usuário
      this.score = 0;
  }

  public String getName() {
      return name;
  }
  public String getSenha() {
        return senha;
  }
  public void setSenha(String senha) {
      this.senha = senha;
  }
  public int getScore() {
      return score;
  }

  public void addScore(int points) {
      this.score += points;
  }
}
