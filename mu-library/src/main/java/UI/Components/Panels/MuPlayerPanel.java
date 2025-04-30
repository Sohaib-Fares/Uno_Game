package UI.Components.Panels;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import UI.Components.Labels.MuCircleLabel;
import UI.Components.Textfields.MuTextField;
import UI.Constatnts.MuColors;

/**
 * Custom rounded border with configurable corner radius, color and thickness
 */
class RoundedBorder extends AbstractBorder {
    private Color color;
    private int thickness;
    private int radius;
    
    public RoundedBorder(Color color, int thickness, int radius) {
        this.color = color;
        this.thickness = thickness;
        this.radius = radius;
    }
    
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));
        g2.draw(new RoundRectangle2D.Double(x + thickness / 2, y + thickness / 2, 
                width - thickness, height - thickness, radius, radius));
        g2.dispose();
    }
    
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness + 2, thickness + 2, thickness + 2, thickness + 2);
    }
    
    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.right = insets.top = insets.bottom = thickness + 2;
        return insets;
    }
}

/**
 * MuPlayerPanel represents a player in the UI with a color circle indicator on the left
 * and a text field on the right for the player's name. Designed to be flexible in width.
 */
public class MuPlayerPanel extends JPanel {
    private MuCircleLabel circleLabel;
    private MuTextField nameField;
    private Color playerColor;
    private static final int CORNER_RADIUS = 10;
    private static final int CIRCLE_SIZE = 40;
    
    /**
     * Creates a player panel with a default placeholder name.
     * 
     * @param color The color to use for the player's circle indicator
     */
    public MuPlayerPanel(Color color) {
        this(color, "Player name");
    }
    
    /**
     * Creates a player panel with the specified color and name placeholder.
     * 
     * @param color The color to use for the player's circle indicator
     * @param placeholderName The placeholder text for the name field
     */
    public MuPlayerPanel(Color color, String placeholderName) {
        super();
        this.playerColor = color;
        
        // Setup panel properties
        setOpaque(false); // Set to false since we're drawing our own background
        setLayout(new BorderLayout(10, 0)); // 10px horizontal gap between components
        
        // Create the content padding border
        Border contentPadding = new EmptyBorder(5, 10, 5, 10);
        // Create the rounded border with brighter gray color
        Border roundedBorder = new RoundedBorder(MuColors.GRAY.brighter(), 1, CORNER_RADIUS);
        // Combine the borders
        setBorder(BorderFactory.createCompoundBorder(roundedBorder, contentPadding));
        
        // Create and add the circle label (left)
        circleLabel = new MuCircleLabel(color, CIRCLE_SIZE, true);
        add(circleLabel, BorderLayout.WEST);
        
        // Create and add the name field (center/right)
        nameField = new MuTextField(placeholderName);
        nameField.setPreferredSize(new Dimension(200, 35));
        add(nameField, BorderLayout.CENTER);
        
        // Set preferred size for the entire panel
        setPreferredSize(new Dimension(400, 50));
    }
    
    /**
     * Override paintComponent to draw rounded rectangle background.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fill rounded rectangle background with white
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS));
        
        super.paintComponent(g2);
        g2.dispose();
    }
    
    /**
     * Gets the player's name from the text field.
     * 
     * @return The player's name, or empty string if only showing placeholder
     */
    public String getPlayerName() {
        return nameField.getActualText();
    }
    
    /**
     * Sets the player's name in the text field.
     * 
     * @param name The name to display
     */
    public void setPlayerName(String name) {
        nameField.setActualText(name);
    }
    
    /**
     * Gets the color used for this player's circle indicator.
     * 
     * @return The player's color
     */
    public Color getPlayerColor() {
        return playerColor;
    }
    
    /**
     * Changes the color of the player's circle indicator.
     * 
     * @param color The new color to use
     */
    public void setPlayerColor(Color color) {
        this.playerColor = color;
        circleLabel.setCircleColor(color);
    }
    
    /**
     * Gets the underlying text field component.
     * 
     * @return The name text field
     */
    public MuTextField getNameField() {
        return nameField;
    }
    
    /**
     * Gets the circle label component.
     * 
     * @return The circle label
     */
    public MuCircleLabel getCircleLabel() {
        return circleLabel;
    }
}
