package UI.Components.Frames;

import javax.swing.*;

import UI.Components.Buttons.MuFilledButton;
import UI.Components.Buttons.MuOutlinedButton;
import UI.Components.Labels.MuCircleLabel;
import UI.Constatnts.MuColors;
import java.awt.*;
import java.awt.geom.Point2D;

public class MuMenuPanel extends JPanel {

    public MuMenuPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();

                g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

                Point2D center = new Point2D.Float(width / 2f, height / 2f);
                float radius = 500f;
                float[] dist = { 0.0f, 0.5f, 1.0f };
                Color[] colors = { MuColors.MuRed.darker(), MuColors.MuRed.brighter(),
                        MuColors.MuRed.brighter().brighter() };
                RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);

                g2d.setPaint(p);
                g2d.fillRect(0, 0, width, height);
            }
        };
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.RED);

        ImageIcon originalIcon = new ImageIcon("src/main/java/UI/Assets/JUNO.png");
        JLabel JUNO; // Declare JLabel

        if (originalIcon.getIconWidth() == -1) {
            System.err.println("Warning: Could not load image icon at src/main/java/UI/Assets/JUNO.png");
            JUNO = new JLabel("JUNO Logo Missing"); 
            JUNO.setHorizontalAlignment(JLabel.CENTER); 
            JUNO.setForeground(Color.WHITE);
        } else {
            // Define desired size
            int desiredWidth = 150; // Example width
            int desiredHeight = 100; // Example height

            // Scale the image
            Image scaledImage = originalIcon.getImage().getScaledInstance(desiredWidth, desiredHeight,
                    Image.SCALE_SMOOTH);

            // Create a new ImageIcon from the scaled image
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Create the JLabel with the scaled icon
            JUNO = new JLabel(scaledIcon);
            // Center the image within the JLabel's area if the JLabel is larger
            JUNO.setHorizontalAlignment(JLabel.CENTER);
            JUNO.setVerticalAlignment(JLabel.CENTER);
        }
        topPanel.add(JUNO, BorderLayout.CENTER);

        int desiredWidth = 150; // Example width
        int desiredHeight = 150; // Example height, maintain aspect ratio if needed

        // Scale the image
        Image scaledImage = originalIcon.getImage().getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);

        // Create a new ImageIcon from the scaled image
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Create the JLabel with the scaled icon
        JUNO = new JLabel(scaledIcon);
        // Center the image within the JLabel's area if the JLabel is larger
        JUNO.setHorizontalAlignment(JLabel.CENTER);
        JUNO.setVerticalAlignment(JLabel.CENTER);
        JUNO.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        topPanel.add(JUNO);

        JLabel subtitleLabel = new JLabel("JUNO Game", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Lato", Font.BOLD, 16));
        subtitleLabel.setForeground(Color.WHITE);
        topPanel.add(subtitleLabel, BorderLayout.SOUTH);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        MuFilledButton startButton = new MuFilledButton("Start Game", Color.RED, Color.WHITE, 16, 300,50, 1, Color.black);
        MuFilledButton howToPlayButton = new MuFilledButton("How to Play", new Color(255, 204, 102), Color.BLACK, 16, 300, 50, 1, Color.black );
        MuFilledButton exitButton = new MuFilledButton("Exit", Color.GRAY, Color.WHITE, 16,300 ,50, 1, Color.black);

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        howToPlayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(howToPlayButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalStrut(40));

        JPanel circlePanel = new JPanel();
        circlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        circlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        circlePanel.add(new MuCircleLabel(MuColors.MuRed));
        circlePanel.add(new MuCircleLabel(MuColors.MuYellow));
        circlePanel.add(new MuCircleLabel(MuColors.MuGreen));
        circlePanel.add(new MuCircleLabel(MuColors.MuBlue));
        circlePanel.setBackground(Color.WHITE);

        circlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, circlePanel.getPreferredSize().height));

        bottomPanel.add(buttonPanel);
        bottomPanel.add(circlePanel);

        add(topPanel);
        add(bottomPanel);
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.white, 10, true));
    }
}