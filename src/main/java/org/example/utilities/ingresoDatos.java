package org.example.utilities;

import java.util.Scanner;

public class ingresoDatos {
    private static final Scanner scanner = new Scanner(System.in);

    //Metodo para ingresar Strings desde consola
    public static String leerString(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    //Metodo para recivir booleanos desde consola
    public static boolean leerBoolean(String mensaje) {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("si") || input.equals("s")|| input.equals("1") || input.equals("true") || input.equals("yes")|| input.equals("y");
    }

    //Metodo para recivitr booleanos desde consola y validarlos
    public static boolean validBoolean(String mensaje) {
        boolean  valid = false;
        do{
            print.print(mensaje);
            String input = scanner.nextLine().trim().toLowerCase();
            valid = input.equals("si") || input.equals("s")|| input.equals("1") || input.equals("true") || input.equals("yes")|| input.equals("y") || input.equals("no") || input.equals("n")|| input.equals("0") || input.equals("false") || input.equals("no")|| input.equals("n");
            if (!valid){
                print.printlnColor(print.RED,"Por favor ingrese una respuesta valida ");
            }else {
                valid = input.equals("si") || input.equals("s")|| input.equals("1") || input.equals("true") || input.equals("yes")|| input.equals("y");
                return valid;
            }

        }while (!valid);
        return valid;
    }

    //Metodo para ingresar strings desde consola y validarlos (no vacios, no caracteres especiales y no numeros)
    public static String validString(String mensaje) {
        String input = "";
        boolean valid = false;
        do {
            print.print(mensaje);
            input = scanner.nextLine().trim();
            valid = input.matches("^[a-zA-ZñÑ\\s]*$");
            if (!valid|| input.isEmpty()) {
                print.printlnColor(print.RED, "No se permiten caracteres especiales o números");
            }
        } while (!valid);
        return input;
    }

}
