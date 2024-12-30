import AppTools.Game.GameService;

import java.io.IOException;

public class UnoApp {
    public static void main(String[] args) throws IOException {
        GameService gameService = new GameService();
        gameService.showGameMenu();
    }
}
