package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/codigocentral";
    private static final String USERNAME = "JavaUser";
    private static final String PASSWORD = "123456789";
    private static Connection connection = null;

    // Método para obtener la conexión
    public static Connection getConnection() {
        try {
            // Si la conexión no está establecida o está cerrada, establecer una nueva conexión
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Conexión exitosa a la base de datos");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return connection;
    }

    public static void main(String[] args) {
        // Obtener la conexión
        Connection connection = ConexionBD.getConnection();
    }
}