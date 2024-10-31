package org.example.utilities;

import java.util.Map;

public class ConsoleUtils {
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void timeOut(int seconds) {
        try {
            for (int i = seconds; i > 0; i--) {
                print.println("\rRedirigiendo en: " + i + " segundos....");
                System.out.flush();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}