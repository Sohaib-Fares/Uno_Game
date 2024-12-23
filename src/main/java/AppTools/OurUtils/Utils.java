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

    // TODO: Revise it, COULD-REMOVE in the end
    // Used to show some messages before going back to the main menu, so it forces
    // the user to hit enter.(Run the GameService to understand more)
    public static void pauseForUser() {
        System.out.println("\n(Press 'Enter' to continue...)");
        try {
            System.in.read();
        } catch (IOException e) {
            System.out.println("Error waiting for user input.");
        }
    }
}
