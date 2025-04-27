package UI.Components.Frames;

import javax.swing.*;

import UI.Components.Buttons.MuFilledButton;
import UI.Constatnts.MuColors;
import java.awt.*;
import java.awt.geom.Point2D;

public class MuMenuPanel extends JPanel {

    public MuMenuPanel() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(550, 700));

        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();

                g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

                Point2D center = new Point2D.Float(width / 2f, height / 2f);
                float radius = 300f;
                float[] dist = { 0.0f, 1.0f };
                Color[] colors = {
                        MuColors.MuRed.brighter(), // Center bright red
                        MuColors.MuRed.darker() // Outer deep red
                };
                RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);

                g2d.setPaint(p);
                g2d.fillRect(0, 0, width, height);
            }
        };

        ImageIcon originalIcon = new ImageIcon("src/main/java/UI/Assets/JUNO.png");
        JLabel JUNO; // Declare JLabel

        if (originalIcon.getIconWidth() == -1) {
            System.err.println("Warning: Could not load image icon at src/main/java/UI/Assets/JUNO.png");
            JUNO = new JLabel("JUNO Logo Missing");
            JUNO.setHorizontalAlignment(JLabel.CENTER);
            JUNO.setForeground(Color.WHITE);
        } else {
            // Define desired size
            int desiredWidth = 250;
            int desiredHeight = 250;

            // Scale the image
            Image scaledImage = originalIcon.getImage().getScaledInstance(desiredWidth, desiredHeight,
                    Image.SCALE_SMOOTH);

            // Create a new ImageIcon from the scaled image
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Create the JLabel with the scaled icon
            JUNO = new JLabel(scaledIcon);
            // Center the image within the JLabel's area if the JLabel is larger
        }
        topPanel.setLayout(new BorderLayout());
        topPanel.add(JUNO, BorderLayout.CENTER);

        int desiredWidth = 250;
        int desiredHeight = 250;

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
        topPanel.add(JUNO, BorderLayout.CENTER);

        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        topPanel.setPreferredSize(new Dimension(desiredWidth + 50, desiredHeight + 50));

        // Create a new panel to add
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setBackground(Color.WHITE);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        MuFilledButton startButton = new MuFilledButton("Start Game", Color.RED, Color.WHITE, 20, 80, 50);
        MuFilledButton howToPlayButton = new MuFilledButton("How to Play", new Color(255, 204, 102), Color.BLACK, 20,
                80,
                50);
        MuFilledButton exitButton = new MuFilledButton("Exit", Color.GRAY.darker(), Color.WHITE, 20, 80, 50);

        Dimension maxButtonSize = new Dimension(Integer.MAX_VALUE, startButton.getPreferredSize().height);
        startButton.setMaximumSize(maxButtonSize);

        Dimension maxHowToPlaySize = new Dimension(Integer.MAX_VALUE, howToPlayButton.getPreferredSize().height);
        howToPlayButton.setMaximumSize(maxHowToPlaySize);

        Dimension maxExitSize = new Dimension(Integer.MAX_VALUE, exitButton.getPreferredSize().height);
        exitButton.setMaximumSize(maxExitSize);

        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(howToPlayButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(exitButton);

        middlePanel.add(buttonPanel);

        JPanel circlePanel = new JPanel();
        circlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        circlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        circlePanel.add(createCircle(MuColors.MuRed));
        circlePanel.add(createCircle(MuColors.MuYellow));
        circlePanel.add(createCircle(MuColors.MuGreen));
        circlePanel.add(createCircle(MuColors.MuBlue));
        circlePanel.add(Box.createVerticalStrut(15));
        circlePanel.setBackground(Color.WHITE);

        circlePanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, this.getPreferredSize().height / 8));
        circlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, circlePanel.getPreferredSize().height));

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);

        add(circlePanel, BorderLayout.SOUTH);
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.white, 3, true));

    }

    private JPanel createCircle(Color color) {
        JPanel circle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                int width = getWidth();
                int height = getHeight();
                float strokeWidth = 6f; // Define stroke width

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillOval((int) (strokeWidth / 2), (int) (strokeWidth / 2), width - (int) strokeWidth,
                        height - (int) strokeWidth);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                int inset = (int) (strokeWidth / 2);
                g2.drawOval(inset, inset, width - (int) strokeWidth, height - (int) strokeWidth);

                g2.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(60, 60);
            }
        };

        circle.setOpaque(false);
        return circle;
    }
}