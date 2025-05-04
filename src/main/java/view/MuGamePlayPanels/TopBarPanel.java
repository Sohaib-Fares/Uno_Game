package view.MuGamePlayPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;

import UI.Components.Buttons.MuOutlinedButton;
import UI.Components.Labels.MuLabel;
import UI.Components.Misc.MuImageIcon;
import UI.Components.Panels.MuPanel;
import controllers.GameController;
import controllers.NavController;
import model.PlayerModel.Player;
import model.PlayerModel.PlayerConfig;

public class TopBarPanel extends JPanel {

        private GameController gameController;
        private NavController navController;
        private List<PlayerConfig> playerConfigs;
        private MuOutlinedButton currentPlayerButton; // Added to store reference

        public TopBarPanel(NavController navController, GameController gameController, List<PlayerConfig> playerConfigs) {
                this.navController = navController;
                this.gameController = gameController;
                this.playerConfigs = playerConfigs;

                setBackground(new Color(0x80461b));
                setPreferredSize(new Dimension(1000, 90));
                setLayout(new BorderLayout());
                setOpaque(true);

                // menu button
                MuOutlinedButton menuButton = new MuOutlinedButton(
                                "← Menu",
                                new Color(255, 221, 0),
                                Color.BLACK,
                                15,
                                100,
                                35,
                                1,
                                Color.BLACK);
                menuButton.addActionListener(e -> navController.showGameSetup());

                MuPanel leftPanel = new MuPanel();
                leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 28));
                leftPanel.setPreferredSize(new Dimension(160, 90));
                leftPanel.setOpaque(false);
                leftPanel.add(menuButton);

                // Center icon
                MuLabel imagelabel;
                MuImageIcon imageicon = new MuImageIcon(
                                "src/main/resources/assets/JUNO.png");
                Image scaledImage = imageicon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                imagelabel = new MuLabel();
                imagelabel.setIcon(new MuImageIcon(scaledImage));
                imagelabel.setOpaque(false);

                MuPanel imagePanel = new MuPanel();
                imagePanel.setLayout(new FlowLayout());
                imagePanel.setOpaque(false);
                imagePanel.add(imagelabel);

                // Right buttons panel
                MuPanel rightPanel = new MuPanel();
                rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 28));
                rightPanel.setPreferredSize(new Dimension(250, 90)); // Made wider to fit both buttons
                rightPanel.setOpaque(false);

                // Players count button
                String playerInfoText = playerConfigs.size() + " Players";
                MuOutlinedButton playerButton = new MuOutlinedButton(
                                playerInfoText,
                                new Color(229, 5, 0),
                                Color.WHITE,
                                15,
                                135,
                                35);

                // Current player turn button
                currentPlayerButton = new MuOutlinedButton(
                                "Current Turn: Player",
                                new Color(0, 171, 240),  // Blue
                                Color.WHITE,
                                15,
                                185,
                                35);

                rightPanel.add(playerButton);
                rightPanel.add(currentPlayerButton);

                // add components to top bar panel
                add(leftPanel, BorderLayout.WEST);
                add(imagePanel, BorderLayout.CENTER);
                add(rightPanel, BorderLayout.EAST);
        }

        /**
         * Updates the display to show the current player's turn
         * @param player The current player
         */
        public void updateCurrentPlayer(Player player) {
                if (player != null && currentPlayerButton != null) {
                        String playerName = player.getName();
                        currentPlayerButton.setText("Current Turn: " + playerName);
                        
                        // Highlight if it's the human player's turn (first player)
                        if (gameController.getPlayers().indexOf(player) == 0) {
                                currentPlayerButton.setBackground(new Color(0, 171, 240)); // Blue
                        } else {
                                currentPlayerButton.setBackground(new Color(229, 5, 0)); // Red for bot turns
                        }
                        
                        revalidate();
                        repaint();
                }
        }
}
