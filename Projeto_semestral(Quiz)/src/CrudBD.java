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
                int id = rs.getInt("id"); // Recupera o ID da pergunta
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

    // Método para salvar o usuário no banco de dados
    public static void saveUser(User user, String senha) {
       String sql = "INSERT INTO users (name, senha, score) VALUES (?, ?, ?)" + 
                     " ON DUPLICATE KEY UPDATE score = ?";
        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setInt(2, user.getScore());
            stmt.setString(3, senha);
            stmt.setInt(4, user.getScore());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para recuperar um usuário do banco de dados
    public static User getUser(String name) {
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

    public static void saveQuiz(String quizName, List<Question> questions) {
        String insertQuizSql = "INSERT INTO quizzes (name) VALUES (?)";
        String insertQuestionSql = "INSERT INTO quiz_questions (quiz_id, question_id) VALUES (?, ?)";
    
        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement quizStmt = conn.prepareStatement(insertQuizSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement questionStmt = conn.prepareStatement(insertQuestionSql)) {
    
            // Insere o quiz e recupera o ID gerado
            quizStmt.setString(1, quizName);
            quizStmt.executeUpdate();
            ResultSet rs = quizStmt.getGeneratedKeys();
            if (!rs.next()) {
                throw new SQLException("Falha ao criar o quiz.");
            }
            int quizId = rs.getInt(1);
    
            // Insere as perguntas associadas ao quiz
            for (Question question : questions) {
                questionStmt.setInt(1, quizId);
                questionStmt.setInt(2, question.getId());
                questionStmt.addBatch();
            }
            questionStmt.executeBatch();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Método Get ID
    public static int getId(String questionText) {
        String sql = "SELECT id FROM questions WHERE question = ?";
        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, questionText);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                return rs.getInt("id"); // Retorna o ID da pergunta
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 se a pergunta não for encontrada
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
    
            stmt.setString(1, studentName); // Filtra os resultados pelo nome do aluno
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
                     "FROM quiz_questions qq " +
                     "JOIN questions q ON qq.question_id = q.id " +
                     "WHERE qq.quiz_id = ?";
    
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

    public static List<String[]> getAllQuizzes() {
        List<String[]> quizzes = new ArrayList<>();
        String sql = "SELECT id, name FROM quizzes";
    
        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String name = rs.getString("name");
                quizzes.add(new String[]{id, name});
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return quizzes;
    }
}
