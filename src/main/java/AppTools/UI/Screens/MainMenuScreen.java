package AppTools.UI.Screens;

import AppTools.UI.Components.MaterialButton;
import AppTools.UI.Components.MaterialPanel;
import AppTools.UI.UnoTheme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class MainMenuScreen extends JPanel {
    private final ActionListener playActionListener;
    private final ActionListener howToPlayActionListener;
    private final ActionListener exitActionListener;

    public MainMenuScreen(ActionListener playActionListener,
            ActionListener howToPlayActionListener,
            ActionListener exitActionListener) {
        this.playActionListener = playActionListener;
        this.howToPlayActionListener = howToPlayActionListener;
        this.exitActionListener = exitActionListener;

        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Add gradient background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                // Create gradient from dark red to medium red
                Color darkRed = new Color(180, 0, 0);
                Color mediumRed = UnoTheme.UNO_RED;

                RadialGradientPaint paint = new RadialGradientPaint(
                        new Point(width / 2, height / 2),
                        Math.max(width, height) / 2,
                        new float[] { 0.0f, 1.0f },
                        new Color[] { mediumRed, darkRed });

                g2.setPaint(paint);
                g2.fill(new Rectangle(0, 0, width, height));

                g2.dispose();
            }
        };
        add(backgroundPanel, BorderLayout.CENTER);

        // Create content panel
        backgroundPanel.setLayout(new GridBagLayout());
        MaterialPanel contentPanel = new MaterialPanel(20, 10, Color.WHITE, true);
        contentPanel.setLayout(new MigLayout("wrap, insets 0, fillx", "[grow]", "[]20[][][]20[]"));

        // Add UNO logo panel
        JPanel logoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create rounded rectangle for the header
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(
                        0, 0, UnoTheme.UNO_RED,
                        getWidth(), getHeight(), new Color(180, 0, 0));

                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Draw UNO logo text
                g2.setFont(new Font("Impact", Font.BOLD, 60));
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("UNO", (getWidth() - fm.stringWidth("UNO")) / 2,
                        (getHeight() + fm.getAscent()) / 2 - fm.getDescent() / 2);

                g2.dispose();
            }
        };

        logoPanel.setPreferredSize(new Dimension(350, 100));
        contentPanel.add(logoPanel, "growx, height 100!, wrap");

        // Add menu buttons
        MaterialButton playButton = new MaterialButton("Play Game", UnoTheme.UNO_RED);
        playButton.addActionListener(playActionListener);
        contentPanel.add(playButton, "growx, height 60!");

        MaterialButton howToPlayButton = new MaterialButton("How to Play", UnoTheme.UNO_YELLOW);
        howToPlayButton.addActionListener(howToPlayActionListener);
        contentPanel.add(howToPlayButton, "growx, height 60!");

        MaterialButton exitButton = new MaterialButton("Exit", new Color(60, 60, 60));
        exitButton.addActionListener(exitActionListener);
        contentPanel.add(exitButton, "growx, height 60!");

        // Add color indicators
        JPanel colorIndicators = new JPanel(new MigLayout("insets 0, center", "[]20[]20[]20[]", "[]"));
        colorIndicators.setOpaque(false);

        addColorCircle(colorIndicators, UnoTheme.UNO_RED);
        addColorCircle(colorIndicators, UnoTheme.UNO_BLUE);
        addColorCircle(colorIndicators, UnoTheme.UNO_GREEN);
        addColorCircle(colorIndicators, UnoTheme.UNO_YELLOW);

        contentPanel.add(colorIndicators, "growx, height 40!");

        // Add content panel to background
        backgroundPanel.add(contentPanel, new GridBagConstraints());
        contentPanel.setPreferredSize(new Dimension(400, 450));
    }

    private void addColorCircle(JPanel container, Color color) {
        JPanel circle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw shadow
                for (int i = 3; i > 0; i--) {
                    float alpha = 0.3f * (float) i / 3;
                    g2.setColor(new Color(0, 0, 0, (int) (alpha * 255)));
                    g2.fill(new Ellipse2D.Double(i, i, getWidth() - 2 * i, getHeight() - 2 * i));
                }

                // Draw circle
                g2.setColor(color);
                g2.fill(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));

                g2.dispose();
            }
        };
        circle.setPreferredSize(new Dimension(40, 40));
        container.add(circle);
    }
}