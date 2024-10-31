package org.example.utilities;

public class print {

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void printColor(String colorCode, Object message) {
        System.out.print(colorCode + message + RESET);
    }
    public static void printlnColor(String colorCode, Object message) {
        System.out.println(colorCode + message + RESET);
    }

    public static void yellowLine() {
        printlnColor(YELLOW, "------------------------------------------------------------------------");
    }

    public static void print(Object message) {
        System.out.print(message);
    }

    public static void println(Object message) {
        System.out.println(message);
    }


}
