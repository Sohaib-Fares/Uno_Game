package App.Game.Screens;

import App.OurUtils.Utils;

public class WelcomeScreen {
    public static void showWelcomeScreen() {
        Utils.clearScreen();
        System.out.println("                            _                            _        \n" + //
                "              __      _____| | ___ ___  _ __ ___   ___  | |_ ___  \n" + //
                "              \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | __/ _ \\ \n" + //
                "               \\ V  V /  __/ | (_| (_) | | | | | |  __/ | || (_) |\n" + //
                "                \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \\__\\___/ \n" + //
                "                                                                  \n" + //
                "                          _             _   _             \n" + //
                "                         | | __ ___   _| | | |_ __   ___  \n" + //
                "                      _  | |/ _` \\ \\ / / | | | '_ \\ / _ \\ \n" + //
                "                     | |_| | (_| |\\ V /| |_| | | | | (_) |\n" + //
                "                      \\___/ \\__,_| \\_/  \\___/|_| |_|\\___/ \n" + //
                "");
        System.out.println();
        System.out.println("1. Play");
        System.out.println("2. How to Play");
        System.out.println("3. Exit");
    }
}
