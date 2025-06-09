package IBD;

public class Funcionalidades {

    //insertarPadrino
    //recibe datos del padrino y los inserta en la base de datos
    // Verificacion de datos?
    //
    public void insertarPadrino(String dni, String nomPadrino, String direccion, String codPostal, 
                                String fechaNacimiento, String telefono, String celular, String usuarioFacebook) {
       
              
    }
    //verificarDatosPadrino por cantidad de caracteres mas que nada  pero vemos
    public void verificarDatosPadrino(String dni, String nomPadrino, String direccion, String codPostal, 
                                String fechaNacimiento, String telefono, String celular, String usuarioFacebook) {
       
    }

    //EliminarUnDonante
    public void eliminarUnDonante(String dni) throws IllegalArgumentException {
        if (dni == null || dni.isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede ser nulo o vacío.");
        }

        // Esta funcion primero se fija si el donante existe en la base de datos

        if (!ControladorFuncionalidades.eliminarDonanteEnBD(dni)) {
            System.out.println("No existe un donante con el DNI: " + dni);
        }
      
        System.out.println("Donante con DNI " + dni + " eliminado correctamente.");
    }

    //Listar Padrinos y programas: (Dni,Apellido,Nombre, nombre_programa,monto, frecuencia de aporte)
    public void listarPadrinosYProgramas() {

        
    }

    // Devolver el total de aportes mensuales de un programa
    public double totalAportesMensuales(String nombrePrograma) {
        
       return ControladorFuncionalidades.ConsultarAportesMensualesPrograma(nombrePrograma);
        
    }

    // Listar donantes que aportan a mas de dos programas
    public void listarDonantesMasDeDosProgramas() {


        
    }

    //Listar donantes con aportes mensuales y los datos de los medios de pago
    public void listarDonantesMensualesYMediosPago() {
        
    }

   
}
