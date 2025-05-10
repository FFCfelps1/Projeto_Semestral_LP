package src.connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static Connection connection;

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    throw new SQLException("Arquivo 'db.properties' não encontrado no classpath.");
                }

                Properties props = new Properties();
                props.load(input);

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                connection = DriverManager.getConnection(url, user, password);
                System.out.println("✅ Conexão com o MySQL estabelecida.");
            } catch (SQLException e) {
                System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
                throw e; // Relança a exceção para o chamador
            } catch (Exception e) {
                System.err.println("Erro inesperado ao conectar com o banco de dados: " + e.getMessage());
                e.printStackTrace();
                throw new SQLException("Erro ao conectar com o banco de dados.", e);
            }
        }
        return connection;
    }
}