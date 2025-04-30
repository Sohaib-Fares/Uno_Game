package UI.Components.Misc;

import javax.swing.JSeparator;

/**
 * Wrapper for javax.swing.JSeparator.
 * A component used to visually separate other components, typically in menus or
 * toolbars.
 */
public class MuSeparator extends JSeparator {

    /**
     * Creates a new horizontal separator.
     */
    public MuSeparator() {
        super();
    }

    /**
     * Creates a new separator with the specified orientation.
     * 
     * @param orientation an integer specifying JSeparator.HORIZONTAL or
     *                    JSeparator.VERTICAL
     */
    public MuSeparator(int orientation) {
        super(orientation);
    }
}