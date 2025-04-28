package UI.Components.Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

import UI.Components.Buttons.MuFilledButton;
import UI.Components.Buttons.MuOutlinedButton;
import UI.Components.Labels.MuCircleLabel;
import UI.Components.Panels.MuPlayerPanel;
import UI.Components.Utils.MuMainBackgroundPanel;
import UI.Constatnts.MuColors;

public class MuGameSetupFrame extends JFrame {
    
    private JPanel menuPanel;
    
    public MuGameSetupFrame() {
        // Setup frame properties
        super.setSize(900, 900);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
        super.setResizable(true);  // Make frame not resizable to keep exact dimensions
        ImageIcon image = new ImageIcon("src/main/java/UI/Assets/JUNO.png");
        super.setIconImage(image.getImage());
        
        // Create background panel with radial gradient
        MuMainBackgroundPanel backgroundPanel = new MuMainBackgroundPanel();
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(new GridBagLayout());
        
        // Create the main menu panel
        createMenuPanel();
        
        // Add the menu panel to the background using GridBagLayout for centering
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(menuPanel, gbc);
        
        // Display the frame
        super.setLocationByPlatform(false);
        super.setVisible(true);
    }

    private void createMenuPanel() {
        // Main panel with BoxLayout Y_AXIS
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
        
        // Set fixed size for menu panel
        menuPanel.setPreferredSize(new Dimension(500, 700));
        menuPanel.setMaximumSize(new Dimension(500, 700));
        menuPanel.setMinimumSize(new Dimension(500, 700));

        // Create and add the top panel with BorderLayout
        JPanel topPanel = createTopPanel();
        
        // Create and add the bottom panel with BoxLayout
        JPanel bottomPanel = createBottomPanel();

        // Add panels to the main panel
        menuPanel.add(topPanel);
        menuPanel.add(bottomPanel);
    }

    private JPanel createTopPanel() {
        // Create top panel with gradient background
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
        
        topPanel.setPreferredSize(new Dimension(500, 200));
        
        // Use a layered approach - one layer for the logo, one for the button
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(500, 200));
        
        // Create back button
        MuOutlinedButton backButton = new MuOutlinedButton("\u2190", MuColors.MuYellow, Color.BLACK, 16, 60, 40, 2, Color.black);
        backButton.setBounds(15, 15, 60, 40);  // Position at top-left with some margin
        
        // Create and center the JUNO logo
        ImageIcon originalIcon = new ImageIcon("src/main/java/UI/Assets/JUNO.png");
        JLabel logoLabel;
        
        if (originalIcon.getIconWidth() == -1) {
            System.err.println("Warning: Could not load image icon at src/main/java/UI/Assets/JUNO.png");
            logoLabel = new JLabel("JUNO Logo Missing");
            logoLabel.setForeground(Color.WHITE);
        } else {
            int desiredWidth = 175;
            int desiredHeight = 175;
            Image scaledImage = originalIcon.getImage().getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            logoLabel = new JLabel(scaledIcon);
        }
        
        // Center logo in the panel
        int logoWidth = 175;
        int logoHeight = 175;
        logoLabel.setBounds((500 - logoWidth) / 2, (200 - logoHeight + 50) / 2, logoWidth, logoHeight);
        
        // Add components to layered pane with different z-order
        layeredPane.add(logoLabel, Integer.valueOf(0));  // Background layer
        layeredPane.add(backButton, Integer.valueOf(1)); // Foreground layer
        
        topPanel.setLayout(new BorderLayout());
        topPanel.add(layeredPane, BorderLayout.CENTER);
        
        return topPanel;
    }


    private JPanel createBottomPanel() {
        // Create bottom panel with BoxLayout Y_AXIS
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Create player section panel with BoxLayout Y_AXIS
        JPanel playerSection = new JPanel();
        playerSection.setLayout(new BoxLayout(playerSection, BoxLayout.Y_AXIS));
        playerSection.setBackground(Color.WHITE);
        playerSection.setMinimumSize(new Dimension(400, 400));
        playerSection.setBorder(BorderFactory.createEmptyBorder(15, 30, 0, 30));
        playerSection.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add player panels with spacing
        MuPlayerPanel player1 = new MuPlayerPanel(MuColors.MuRed, "Player 1 name");
        playerSection.add(player1);
        playerSection.add(Box.createVerticalStrut(15));
        
        MuPlayerPanel player2 = new MuPlayerPanel(MuColors.MuBlue, "Player 2 name");
        playerSection.add(player2);
        playerSection.add(Box.createVerticalStrut(15));

        MuPlayerPanel player3 = new MuPlayerPanel(MuColors.MuGreen, "Player 3 name");
        playerSection.add(player3);
        playerSection.add(Box.createVerticalStrut(15));

        MuPlayerPanel player4 = new MuPlayerPanel(MuColors.MuBlue, "Player 4 name");
        playerSection.add(player4);
        playerSection.add(Box.createVerticalStrut(15));

        
        // Create Add/Remove panel with BorderLayout for left/right alignment
        JPanel addRemovePanel = new JPanel();
        addRemovePanel.setLayout(new BorderLayout());
        addRemovePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addRemovePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        addRemovePanel.setPreferredSize(new Dimension(600, 50));
        addRemovePanel.setBackground(Color.WHITE);
        

        // Create left button
        MuFilledButton addButton = new MuFilledButton
        ("+  Add Player", MuColors.MuGreen, Color.WHITE, 16, 140, 40);

        // Create right button
        MuFilledButton removeButton = new MuFilledButton
        ("-  Remove", MuColors.MuRed, Color.WHITE, 16, 140, 40);

        // Create left and right panels to hold buttons
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 0));
        leftPanel.setOpaque(false);
        leftPanel.add(addButton);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 20, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(removeButton);

        // Add panels to the main panel using BorderLayout
        addRemovePanel.add(leftPanel, BorderLayout.WEST);
        addRemovePanel.add(rightPanel, BorderLayout.EAST);
        
        // Create Start Game panel with FlowLayout
        JPanel startGamePanel = new JPanel();
        startGamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        startGamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGamePanel.setBackground(Color.WHITE);
        startGamePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Add Start Game button
        MuFilledButton startGameButton = new MuFilledButton("Start Game", MuColors.MuYellow, Color.black, 16, 420, 40, 1, Color.black);
        startGamePanel.add(startGameButton);
        
        // Add all sections to bottom panel
        bottomPanel.add(playerSection);
        bottomPanel.add(addRemovePanel);
        bottomPanel.add(startGamePanel);
        
        return bottomPanel;
    }
}
