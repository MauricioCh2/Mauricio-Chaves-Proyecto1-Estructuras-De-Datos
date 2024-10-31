package org.example.utilities;

import java.util.Scanner;

public class ingresoDatos {
    private static final Scanner scanner = new Scanner(System.in);


    //Metodo para recivitr booleanos desde consola y validarlos
    public static boolean validBoolean(String mensaje) {
        boolean  valid;
        do{
            print.print(mensaje);
            String input = scanner.nextLine().trim().toLowerCase();
            valid = input.equals("si") || input.equals("s")|| input.equals("1") || input.equals("true") || input.equals("yes")|| input.equals("y") ||  input.equals("0") || input.equals("false") || input.equals("no")|| input.equals("n");
            if (!valid){
                print.printlnColor(print.RED,"DATOS ERRONEOS: Por favor ingrese una respuesta valida ");
            }else {
                valid = input.equals("si") || input.equals("s")|| input.equals("1") || input.equals("true") || input.equals("yes")|| input.equals("y");
                return valid;
            }

        }while (true);

    }

    //Metodo para ingresar strings desde consola y validarlos (no vacios, no caracteres especiales y no numeros)
    public static String validString(String mensaje) {
        String input;
        boolean valid;
        do {
            print.print(mensaje);
            input = scanner.nextLine().trim();
            valid = input.matches("^[a-zA-ZñÑ\\s]*$");
            if (!valid|| input.isEmpty()) {
                print.printlnColor(print.RED, "DATOS ERRONEOS: No se permiten caracteres especiales o números");
            }
        } while (!valid);
        return input;
    }

    //Metodo paraingresar opciones desde consola y validarlos (no vacios, no caracteres especiales, no letras y no mas quelo solicitado)
    public static int validOpcionNum(int max, String mensaje) {
        int input = -1;
        boolean valid;
        do {
            print.print(mensaje);
            String userInput = scanner.nextLine().trim();
            try {
                input = Integer.parseInt(userInput);
                valid = input > 0 && input <= max;
                if (!valid) {
                    print.printlnColor(print.RED, "DATOS ERRONEOS: Por favor ingrese una opción válida");
                }
            } catch (NumberFormatException e) {
                print.printlnColor(print.RED, "DATOS ERRONEOS: Por favor ingrese un número válido");
                valid = false;
            }
        } while (!valid);
        return input;
    }

}
