package view;

import javax.swing.*;

import UI.Components.Buttons.MuFilledButton;
import UI.Components.Dialogs.MuJOptionPane;
import UI.Components.Labels.MuCircleLabel;
import UI.Components.Misc.MuBox;
import UI.Components.Panels.MuHeaderPanel;
import UI.Components.Panels.MuVerticalListPanel;
import UI.Constatnts.MuColors;
import controllers.NavController;

import java.awt.*;
import java.awt.event.ActionEvent; // Import ActionEvent
import java.awt.event.ActionListener; // Import ActionListener
import java.awt.geom.Point2D;

public class MuMenuPanel extends JPanel {

    public MuMenuPanel(NavController navController) { // Accept MuMainFrame in constructor

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(550, 700)); // Keep preferred size

        // --- Top Panel (Logo) ---
        MuHeaderPanel topPanel = new MuHeaderPanel();
        Point2D relativeCenter = new Point2D.Float(0.5f, 0.5f); // Center point relative to size

        float radius = 300f;
        float[] dist = { 0.0f, 1.0f };
        Color[] colors = {
                MuColors.MuRed.brighter(), // Center bright red
                MuColors.MuRed.darker() // Outer deep red
        };
        topPanel.setRelativeRadialGradient(relativeCenter, radius, dist,
                colors);

        topPanel.setPreferredSize(new Dimension(250 + 50, 250 + 50)); // Example: 250x250 logo + 25 padding each side
        topPanel.setMinimumSize(new Dimension(200 + 50, 200 + 50)); // Example: 250x250 logo + 25 padding each side
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // Set preferred size based on desired logo size + padding
        topPanel.setContentIcon("src/main/resources/assets/JUNO.png", 250, 250);

        // --- Middle Panel (Buttons) ---
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setBackground(Color.WHITE);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20)); // Adjust padding as needed
        middlePanel.setMinimumSize(new Dimension(260, 260));

        MuVerticalListPanel buttonPanel = new MuVerticalListPanel();
        // Create Buttons
        MuFilledButton startButton = new MuFilledButton("Start Game", MuColors.MuRed, Color.WHITE, 20, 200, 50);
        MuFilledButton howToPlayButton = new MuFilledButton("How to Play", MuColors.MuYellow, Color.BLACK, 20, 200, 50);
        MuFilledButton exitButton = new MuFilledButton("Exit", Color.GRAY.darker(), Color.WHITE, 20, 200, 50);

        // Set maximum size to allow buttons to expand horizontally if needed, but
        // constrained vertically
        Dimension buttonMaxSize = new Dimension(300, 50); // Max width 300, fixed height 50
        startButton.setMaximumSize(buttonMaxSize);
        howToPlayButton.setMaximumSize(buttonMaxSize);
        exitButton.setMaximumSize(buttonMaxSize);

        // Center align buttons themselves
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        howToPlayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add Buttons to buttonPanel
        buttonPanel.add(startButton);
        buttonPanel.add(howToPlayButton);
        buttonPanel.add(exitButton);

        buttonPanel.setMinimumSize(new Dimension(200, 200));

        middlePanel.add(buttonPanel);
        middlePanel.add(MuBox.createVerticalGlue()); // Pushes buttons up if extra space

        // --- Bottom Panel (Circles) ---
        JPanel circlePanel = new JPanel();
        circlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10)); // Added vertical gap
        circlePanel.setBackground(Color.WHITE);
        circlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Padding top/bottom

        circlePanel.add(new MuCircleLabel(MuColors.MuRed));
        circlePanel.add(new MuCircleLabel(MuColors.MuYellow));
        circlePanel.add(new MuCircleLabel(MuColors.MuGreen));
        circlePanel.add(new MuCircleLabel(MuColors.MuBlue));

        // Set preferred and maximum height for the circle panel
        int circlePanelHeight = 80; // Adjust as needed
        circlePanel.setPreferredSize(new Dimension(100, circlePanelHeight)); // Width is flexible due to FlowLayout
        circlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, circlePanelHeight));

        // --- Add Panels to MuMenuPanel ---
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(circlePanel, BorderLayout.SOUTH);

        setOpaque(false); // Make this panel transparent
        setBorder(BorderFactory.createLineBorder(Color.white, 3, true)); // Keep border

        // --- Add Action Listeners ---
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navController.showGameSetup();
                // setup view
                System.out.println("Start Game Clicked - Implement GameSetupPanel"); // Placeholder
            }
        });

        howToPlayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to how-to-play view

                navController.showHowToPlay();
                System.out.println("How to Play Clicked - Implement HowToPlayPanel"); // Placeholder
            }
        });

        // exitButton.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // // Ask for confirmation before exiting
        // int confirmed = MuJOptionPane.showConfirmDialog(mainFrame, "Are you sure you
        // want to exit?",
        // "Exit Confirmation",
        // JOptionPane.YES_NO_OPTION);

        // if (confirmed == MuJOptionPane.YES_OPTION) {
        // mainFrame.dispose(); // Close the window
        // System.exit(0); // Terminate the application
        // }
        // }
        // });

        exitButton.addActionListener(e -> System.exit(0));
    }
}