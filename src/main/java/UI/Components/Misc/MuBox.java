package UI.Components.Misc;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;

/**
 * Wrapper for javax.swing.Box.
 * Provides static methods for creating invisible components used for layout
 * spacing.
 */
public class MuBox {

    // Private constructor to prevent instantiation
    private MuBox() {
    }

    /**
     * Creates an invisible component that takes up space horizontally.
     * 
     * @param width the width of the component
     * @return the component
     * @see javax.swing.Box#createHorizontalStrut(int)
     */
    public static Component createHorizontalStrut(int width) {
        return Box.createHorizontalStrut(width);
    }

    /**
     * Creates an invisible component that takes up space vertically.
     * 
     * @param height the height of the component
     * @return the component
     * @see javax.swing.Box#createVerticalStrut(int)
     */
    public static Component createVerticalStrut(int height) {
        return Box.createVerticalStrut(height);
    }

    /**
     * Creates an invisible, fixed-size component.
     * 
     * @param d the dimensions of the component
     * @return the component
     * @see javax.swing.Box#createRigidArea(java.awt.Dimension)
     */
    public static Component createRigidArea(java.awt.Dimension d) {
        return Box.createRigidArea(d);
    }

    /**
     * Creates an invisible component that can expand horizontally.
     * 
     * @return the component
     * @see javax.swing.Box#createHorizontalGlue()
     */
    public static Component createHorizontalGlue() {
        return Box.createHorizontalGlue();
    }

    /**
     * Creates an invisible component that can expand vertically.
     * 
     * @return the component
     * @see javax.swing.Box#createVerticalGlue()
     */
    public static Component createVerticalGlue() {
        return Box.createVerticalGlue();
    }

    /**
     * Creates a Box that displays its components horizontally.
     * 
     * @return the Box
     * @see javax.swing.Box#createHorizontalBox()
     */
    public static Box createHorizontalBox() {
        return Box.createHorizontalBox();
    }

    /**
     * Creates a Box that displays its components vertically.
     * 
     * @return the Box
     * @see javax.swing.Box#createVerticalBox()
     */
    public static Box createVerticalBox() {
        return Box.createVerticalBox();
    }

    /**
     * Creates a Box that uses a BoxLayout.
     * 
     * @param parent the parent container
     * @param axis   the axis for the BoxLayout
     * @return the Box
     * @see javax.swing.Box#Box(int)
     */
    public static Box createBox(int axis) {
        // Note: The original Box constructor takes an axis directly.
        // We might need a different approach if we want a MuBox *instance*.
        // For now, mirroring static factory methods.
        if (axis == BoxLayout.X_AXIS) {
            return Box.createHorizontalBox();
        } else if (axis == BoxLayout.Y_AXIS) {
            return Box.createVerticalBox();
        } else {
            throw new IllegalArgumentException("Invalid axis: " + axis);
        }
    }
}