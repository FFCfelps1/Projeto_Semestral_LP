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
                int id = rs.getInt("id");
                String questionText = rs.getString("question");
                String[] options = {
                    rs.getString("optionA"),
                    rs.getString("optionB"),
                    rs.getString("optionC"),
                    rs.getString("optionD")
                };
                int correctOption = rs.getInt("correctOption");

                questions.add(new Question(id, questionText, options, correctOption));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public static void saveUser(User user, String senha) {
        String sql = "INSERT INTO users (name, senha, score) VALUES (?, ?, ?)" + 
                     " ON DUPLICATE KEY UPDATE score = ?";
        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, senha);
            stmt.setInt(3, user.getScore());
            stmt.setInt(4, user.getScore());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User getUser(String name, String senha) {
        String sql = "SELECT * FROM users WHERE name = ? AND senha = ?";
        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("senha"));
                user.addScore(rs.getInt("score"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public static void saveQuiz(List<Question> questions) {
        String sql = "INSERT INTO quizzes (question_id) VALUES (?)";

        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Question question : questions) {
                stmt.setInt(1, question.getId());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getId(String questionText) {
        String sql = "SELECT id FROM questions WHERE question = ?";
        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, questionText);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<String[]> getResults() {
        List<String[]> results = new ArrayList<>();
        String sql = "SELECT student_name, quiz_name, score FROM results ORDER BY id DESC";

        try (Connection conn = ConnFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String studentName = rs.getString("student_name");
                String quizName = rs.getString("quiz_name");
                String score = rs.getString("score");
                results.add(new String[]{studentName, quizName, score});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public static List<Question> getRandomQuestions(int limit) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions ORDER BY RAND() LIMIT ?";

        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String questionText = rs.getString("question");
                String[] options = {
                    rs.getString("optionA"),
                    rs.getString("optionB"),
                    rs.getString("optionC"),
                    rs.getString("optionD")
                };
                int correctOption = rs.getInt("correctOption");

                questions.add(new Question(id, questionText, options, correctOption));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    public static List<String[]> getStudentResults(String studentName) {
        List<String[]> results = new ArrayList<>();
        String sql = "SELECT quiz_name, score FROM results WHERE student_name = ? ORDER BY id DESC";

        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String quizName = rs.getString("quiz_name");
                String score = rs.getString("score");
                results.add(new String[]{quizName, score});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public static List<Question> getQuizQuestions(int quizId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT q.id, q.question, q.optionA, q.optionB, q.optionC, q.optionD, q.correctOption " +
                     "FROM quiz_sequence qs " +
                     "JOIN questions q ON qs.question_id = q.id " +
                     "WHERE qs.quiz_id = ? " +
                     "ORDER BY qs.question_order";

        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quizId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String questionText = rs.getString("question");
                String[] options = {
                    rs.getString("optionA"),
                    rs.getString("optionB"),
                    rs.getString("optionC"),
                    rs.getString("optionD")
                };
                int correctOption = rs.getInt("correctOption");

                questions.add(new Question(id, questionText, options, correctOption));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    public static void saveResult(String studentName, String quizName, int score) {
        String sql = "INSERT INTO results (student_name, quiz_name, score) VALUES (?, ?, ?)";

        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentName);
            stmt.setString(2, quizName);
            stmt.setInt(3, score);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ MÉTODO ADICIONADO: getUser apenas pelo nome (sem senha)
    public static User getUser(String name) {
        String sql = "SELECT * FROM users WHERE name = ?";
        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("senha"));
                user.addScore(rs.getInt("score"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
