package AppTools.OurUtils;

import java.io.IOException;

public class Utils {

    // Clears the console
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }

    // TODO: VERY USEFUL
    // Used to show some messages before going back to the main menu, so it forces
    // the user to hit enter.(Run the GameService to understand more)
    public static void pauseForUser() {
        System.out.println("\n\n(Press 'Enter' to continue...)");
        try {
            System.in.read();
        } catch (IOException e) {
            System.out.println("Error waiting for user input.");
        }
    }

    public static void waitFor(int milSec) {
        try {
            Thread.sleep(milSec);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void printTable(String title, String info) {
        int TOTAL_LENGHT = 98;

        int titleSize = title.length();
        int infoSize = info.length();

        int titlePadding = (TOTAL_LENGHT - titleSize) / 2;
        int infoPadding = (TOTAL_LENGHT - infoSize + 1) / 2;

        System.out.println("+" + "-".repeat(titlePadding - 1) + " " + title + " "
                + "-".repeat((titleSize % 2) == 0 ? (titlePadding - 1) : (titlePadding)) + "+\n"
                + "|" + " ".repeat(infoPadding - 2) + "* " + info + " *"
                + " ".repeat((infoSize % 2) == 0 ? (infoPadding - 2) : (infoPadding - 3)) + "|\n"
                + "+" + "-".repeat(TOTAL_LENGHT) + "+");
    }
}
