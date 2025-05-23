import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrudBD {

    public static List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions";

        try (Connection conn = ConnFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String questionText = rs.getString("question");
                String[] options = {
                    rs.getString("optionA"),
                    rs.getString("optionB"),
                    rs.getString("optionC"),
                    rs.getString("optionD")
                };
                int correctOption = rs.getInt("correctOption");

                questions.add(new Question(questionText, options, correctOption));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
    // Método para salvar o usuário no banco de dados
    public static void saveUser(User user) {
        String sql = "INSERT INTO users (name, score) VALUES (?, ?) ON DUPLICATE KEY UPDATE score = ?";
        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setInt(2, user.getScore());
            stmt.setInt(3, user.getScore());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para recuperar um usuário do banco de dados
    public static User getUser(String name) {
        String sql = "SELECT * FROM users WHERE name = ?";
        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getString("name"));
                user.addScore(rs.getInt("score"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se o usuário não for encontrado
    }

    public static void addQuestion(Question question) {
        String sql = "INSERT INTO questions (question, optionA, optionB, optionC, optionD, correctOption) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, question.getQuestion());
            stmt.setString(2, question.getOptions()[0]);
            stmt.setString(3, question.getOptions()[1]);
            stmt.setString(4, question.getOptions()[2]);
            stmt.setString(5, question.getOptions()[3]);
            stmt.setInt(6, question.getCorrectAnswer());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateQuestion(Question question) {
        String sql = "UPDATE questions SET optionA = ?, optionB = ?, optionC = ?, optionD = ?, correctOption = ? WHERE question = ?";

        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, question.getOptions()[0]);
            stmt.setString(2, question.getOptions()[1]);
            stmt.setString(3, question.getOptions()[2]);
            stmt.setString(4, question.getOptions()[3]);
            stmt.setInt(5, question.getCorrectAnswer());
            stmt.setString(6, question.getQuestion());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeQuestion(Question question) {
        String sql = "DELETE FROM questions WHERE question = ?";

        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, question.getQuestion());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
