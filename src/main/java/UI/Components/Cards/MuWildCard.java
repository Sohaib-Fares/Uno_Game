package UI.Components.Cards;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MuWildCard extends MuCard {
    private final String label; // Add label field

    // UNO Wild card showing a circle divided into four color segments
    public MuWildCard(String label) {
        super(new Color(0x212121));
        this.label = label; // initialize label

        // Add corner labels like MuNumberedCard
        Color fontColor = Color.WHITE;
        JLabel topLeftLabel = new JLabel(this.label);
        topLeftLabel.setFont(new Font("Lato", Font.BOLD, 14));
        topLeftLabel.setForeground(fontColor);
        JLabel bottomRightLabel = new JLabel(this.label);
        bottomRightLabel.setFont(new Font("Lato", Font.BOLD, 14));
        bottomRightLabel.setForeground(fontColor);

        // Add center label
        JLabel centerLabel = new JLabel(this.label);
        centerLabel.setFont(new Font("Lato", Font.BOLD, 40)); // Use a larger font for the center
        centerLabel.setForeground(fontColor);

        // GridBag constraints for labels
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;

        // Add Center Label
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0); // Reset insets for center
        add(centerLabel, gbc);

        // top-left
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(8, 8, 0, 0);
        add(topLeftLabel, gbc);
        // bottom-right
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(0, 0, 8, 8);
        add(bottomRightLabel, gbc);
        // No labels for wild card; focus on colored circle // <-- This comment is now
        // inaccurate, but kept for context
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Call super.paintComponent first to draw the base card (background, white
        // oval, border, labels)
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background and border are now drawn by super.paintComponent(g) from MuCard.
        // We only need to draw the four-color oval on top.

        int width = getWidth();
        int height = getHeight();
        int gap = 2; // Thickness of the gap lines

        // Define oval dimensions (wider than tall)
        int ovalHeight = (int) (height * 0.65); // Make it slightly smaller vertically
        int ovalWidth = (int) (width * 0.75); // Make it wider horizontally
        int ovalX = (width - ovalWidth) / 2;
        int ovalY = (height - ovalHeight) / 2;

        // Define UNO colors order: RED, BLUE, GREEN, YELLOW
        Color[] colors = new Color[] {
                new Color(0xe21b1b), // Red
                new Color(0x0098dc), // Blue
                new Color(0x00aa4e), // Green
                new Color(0xffd600) // Yellow
        };

        // Draw four-color Oval segments
        for (int i = 0; i < 4; i++) {
            g2d.setColor(colors[i]);
            // Use ovalWidth and ovalHeight for fillArc
            g2d.fillArc(ovalX, ovalY, ovalWidth, ovalHeight, i * 90, 90);
        }

        // Draw gaps (thin black lines)
        g2d.setColor(getBackgroundColor()); // Use background color for gaps
        g2d.setStroke(new BasicStroke(gap));

        int centerX = ovalX + ovalWidth / 2;
        int centerY = ovalY + ovalHeight / 2;

        // Draw horizontal gap line
        g2d.drawLine(ovalX, centerY, ovalX + ovalWidth, centerY);
        // Draw vertical gap line
        g2d.drawLine(centerX, ovalY, centerX, ovalY + ovalHeight);

        // Dispose the graphics context
        g2d.dispose();
    }

    // Helper to access MuCard's cardColor since it's private
    private Color getBackgroundColor() {
        // MuCard stores cardColor, but no getter; background is black for wild
        return Color.BLACK;
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }
}
