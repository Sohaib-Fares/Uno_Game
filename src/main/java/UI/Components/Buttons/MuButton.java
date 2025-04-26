package UI.Components.Buttons;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Custom button implementation for the Uno game UI.
 * Extends JButton with rounded corners and outline capabilities.
 * Serves as the base class for all button variants in the game.
 */
public class MuButton extends JButton {
    private int cornerRadius;  // Controls the roundness of button corners
    private int outlineThickness; // Thickness of the button outline border
    private Color outlineColor; // Color of the button outline border

    /**
     * Creates a button with specified text, colors, dimensions and corner radius.
     * No outline will be drawn.
     * 
     * @param text Button label text
     * @param bgColor Background color
     * @param textColor Text color
     * @param width Button width in pixels
     * @param height Button height in pixels
     * @param cornerRadius Radius of rounded corners
     * @param outlineThickness Thickness of the outline border (0 for no outline)
     * @param outlineColor Color of the outline border
     */


    public MuButton(String text, Color bgColor, Color textColor, int width, int height, int cornerRadius) {
        this(text, bgColor, textColor, width, height, cornerRadius, 0, Color.BLACK);    
    }

    public MuButton(String text, Color bgColor, Color textColor, int width, int height, int cornerRadius, int outlineThickness, Color outlineColor) {
        super(text);

        this.cornerRadius = cornerRadius;
        this.outlineThickness = outlineThickness;
        this.outlineColor = outlineColor;

        setFont(new Font("Noto Sans Symbols", Font.BOLD, 16));
        setBackground(bgColor);
        setForeground(textColor);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(300, 50));
        setPreferredSize(new Dimension(width, height));
        setVisible(true);
    }

    /**
     * Custom painting implementation that draws the button with rounded corners
     * and an optional outline border.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Optional outline
        if (outlineThickness > 0) {
            g2.setColor(outlineColor);
            g2.setStroke(new java.awt.BasicStroke(outlineThickness));
            int adjust = outlineThickness / 2;

            g2.drawRoundRect(
                adjust, adjust,
                getWidth() - outlineThickness, getHeight() - outlineThickness,
                cornerRadius - adjust, cornerRadius - adjust
            );
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
