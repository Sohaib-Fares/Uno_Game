package UI.Components.Textfields;

import UI.Constatnts.MuColors;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;

/**
 * MuTextField is a custom text field component for the Uno game UI.
 * It provides enhanced styling and behavior compared to standard JTextField.
 */
public class MuTextField extends JTextField {
    
    private String placeholder;
    private boolean isPlaceholderShowing;
    private int borderRadius = 7; // Default border radius
    private static final int BORDER_THICKNESS = 1; // Consistent border thickness
    
    /**
     * Creates a default MuTextField with standard styling.
     */
    public MuTextField() {
        this("Player 1 name", 10);
    }
    
    /**
     * Creates a MuTextField with the specified placeholder text.
     * 
     * @param placeholder Text displayed when the field is empty
     */
    public MuTextField(String placeholder) {
        this(placeholder, 10);
    }
    
    /**
     * Creates a MuTextField with the specified placeholder text and columns.
     * 
     * @param placeholder Text displayed when the field is empty
     * @param columns The number of columns to use
     */
    public MuTextField(String placeholder, int columns) {
        super(columns);
        this.placeholder = placeholder;
        this.isPlaceholderShowing = placeholder != null && !placeholder.isEmpty();
        
        configureAppearance();
        setupPlaceholderBehavior();
    }
    
    private void configureAppearance() {
        // Configure font and colors
        setFont(new Font("Arial", Font.PLAIN, 14));
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
        
        // Configure border with rounded corners
        setBorder(new RoundedBorder(borderRadius, Color.gray, BORDER_THICKNESS));
        setMargin(new Insets(5, 10, 5, 10));
        
        // Configure other properties
        setCaretColor(MuColors.MuBlue);
        setSelectionColor(new Color(MuColors.MuBlue.getRed(), 
                                   MuColors.MuBlue.getGreen(), 
                                   MuColors.MuBlue.getBlue(), 100));
        
        // Make the text field transparent to show the rounded corners properly
        setOpaque(false);
    }
    
    // Custom rounded border implementation
    class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color borderColor;
        private int thickness;
        
        RoundedBorder(int radius, Color color, int thickness) {
            this.radius = radius;
            this.borderColor = color;
            this.thickness = thickness;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(borderColor.brighter());
            g2d.setStroke(new BasicStroke(thickness));
            g2d.draw(new RoundRectangle2D.Double(x + thickness / 2.0, y + thickness / 2.0, 
                    width - thickness, height - thickness, radius, radius));
            g2d.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            int value = thickness + 5;
            return new Insets(value, value, value, value);
        }
        
        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = thickness + 5;
            return insets;
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius));
        super.paintComponent(g);
        g2.dispose();
    }
    
    private void setupPlaceholderBehavior() {
        if (placeholder != null && !placeholder.isEmpty()) {
            showPlaceholder();
            
            addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (isPlaceholderShowing) {
                        setText("");
                        setForeground(Color.BLACK);
                        isPlaceholderShowing = false;
                    }
                }
                
                @Override
                public void focusLost(FocusEvent e) {
                    if (getText().isEmpty()) {
                        showPlaceholder();
                    }
                }
            });
        }
    }
    
    private void showPlaceholder() {
        setText(placeholder);
        setForeground(Color.GRAY);
        isPlaceholderShowing = true;
    }
    
    /**
     * Gets the actual text in the field, ignoring the placeholder if shown.
     * 
     * @return The actual text entered by the user, or empty string if showing placeholder
     */
    public String getActualText() {
        return isPlaceholderShowing ? "" : getText();
    }
    
    /**
     * Sets the text and ensures placeholder mode is properly handled
     * 
     * @param text The text to set in the field
     */
    public void setActualText(String text) {
        // Only change display if the text is different
        if (!text.equals(getText()) || isPlaceholderShowing) {
            super.setText(text);
            // If we're setting real text, we're not showing the placeholder
            if (text != null && !text.isEmpty()) {
                setForeground(Color.BLACK);
                isPlaceholderShowing = false;
            }
            // If setting empty text, show the placeholder
            else if (placeholder != null && !placeholder.isEmpty()) {
                showPlaceholder();
            }
        }
    }
    
    /**
     * Sets the placeholder text for this text field.
     * 
     * @param placeholder The placeholder text to use
     */
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        if (getText().isEmpty() || isPlaceholderShowing) {
            showPlaceholder();
        }
    }
    
    /**
     * Sets the border radius for the text field.
     * 
     * @param radius The corner radius in pixels
     */
    public void setBorderRadius(int radius) {
        this.borderRadius = radius;
        setBorder(new RoundedBorder(radius, Color.gray, BORDER_THICKNESS));
        repaint();
    }
    
    /**
     * Override to prevent automatic focus acquisition.
     * This ensures the text field won't get focus until explicitly clicked.
     */
    @Override
    public boolean isFocusable() {
        // We're still focusable when clicked, but won't get automatic focus
        return super.isFocusable() && hasFocus();
    }
}
