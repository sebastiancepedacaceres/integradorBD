package IBD;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Funcionalidades {

    //insertarPadrino
    //recibe datos del padrino y los verifica antes de insertarlos en la base de datos
    public void insertarPadrino(Connection conn){

        String dni;
        String nomPadrino; 
        String direccion;
        String codPostal; 
        java.sql.Date fechaNacimiento;
        String telefono;
        String celular; 
        String usuarioFacebook;

        Scanner scanner = new Scanner(System.in);
        
        
        System.out.println("Ingrese el dni del padrino que desea registrar: ");
        dni = scanner.nextLine();
        while(dni.length() > 10 ){
            System.out.println("El DNI excede los 10 caracteres permitidos. Por favor, ingrese un DNI valido: ");
            dni = scanner.nextLine();
        }

        System.out.println("Ingrese nombre y apellido del padrino: ");
        nomPadrino = scanner.nextLine();
        while(nomPadrino.length() > 50 ){
            System.out.println("El nombre del padrino excede los 50 caracteres permitidos. Por favor, ingrese un nombre valido: ");
            nomPadrino = scanner.nextLine();
        }

        System.out.println("Ingrese la direccion: ");
        direccion = scanner.nextLine();
        if(direccion.length() > 40 || direccion.isEmpty()){
            direccion = null;
        }

        System.out.println("Ingrese el Codigo Postal: ");
        codPostal = scanner.nextLine();
        if(codPostal.length() > 10 || codPostal.isEmpty()){
            codPostal = null;
        }

        System.out.println("Ingrese el telefono: ");
        telefono = scanner.nextLine();
        if(telefono.isEmpty() || telefono.length()>20){
            telefono = null;
        }
        

        System.out.println("Ingrese el celular: ");
        celular = scanner.nextLine();
        while(celular.isEmpty() || celular.length()>20){
            System.out.println("El celular no puede estar vacio  o exceder los 20 caracteres. Por favor, ingrese un numero valido: ");
            celular = scanner.nextLine();
        }

        System.out.println("Ingrese el usuario de Facebook: ");
        usuarioFacebook = scanner.nextLine();
        if(usuarioFacebook.isEmpty() || usuarioFacebook.length()>50){
            usuarioFacebook = null;
        }

        System.out.println("Ingrese dia de la fecha de nacimiento: ");
        int diaNac = scanner.nextInt();
        while(diaNac < 1 || diaNac > 31){
            System.out.println("Dia invalido. Ingrese un día entre 1 y 31: ");
            diaNac = scanner.nextInt();
        }
        System.out.println("Ingrese mes de la fecha de nacimiento: ");
        int mesNac = scanner.nextInt();
        while(mesNac < 1 || mesNac > 12){
            System.out.println("Mes invalido. Ingrese un mes entre 1 y 12: ");
            mesNac = scanner.nextInt();
        }
        System.out.println("Ingrese año de la fecha de nacimiento: ");
        int anioNac = scanner.nextInt();
        while(anioNac < 1900 || anioNac > LocalDate.now().getYear()){
            System.out.println("Año invalido. Ingrese un año entre 1900 y el año actual: ");
            anioNac = scanner.nextInt();
        }

        LocalDate localDate = LocalDate.of(anioNac, mesNac, diaNac);
        fechaNacimiento = Date.valueOf(localDate);

        insertarPadrino(conn, dni, nomPadrino, direccion, codPostal, fechaNacimiento, telefono, celular, usuarioFacebook);
        
    }

    //inserta un padrino en la base de datos
    //verifica si el padrino ya existe en la base de datos
    //si el padrino ya existe, no lo inserta y muestra un mensaje
    //si el padrino no existe, lo inserta 
    public void insertarPadrino(Connection conn, String dni, String nomPadrino, String direccion, String codPostal, java.sql.Date fechaNacimiento, String telefono, String celular, String usuarioFacebook) {

        String sqlVerificar = "SELECT dni FROM padrinos WHERE dni = ?";
        String sql = "INSERT INTO padrinos (dni, nom_padrino, direccion, cod_Postal, fecha_nacimiento, telefono, celular, facebook) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement psVerificar = conn.prepareStatement(sqlVerificar);
            psVerificar.setString(1, dni);
            ResultSet rs = psVerificar.executeQuery();
            if (rs.next()) {
                System.out.println("Ya existe un padrino con ese DNI.");
                return;
            } else {
                psVerificar = conn.prepareStatement(sql);

                psVerificar.setString(1, dni);
                psVerificar.setString(2, nomPadrino);
                psVerificar.setString(3, direccion);
                psVerificar.setString(4, codPostal);
                psVerificar.setDate(5, fechaNacimiento);
                psVerificar.setString(6, telefono);
                psVerificar.setString(7, celular);
                psVerificar.setString(8, usuarioFacebook);

                int filasAfectadas = psVerificar.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Padrino insertado correctamente.");
                }


            }
        } catch (SQLException e) {
            System.out.println("Error al verificar el DNI: " + e.getMessage() + "el dni ya existe");
        } catch (Exception e) {
                System.out.println("Error al insertar el padrino: " + e.getMessage());
        }         
    }

    //chequea si el dni cumple las restricciones de la base de datos
    public void eliminarUnDonante(Connection conn){
        System.out.println("Ingrese el dni del donante que desea eliminar: ");
        Scanner scanner = new Scanner(System.in);
        String dni = scanner.nextLine();

        eliminarUnDonante(dni, conn);

    }

    //elimina un donante de la base de datos
    //verifica si el donante existe en la base de datos
    //si el donante no existe, muestra un mensaje
    //si el donante existe, lo elimina
    public void eliminarUnDonante(String dni, Connection conn) throws IllegalArgumentException {
        if (dni == null || dni.isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede ser nulo o vacio.");
        }
        if (dni.length() > 10) {
            System.out.println("El DNI excede los 10 caracteres permitidos.");
            return;
        }


        String sqlVerificar = "SELECT dni_donante FROM donantes WHERE dni_donante = ?";
        String sql = "DELETE FROM donantes WHERE dni_donante = ?";

        try {
            PreparedStatement psEliminar = conn.prepareStatement(sqlVerificar);
            psEliminar.setString(1, dni);
            ResultSet rs = psEliminar.executeQuery();
            if (rs.next()) {
                psEliminar = conn.prepareStatement(sql);
                psEliminar.setString(1, dni);
                int filas = psEliminar.executeUpdate();

                if (filas > 0) {
                    System.out.println("Donante eliminado correctamente.");
                } else {
                    System.out.println("Ha ocurrido un error al intentar eliminar al donante.");
                }
            } else {

                System.out.println("No existe un donante con ese DNI.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar el DNI: " + e.getMessage() + "el dni ya existe");
        } catch (Exception e) {
                System.out.println("Error al insertar el padrino: " + e.getMessage());
        }      

        // Esta funcion primero se fija si el donante existe en la base de datos
    }

    //Listar Padrinos y programas: (Dni,Apellido,Nombre, nombre_programa,monto, frecuencia de aporte)
    public void listarPadrinosYProgramas(Connection conn) {

        try{
            String sql = "SELECT dni, nom_padrino, nom_programa, monto, frecuencia  FROM padrinos INNER JOIN (SELECT * FROM aportes JOIN programas USING(id_programa)) AS ap USING(dni);";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nom_padrino");
                String nombre_programa = rs.getString("nom_programa");
                float monto = rs.getFloat("monto");
                String frecuencia = rs.getString("frecuencia");

                System.out.println("DNI: " + dni + ", Nombre del donante: " + nombre + ", Nombre del programa: " + nombre_programa + ", Monto que aporta: " + monto + ", Frecuencia de aporte: " + frecuencia);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    // Devolver de cada programa el total de aportes mensuales
    public void totalAportesMensuales(Connection conn) {

        try{
            String sql = "SELECT id_programa, nom_programa, descripcion, SUM(monto) AS aportes_totales FROM programas LEFT JOIN (SELECT * FROM aportes WHERE frecuencia='Mensual') as ap USING(id_programa) GROUP BY id_programa;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_programa");
                String nombre = rs.getString("nom_programa");
                String descripcion = rs.getString("descripcion");
                float aportesTotales = rs.getFloat("aportes_totales");

                System.out.println("ID: " + id + ", Nombre del programa: " + nombre + ", Descripcion: " + descripcion + ", Aportes totales: " + aportesTotales);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    // Listar donantes que aportan a mas de dos programas
    public void listarDonantesMasDeDosProgramas(Connection conn) {

        try{
            String sql = "SELECT p.dni, nom_padrino, cant_programas FROM padrinos p INNER JOIN (SELECT dni, COUNT(id_programa) as cant_programas FROM aportes GROUP BY dni HAVING cant_programas>2) as ap USING(dni);";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nom_padrino");
                int cantidad_programas = rs.getInt("cant_programas");

                System.out.println("DNI: " + dni + ", Nombre del donante: " + nombre + ", Cantidad de programas a los que aporta: " + cantidad_programas);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    //Listar donantes con aportes mensuales y los datos de los medios de pago
    public void listarDonantesMensualesYMediosPago(Connection conn) {

        try{
            String sql = "SELECT dni, id_programa, monto, id_mp, nom_titular, cbu, nro_cuenta, tipo, nom_banco, num_sucursal FROM (SELECT cu.cbu, id_mp, nro_cuenta, tipo, nom_banco, num_sucursal FROM cuentas cu JOIN debitosbancarios db ON(cu.cbu=db.cbu)) AS datos JOIN (SELECT dni, id_programa, monto, m.id_mp, nom_titular FROM mediospagos m INNER JOIN (SELECT dni, id_programa, monto, id_mp FROM aportes WHERE frecuencia = 'Mensual') as ap USING(id_mp)) as conDebito USING(id_mp);";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String dni = rs.getString("dni");
                int id_programa = rs.getInt("id_programa");
                float monto = rs.getFloat("monto");
                String id_mp = rs.getString("id_mp");
                String nom_titular = rs.getString("nom_titular");
                String cbu = rs.getString("cbu");
                String nro_cuenta = rs.getString("nro_cuenta");
                String tipo = rs.getString("tipo");
                String nom_banco = rs.getString("nom_banco");
                String num_sucursal = rs.getString("num_sucursal");

                System.out.println("Pago con Debito Bancario, DNI: " + dni + ", ID del programa: " + id_programa + ", Monto que aporta: " + monto + ", ID del medio de pago: " + id_mp + ", Nombre del titular: " + nom_titular + ", CBU: " + cbu + ", Numero de cuenta: " + nro_cuenta + ", Tipo de cuenta: " + tipo + ", Nombre del banco: " + nom_banco + ", Numero de sucursal: " + num_sucursal);
            }

            sql = "SELECT dni, id_programa, monto, id_mp, nom_titular, cbu, nro_cuenta, tipo, nom_banco, num_sucursal FROM (SELECT cu.cbu, id_mp, nro_cuenta, tipo, nom_banco, num_sucursal FROM cuentas cu JOIN transferencias ts ON(cu.cbu=ts.cbu)) AS datos JOIN (SELECT dni, id_programa, monto, m.id_mp, nom_titular FROM mediospagos m INNER JOIN (SELECT dni, id_programa, monto, id_mp FROM aportes WHERE frecuencia = 'Mensual') as ap USING(id_mp)) as conDebito USING(id_mp);";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String dni = rs.getString("dni");
                int id_programa = rs.getInt("id_programa");
                float monto = rs.getFloat("monto");
                String id_mp = rs.getString("id_mp");
                String nom_titular = rs.getString("nom_titular");
                String cbu = rs.getString("cbu");
                String nro_cuenta = rs.getString("nro_cuenta");
                String tipo = rs.getString("tipo");
                String nom_banco = rs.getString("nom_banco");
                String num_sucursal = rs.getString("num_sucursal");

                System.out.println("Pago con Transferencia, DNI: " + dni + ", ID del programa: " + id_programa + ", Monto que aporta: " + monto + ", ID del medio de pago: " + id_mp + ", Nombre del titular: " + nom_titular + ", CBU: " + cbu + ", Número de cuenta: " + nro_cuenta + ", Tipo de cuenta: " + tipo + ", Nombre del banco: " + nom_banco + ", Número de sucursal: " + num_sucursal);

            }

            sql = "SELECT dni, id_programa, monto, id_mp, nom_titular, fecha_vencimiento, nom_tarjeta, num_tarjeta FROM tarjetascredito JOIN (SELECT dni, id_programa, monto, m.id_mp, nom_titular FROM mediospagos m INNER JOIN (SELECT dni, id_programa, monto, id_mp FROM aportes WHERE frecuencia = 'Mensual') as ap USING(id_mp)) as conDebito USING(id_mp);";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String dni = rs.getString("dni");
                int id_programa = rs.getInt("id_programa");
                float monto = rs.getFloat("monto");
                String id_mp = rs.getString("id_mp");
                String nom_titular = rs.getString("nom_titular");
                String fecha_vencimiento = rs.getString("fecha_vencimiento");
                String nom_tarjeta = rs.getString("nom_tarjeta");
                String num_tarjeta = rs.getString("num_tarjeta");
                

                System.out.println("Pago con Tarjeta de Credito, DNI: " + dni + ", ID del programa: " + id_programa + ", Monto que aporta: " + monto + ", ID del medio de pago: " + id_mp + ", Nombre del titular: " + nom_titular + ", Fecha de vencimiento: " + fecha_vencimiento + ", Nombre en la tarjeta: " + nom_tarjeta + ", Numero de tarjeta: " + num_tarjeta);

            }





        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

   
}
