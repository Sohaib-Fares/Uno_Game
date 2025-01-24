
import java.io.IOException;

import App.Game.GameService;

public class UnoApp {
    public static void main(String[] args) throws IOException {
        GameService service = new GameService();
        service.showGameMenu();
    }
}
