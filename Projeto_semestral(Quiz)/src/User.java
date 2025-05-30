public class User {
    private int user_id;
    private String name;
    private String senha;
    private int totalScore;

    public User(int id, String name, String senha) {
        this.user_id = user_id;
        this.name = name;
        this.senha = senha;
        this.totalScore = 0;
    }

    // Construtor sem ID, usado para novos usuários
    public User(String name, String senha) {
        this.name = name;
        this.senha = senha;
        this.totalScore = 0;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int id) {
        this.user_id = id;
    }

    public String getName() {
        return name;
    }

    public String getSenha() {
        return senha;
    }

    public int getScore() {
        return totalScore;
    }

    public void addScore(int points) {
        this.totalScore += points;
    }

    public void setScore(int score) {
        this.totalScore = score;
    }
}