package IBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class App {
    public static void main(String[] args) {
        // Carga la configuración de la base de datos desde el archivo config.properties
        Properties props = cargarConfiguracion();
        if (props == null) throw new RuntimeException("No se pudo cargar la configuración de la base de datos");

        // Obtiene los datos de conexión desde las propiedades
        String jdbcURL = props.getProperty("db.url"); // URL de la base de datos
        String username = props.getProperty("db.user"); // Usuario de la base de datos
        String password = props.getProperty("db.password"); // Contraseña de la base de datos

        // Intenta establecer la conexión con la base de datos
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            System.out.println("¡Conexión exitosa a MySQL!");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos:");
            e.printStackTrace();
        }
    }

    /**
     * Lee el archivo de configuración y carga las propiedades de conexión.
     * @return Properties con los datos de conexión, o null si hay error.
     */
    private static Properties cargarConfiguracion() {
        Properties props = new Properties();
        // Intenta abrir el archivo config.properties desde resources
        try (InputStream input = App.class.getClassLoader().getResourceAsStream("IBD/config.properties")) {
            if (input == null) {
                System.out.println("No se encontró el archivo de configuración.");
                return null;
            }
            props.load(input); // Carga las propiedades desde el archivo
        } catch (Exception e) {
            System.out.println("Error al leer el archivo de configuración:");
            e.printStackTrace();
            return null;
        }
        return props; // Devuelve las propiedades cargadas
    }
}
