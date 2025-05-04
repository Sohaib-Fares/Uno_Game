package main;

import java.io.IOException;

import javax.swing.SwingUtilities;

import controllers.GameController; // Import GameController
import controllers.NavController;
import view.MuMainFrame;

public class App {
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> {
            MuMainFrame mainFrame = new MuMainFrame();

            // 2. Create Navigation Controller
            NavController navController = new NavController(mainFrame);

            // 3. Create Game Controller (and potentially others)
            GameController gameController = new GameController(navController);

            // 4. Inject Controllers into the View
            mainFrame.setNavController(navController);
            mainFrame.setGameController(gameController); // Add method to set GameController

            // --- Instantiate other Controllers (Game, Setup) and Model here ---
            // GameState gameState = new GameState();
            // SetupController setupController = new SetupController(gameState, mainFrame,
            // navController); // May need nav
            // GameController gameController = new GameController(gameState, mainFrame,
            // navController); // May need nav

            // --- Inject other controllers into relevant view components ---
            // mainFrame.getSetupPanel().setSetupController(setupController); // Example
            // mainFrame.getGamePanel().setGameController(gameController); // Example

            // 5. Make the main frame visible
            mainFrame.pack(); // Adjust size
            mainFrame.setLocationRelativeTo(null); // Center
            mainFrame.setVisible(true);
        });

    }
}