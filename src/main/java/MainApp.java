import java.io.IOException;
import AppService.Game.GameService;

public class MainApp {
    public static void main(String[] args) throws IOException {
        GameService service = new GameService();
        service.showGameMenu();
    }
}
