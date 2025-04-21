package AppService.Game.Screens;

import AppService.OurUtils.Utils;

public class GameOverScreen {
    public GameOverScreen() {
    }

    public static void showGameOver(String winnerName) {
        Utils.clearScreen();
        for (int i = 0; i < 4; i++) {
            System.out.println("                ____                         ___                 \n" + //
                    "               / ___| __ _ _ __ ___   ___   / _ \\__   _____ _ __ \n" + //
                    "              | |  _ / _` | '_ ` _ \\ / _ \\ | | | \\ \\ / / _ \\ '__|\n" + //
                    "              | |_| | (_| | | | | | |  __/ | |_| |\\ V /  __/ |   \n" + //
                    "               \\____|\\__,_|_| |_| |_|\\___|  \\___/  \\_/ \\___|_|  ");
            Utils.waitFor(900);
            Utils.clearScreen();
            Utils.waitFor(600);
        }
        Utils.clearScreen();

        Utils.printTable("Winner", "And the winner is ....");
        Utils.waitFor(900);

        Utils.clearScreen();

        Utils.printTable("Winner", winnerName);
        Utils.pauseForUser();

    }
}
