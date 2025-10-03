package academy.util;

import java.util.Scanner;

public class InputUtils {

    private InputUtils() {}

    public static int safeReadInt(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        }
    }
}
