package PROG06;

import PROG06.DNIException;
import java.util.Scanner;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Aplicación principal (PROG06)
 *
 * @author Key
 */
public class AplicacionClientes {

    public static void main(String[] args) throws FileNotFoundException, DNIException {

        /* Declaración e inicialización de un objeto
        con el objeto de entrada estándar predefinido*/
        Scanner sc = new Scanner(System.in);

        // Declaración de variables
        boolean salir = false;
        String NIF, nombre, tlfn, direccion;
        double deuda;
        int opcion;

        // Instanciación de la clase File
        File fichero = new File("clientes.dat");

        // Declaración de la clase Cliente
        Cliente c;

        // Bucle 
        while (!salir) {

            System.out.println("1. Añadir cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar clientes");
            System.out.println("4. Borrar cliente");
            System.out.println("5. Borrar fichero de clientes completamente");
            System.out.println("6. Salir de la aplicación");

            // Controlando las excepciones con try/catch
            try {
                System.out.println("Escribe una opción");
                opcion = Integer.parseInt(sc.nextLine());

                // menú
                switch (opcion) {
                    case 1: // Añadir cliente

                        if (!fichero.exists()) {
                            fichero.createNewFile();
                        }

                        // Preguntas para añadir atributos al cliente
                        System.out.println("Introduce un DNI");
                        NIF = sc.nextLine();

                        validarDNI(NIF);

                        System.out.println("Introduce el nombre");
                        nombre = sc.nextLine();

                        System.out.println("Introduce un telefono");
                        tlfn = sc.nextLine();

                        System.out.println("Introduce una direccion");
                        direccion = sc.nextLine();

                        System.out.println("Introduce la deuda");
                        deuda = Double.parseDouble(sc.nextLine());

                        // Creamos el cliente
                        c = new Cliente(NIF, nombre, tlfn, direccion, deuda);

                        // Declaración de la clase ObjectOutputStream (Añadir objeto en fichero)
                        ObjectOutputStream oos;

                        if (fichero.length() == 0) {
                            oos = new ObjectOutputStream(new FileOutputStream(fichero));
                        } else {
                            oos = new MiObjectOutputStream(new FileOutputStream(fichero, true));
                        }

                        oos.writeObject(c);

                        System.out.println("Se ha añadido correctamente");

                        break;
                    case 2: // Listar clientes

                        if (fichero.exists()) {

                            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero));

                            //Saldrá cuando no haya mas datos que leer, EOFException
                            while (true) {

                                //leo el objeto
                                c = (Cliente) ois.readObject();

                                //lo muestro
                                System.out.println(c.toString());

                            }
                        } else {
                            System.out.println("Debes crear el fichero");
                        }
                        break;

                    case 3: // Buscar clientes

                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero));

                        Object aux = ois.readObject();

                        System.out.println("Introduce un NIF");
                        NIF = sc.nextLine();

                        // Mientras haya objetos
                        while (aux != null) {

                            if (aux.toString().contains(NIF)) {
                                System.out.println(aux);
                            }
                            aux = ois.readObject();

                        }
                        ois.close();
                        break;

                    case 4: // Borrar cliente

                        ObjectInputStream objIs = new ObjectInputStream(new FileInputStream(fichero));
                        ObjectOutputStream objOs = new ObjectOutputStream(new FileOutputStream(fichero));
                        Object obj = objIs.readObject();

                        System.out.println("Introduce un NIF");
                        NIF = sc.nextLine();

                        // Mientras haya objetos
                        while (obj != null) {
                            if (obj.toString().contains(NIF)) {
                                System.out.println(obj);
                                objOs.writeObject(null);
                            }

                            obj = objIs.readObject();

                        }

                        objIs.close();
                        break;

                    case 5: // Borrar fichero de clientes completamente
                        if (fichero.exists()) {
                            fichero.delete();
                            System.out.println("El fichero " + fichero.getName() + " ha sido borrado");
                        } else {
                            System.out.println("El fichero " + fichero.getName() + " no existe");
                        }
                        break;
                    case 6: // Salir de la aplicación
                        salir = true;
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.next();
            } catch (EOFException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AplicacionClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

// Método que valida el DNI
    public static void validarDNI(String DNI) throws DNIException {

        //Comprobamos la longitud del dni
        if (!(DNI.length() >= 8 && DNI.length() <= 9)) {
            throw new DNIException(DNIException.LONGITUD_NO_CORRECTA);
        }

        //saco la parte numerica
        String parte_numerica = DNI.substring(0, DNI.length() - 1);

        //Aqui guardare el dni
        int numeroDNI = 0;

        try {
            //Lo transformo en un numero
            //Puede saltar la excepcion
            numeroDNI = Integer.parseInt(parte_numerica);
        } catch (NumberFormatException e) {
            throw new DNIException(DNIException.PARTE_NUMERICA_NO_CORRECTA);
        }

        //
        char letra = DNI.substring(DNI.length() - 1, DNI.length()).toUpperCase().charAt(0);

        if (!(letra >= 'A' && letra <= 'Z')) {
            throw new DNIException(DNIException.PARTE_LETRA_NO_CORRECTA);
        }

        //Ya hemos validado el formato
        final int DIVISOR = 23;

        char letrasNIF[] = {'T', 'R', 'W', 'A', 'G', 'M', 'Y',
            'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z',
            'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

        int resto = numeroDNI % DIVISOR;

        String nuevoDNI = numeroDNI + "" + letrasNIF[resto];

        if (DNI.startsWith("0")) {
            nuevoDNI = "0" + nuevoDNI;
        }

        if (!(nuevoDNI.equals(DNI))) {
            throw new DNIException(DNIException.FORMATO_NO_CORRECTO);
        }

    }

}
