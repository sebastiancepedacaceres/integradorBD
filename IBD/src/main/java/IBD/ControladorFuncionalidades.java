package IBD;

public class ControladorFuncionalidades {

    // Esta funcion primero se fija si el donante existe en la base de datos
    //si lo eliminar devuelve true, 
    // si no existe o hubo un error devuelve false


    public static boolean eliminarDonanteEnBD(String dni) {
     
        return true; 
    }

    public static double ConsultarAportesMensualesPrograma(String nombrePrograma) {
        
        //chequear si existe nombrePrograma en la base de datos

        //ejecutar una consulta
        return 0.0; //devuelve el resultado dela query select * ....
    }

    //ejemplo de una funcion 
     /*
     * public static void mostrarAlumnos() {
        String sql = "SELECT id, nombre, edad FROM alumnos";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");

                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Edad: " + edad);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     */
}
