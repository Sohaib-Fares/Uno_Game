package UI.Components.Cards;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * Represents the base visual component for an UNO card.
 * It handles drawing the rounded rectangle background, the inner white oval,
 * and the border. Specific card types (Numbered, Action, Wild) extend this
 * class.
 */
public class MuCard extends JPanel {
    private boolean border;
    private Color cardColor; // The main background color of the card
    private int arcSize = 30; // The radius for the rounded corners

    /**
     * Overrides the paintComponent method to draw the custom card appearance.
     * This includes the background color, a central translucent white oval,
     * and a black border, all with rounded corners.
     *
     * @param g The Graphics object to paint on.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Create a Graphics2D object for more advanced drawing capabilities
        Graphics2D g2d = (Graphics2D) g.create();

        // Enable anti-aliasing for smoother shapes
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Improve stroke rendering quality
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        // Get the current dimensions of the component
        int width = getWidth();
        int height = getHeight();

        // --- Draw Background ---
        // Set the drawing color to the card's specific color
        g2d.setColor(cardColor);
        // Fill a rounded rectangle covering the entire component area
        g2d.fillRoundRect(0, 0, width, height, arcSize, arcSize);

        // --- Draw Inner White Oval ---
        // Calculate the diameter of the inner oval based on the smaller dimension
        int circleDiameter = (int) (Math.min(width, height) * 0.7);
        // Calculate the top-left position (x, y) to center the oval
        int circleX = (width - circleDiameter) / 2;
        int circleY = (height - circleDiameter) / 2;
        // Set the color to white with some transparency (alpha value 110)
        g2d.setColor(new Color(255, 255, 255, 110));
        // Fill the oval
        g2d.fillOval(circleX, circleY, circleDiameter, circleDiameter);

        // --- Draw Border ---
        int borderThickness = 3; // Define the thickness of the card's border
        // Set the drawing color to black for the border
        g2d.setColor(Color.BLACK);
        // Set the stroke (line style) for the border thickness
        g2d.setStroke(new BasicStroke(borderThickness));
        // Calculate the offset needed to center the border stroke correctly
        int borderOffset = borderThickness / 2;
        // Draw the rounded rectangle border, slightly inset to account for thickness
        g2d.drawRoundRect(borderOffset, borderOffset, width - borderThickness, height - borderThickness, arcSize,
                arcSize); // Adjusted width/height for stroke

        // Dispose of the Graphics2D context to release system resources
        g2d.dispose();
        // Call the superclass's paintComponent method to ensure child components are
        // drawn

        super.paintComponent(g);
    }

    /**
     * Constructs a MuCard with a specified background color.
     * Sets the panel to be non-opaque (transparent) so the custom painting shows
     * through.
     * Initializes the layout and preferred size.
     *
     * @param color The background color of the card.
     */
    public MuCard(Color color) {
        this.cardColor = color;

        // Make the JPanel transparent so our custom drawing is visible
        setOpaque(false);

        // Set the layout manager (GridBagLayout is often used for centering)
        super.setLayout(new GridBagLayout());
        // Set the default preferred size for the card component
        super.setPreferredSize(new Dimension(100, 150));
    }

    /**
     * Returns the preferred size of the card component.
     *
     * @return A Dimension object representing the preferred width and height.
     */
    @Override
    public Dimension getPreferredSize() {
        // Define the standard size for an UNO card component
        return new Dimension(100, 150);
    }

    /**
     * Sets the arc size (corner radius) for the rounded rectangle.
     * Triggers a repaint to reflect the change visually.
     *
     * @param arcSize The desired arc size (radius) for the corners.
     */
    public void setArcSize(int arcSize) {
        this.arcSize = arcSize;
        // Request a repaint to update the component's appearance
        repaint();
    }
}