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
}
