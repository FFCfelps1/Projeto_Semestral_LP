import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrudBD {

    //// USUARIO
    // Método para salvar o usuário no banco de dados
    public static void saveUser(User user, String senha) {
        String sql = "INSERT INTO users (user_id, name, senha, totalScore) VALUES (?, ?, ?, ?)" + 
                     " ON DUPLICATE KEY UPDATE totalScore = ?";
        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Gera um ID aleatório entre 1 a 100
            // Garante que o ID seja único
            // (Pega todos os IDs existentes e verifica se o novo ID já está em uso)
            int attempts = 0;
            while (attempts < 100) {
                boolean idExists = false;
                int randomId = (int)(Math.random() * 90000) + 10000;

                // Verifica se o ID já existe
                String checkSql = "SELECT COUNT(*) FROM users WHERE user_id = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setInt(1, randomId);
                    ResultSet rs = checkStmt.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) {
                        idExists = true; // ID já existe
                    }
                }

                if (!idExists) {
                    user.setUser_id(randomId);
                    break; // ID único encontrado
                }
                attempts++;
            }

            if (attempts == 100) {
                throw new RuntimeException("Não foi possível gerar um ID único após 100 tentativas.");
            }

            stmt.setInt(1, user.getUser_id());
            stmt.setString(2, user.getName());
            stmt.setString(3, senha);
            stmt.setInt(4, user.getScore());
            stmt.setInt(5, user.getScore());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para recuperar um usuário do banco de dados
    public static User getUser(String name, String senha) {
        String sql = "SELECT * FROM users WHERE name = ? AND senha = ?";
        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, name);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                // Recupera todos os dados do usuário, incluindo o user_id
                User user = new User(rs.getString("name"), rs.getString("senha"));
                user.setUser_id(rs.getInt("user_id")); // Adiciona o user_id ao objeto User
                user.addScore(rs.getInt("totalScore")); // Adiciona a pontuação total
                return user;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se o usuário não for encontrado
    }

    // Método getUser apenas pelo nome
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
    ////

    //// QUESTIONS
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
    ////

    //// QUIZZES
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

    //// RESULTS
    public static List<String[]> getResults() {
        List<String[]> results = new ArrayList<>();
        String sql = "SELECT user_id, quiz_name, totalScore FROM results ORDER BY id DESC";
    
        try (Connection conn = ConnFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            while (rs.next()) {
                String user_id = rs.getString("user_id");
                String quizName = rs.getString("quiz_name");
                String totalScore = rs.getString("totalScore");
                results.add(new String[]{user_id, quizName, totalScore});
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return results;
    }


    public static List<String[]> getStudentResults(int user_id) {
        List<String[]> results = new ArrayList<>();
        String sql = "SELECT quiz_name, totalScore FROM results WHERE user_id = ? ORDER BY id DESC";
    
        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, user_id); // Filtra os resultados pelo nome do aluno
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                String quizName = rs.getString("quiz_name");
                String totalScore = rs.getString("totalScore");
                results.add(new String[]{quizName, totalScore});
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return results;
    }


    public static void saveResult(int user_id, String quizName, int totalScore) {
        String sql = "INSERT INTO results (user_id, quiz_name, totalScore) VALUES (?, ?, ?)";
    
        try (Connection conn = ConnFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, user_id);
            stmt.setString(2, quizName);
            stmt.setInt(3, totalScore);
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
