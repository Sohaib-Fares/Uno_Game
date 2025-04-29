package UI.Components.Frames;

import javax.swing.*;
import UI.Components.Buttons.MuFilledButton;
import UI.Constatnts.MuColors;
import java.awt.*;
import java.awt.event.ActionEvent; // Import ActionEvent
import java.awt.event.ActionListener; // Import ActionListener
import java.awt.geom.Point2D;

public class MuMenuPanel extends JPanel {

    private MuMainFrame mainFrame; // Reference to the main frame

    public MuMenuPanel(MuMainFrame frame) { // Accept MuMainFrame in constructor
        this.mainFrame = frame;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(550, 700)); // Keep preferred size

        // --- Top Panel (Logo) ---
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
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // Set preferred size based on desired logo size + padding
        topPanel.setPreferredSize(new Dimension(250 + 50, 250 + 50)); // Example: 250x250 logo + 25 padding each side

        ImageIcon originalIcon = new ImageIcon("src/main/java/UI/Assets/JUNO.png");
        JLabel JUNO;

        if (originalIcon.getIconWidth() == -1) {
            System.err.println("Warning: Could not load image icon at src/main/java/UI/Assets/JUNO.png");
            JUNO = new JLabel("JUNO Logo Missing");
            JUNO.setHorizontalAlignment(JLabel.CENTER);
            JUNO.setForeground(Color.WHITE);
        } else {
            int desiredWidth = 250;
            int desiredHeight = 250;
            Image scaledImage = originalIcon.getImage().getScaledInstance(desiredWidth, desiredHeight,
                    Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JUNO = new JLabel(scaledIcon);
            JUNO.setHorizontalAlignment(JLabel.CENTER);
            JUNO.setVerticalAlignment(JLabel.CENTER);
        }
        topPanel.add(JUNO, BorderLayout.CENTER);

        // --- Middle Panel (Buttons) ---
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setBackground(Color.WHITE);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20)); // Adjust padding as needed

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center buttons horizontally

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
        buttonPanel.add(Box.createVerticalStrut(20)); // Spacing
        buttonPanel.add(howToPlayButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Spacing
        buttonPanel.add(exitButton);

        middlePanel.add(buttonPanel);
        middlePanel.add(Box.createVerticalGlue()); // Pushes buttons up if extra space

        // --- Bottom Panel (Circles) ---
        JPanel circlePanel = new JPanel();
        circlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10)); // Added vertical gap
        circlePanel.setBackground(Color.WHITE);
        circlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Padding top/bottom

        circlePanel.add(createCircle(MuColors.MuRed));
        circlePanel.add(createCircle(MuColors.MuYellow));
        circlePanel.add(createCircle(MuColors.MuGreen));
        circlePanel.add(createCircle(MuColors.MuBlue));

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
                mainFrame.switchToPanel(MuMainFrame.GAME_SETUP_PANEL); // Switch to game
                // setup view
                System.out.println("Start Game Clicked - Implement GameSetupPanel"); // Placeholder
            }
        });

        howToPlayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to how-to-play view
                mainFrame.switchToPanel(MuMainFrame.HOW_TO_PLAY_PANEL);
                System.out.println("How to Play Clicked - Implement HowToPlayPanel"); // Placeholder
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ask for confirmation before exiting
                int confirmed = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to exit?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    mainFrame.dispose(); // Close the window
                    System.exit(0); // Terminate the application
                }
            }
        });
    }

    private JPanel createCircle(Color color) {
        JPanel circle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                int width = getWidth();
                int height = getHeight();
                float strokeWidth = 4f; // Adjusted stroke width

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                // Fill slightly smaller oval to account for stroke
                g2.fillOval((int) (strokeWidth / 2), (int) (strokeWidth / 2), (int) (width - strokeWidth),
                        (int) (height - strokeWidth));

                // Draw border
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(2f)); // Thinner border stroke
                g2.drawOval((int) (strokeWidth / 2), (int) (strokeWidth / 2), (int) (width - strokeWidth),
                        (int) (height - strokeWidth));

                // g2.dispose(); // Dispose is handled by Swing
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(50, 50); // Slightly smaller circles
            }
        };

        circle.setOpaque(false); // Important for transparency
        return circle;
    }
}