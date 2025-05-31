package IBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la contraseña de MySQL: ");
        String password = scanner.nextLine();

        String jdbcURL = "jdbc:mysql://localhost:3306/proyectoBD";
        String username = "root";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            System.out.println("¡Conexión exitosa a MySQL!");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos:");
            e.printStackTrace();
        }

        scanner.close();
    }
}
