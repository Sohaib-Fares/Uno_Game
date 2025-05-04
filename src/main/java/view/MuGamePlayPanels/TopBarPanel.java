package view.MuGamePlayPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Dimension;

import javax.swing.JPanel;

import UI.Components.Buttons.MuOutlinedButton;
import UI.Components.Labels.MuLabel;
import UI.Components.Misc.MuImageIcon;
import UI.Components.Panels.MuPanel;
import controllers.NavController;

public class TopBarPanel extends JPanel {

        public TopBarPanel(NavController navController) {

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

                // Right button
                MuOutlinedButton playerButton = new MuOutlinedButton(
                                "⟲ Player1",
                                new Color(229, 5, 0),
                                Color.WHITE,
                                15,
                                135,
                                35);

                MuPanel rightPanel = new MuPanel();
                rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 28));
                rightPanel.setPreferredSize(new Dimension(160, 90));
                rightPanel.setOpaque(false);
                rightPanel.add(playerButton);

                // add components to top bar panel
                add(leftPanel, BorderLayout.WEST);
                add(imagePanel, BorderLayout.CENTER);
                add(rightPanel, BorderLayout.EAST);
        }
}
