package UI.Components.Panels;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets; // Import Insets
import java.awt.Dimension; // Import Dimension
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import UI.Components.Containers.MuLayeredPane;
import UI.Components.Labels.MuLabel;
import UI.Components.Misc.MuImageIcon;

/**
 * A panel designed for headers, typically containing a background
 * (gradient or image) and a centered logo or title.
 * Can also accommodate components layered on top (e.g., back button).
 */
public class MuHeaderPanel extends MuGradientPanel { // Inherits gradient background

    private MuLayeredPane layeredPane;
    private MuLabel contentLabel; // To hold the centered icon or text
    private JPanel backgroundContentPanel; // Panel holding the contentLabel, placed on default layer

    public MuHeaderPanel() {
        super(new BorderLayout()); // Use BorderLayout by default

        layeredPane = new MuLayeredPane();
        // Important: LayeredPane usually works best with absolute positioning (null
        // layout)
        // If children need layout management, consider alternatives or manage bounds
        // carefully.
        // layeredPane.setLayout(null); // Explicitly set null layout if managing all
        // bounds
        layeredPane.setOpaque(false); // Layered pane itself is transparent

        // Panel to hold the main content (logo/title) on the base layer
        backgroundContentPanel = new MuPanel(new BorderLayout()); // Use BorderLayout to center contentLabel
        backgroundContentPanel.setOpaque(false);

        contentLabel = new MuLabel(); // Initialize label
        contentLabel.setHorizontalAlignment(JLabel.CENTER);
        contentLabel.setVerticalAlignment(JLabel.CENTER);
        backgroundContentPanel.add(contentLabel, BorderLayout.CENTER);

        // Add background content panel to the default layer
        // Since layeredPane likely has null layout, we MUST set bounds for
        // backgroundContentPanel
        layeredPane.add(backgroundContentPanel, MuLayeredPane.DEFAULT_LAYER);

        // Add the layered pane to the MuHeaderPanel
        add(layeredPane, BorderLayout.CENTER);
    }

    /**
     * Sets the main content to be a scaled image icon.
     * 
     * @param iconPath Path to the image file.
     * @param width    Desired width for the scaled image.
     * @param height   Desired height for the scaled image.
     */
    public void setContentIcon(String iconPath, int width, int height) {
        MuImageIcon originalIcon = new MuImageIcon(iconPath);
        if (originalIcon.getIconWidth() != -1) {
            Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            contentLabel.setIcon(new MuImageIcon(scaledImage));
            contentLabel.setText(null); // Clear any text
        } else {
            contentLabel.setIcon(null);
            contentLabel.setText("Icon Error"); // Placeholder text
            System.err.println("Warning: Could not load image icon at " + iconPath);
        }
        // Preferred size might change, trigger layout update
        revalidate();
        repaint();
    }

    /**
     * Sets the main content to be text.
     * 
     * @param text The text to display.
     */
    public void setContentText(String text) {
        contentLabel.setText(text);
        contentLabel.setIcon(null); // Clear any icon
        // Preferred size might change, trigger layout update
        revalidate();
        repaint();
    }

    /**
     * Adds a component to a specific layer on top of the background/content.
     * Useful for overlaying buttons or other elements.
     * 
     * @param component   The component to add.
     * @param layer       The layer depth (Integer). Higher numbers are on top. Use
     *                    MuLayeredPane constants like PALETTE_LAYER, MODAL_LAYER
     *                    etc.
     * @param constraints Constraints for positioning the component within the
     *                    layered pane (e.g., setBounds as a Rectangle).
     */
    public void addLayeredComponent(JComponent component, Integer layer, Object constraints) {
        // No need to set backgroundContentPanel bounds here, setBounds override handles
        // it.
        layeredPane.add(component, layer);
        // If using absolute positioning (setBounds), apply it here
        if (constraints instanceof java.awt.Rectangle) {
            component.setBounds((java.awt.Rectangle) constraints);
        } else if (constraints != null) {
            // Handle other constraint types if necessary (e.g., for a layout manager within
            // layeredPane)
            System.err.println(
                    "Warning: Unsupported constraints type for addLayeredComponent: " + constraints.getClass());
        }
        // Layered pane might need revalidation if component affects layout (unlikely
        // with null layout)
        // layeredPane.revalidate();
        // layeredPane.repaint();
    }

    // Override paintComponent ONLY for custom painting (like gradient), not layout.
    @Override
    protected void paintComponent(Graphics g) {
        // The superclass (MuGradientPanel) handles gradient painting.
        // The layout manager handles positioning children (layeredPane).
        super.paintComponent(g);
    }

    // Override setBounds to manage the size of layeredPane and
    // backgroundContentPanel.
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        // Calculate the effective area inside the header panel's borders
        Insets insets = getInsets();
        int effectiveWidth = width - insets.left - insets.right;
        int effectiveHeight = height - insets.top - insets.bottom;

        // Ensure layeredPane fills the effective area
        if (layeredPane != null) {
            layeredPane.setBounds(insets.left, insets.top, effectiveWidth, effectiveHeight);
        }

        // Ensure backgroundContentPanel fills the layeredPane
        // This is crucial because layeredPane likely uses null layout.
        if (backgroundContentPanel != null && layeredPane != null) {
            backgroundContentPanel.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
            // Explicitly tell backgroundContentPanel to re-layout its children (the
            // contentLabel)
            // now that its size is set.
            backgroundContentPanel.revalidate();
        }
        // No need to call revalidate() on 'this' usually, as super.setBounds should
        // trigger it.
    }
}