package IBD;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

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
        
       
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        int opcion = 0;
        do {
            System.out.println("Menú de opciones:");
            System.out.println("1. Insertar Padrino");
            System.out.println("2. Eliminar Donante");
            System.out.println("3. Listar Padrinos y Programas");
            System.out.println("4. Total Aportes Mensuales de un Programa");
            System.out.println("5. Listar Donantes que Aportan a Más de Dos Programas");
            System.out.println("6. Listar Donantes Mensuales y Medios de Pago");
            System.out.println("7. Salir");

            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            Funcionalidades funcionalidades = new Funcionalidades();
                       
            switch(opcion){
                case 1: funcionalidades.insertarPadrino(connection, "1007", "nom", "nom2", "codp", new Date(System.currentTimeMillis()), "nom3", "nom4", "nom5");break;
                case 2: funcionalidades.eliminarUnDonante("1001", connection);break;
                case 3: funcionalidades.listarPadrinosYProgramas(connection);break;
                case 4: funcionalidades.totalAportesMensuales(connection); break;
                case 5: funcionalidades.listarDonantesMasDeDosProgramas(connection); break;
                case 6: funcionalidades.listarDonantesMensualesYMediosPago(connection);break;
                case 7:
                    System.out.println("Saliendo del programa...");
                    salir = true; 
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
                    break;  
   
            }

        } while (!salir);

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
