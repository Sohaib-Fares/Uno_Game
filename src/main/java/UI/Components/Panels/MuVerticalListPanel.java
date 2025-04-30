// filepath: /home/hithat/Desktop/Univ/Uno_Project/Uno_Game/src/main/java/UI/Components/Panels/MuVerticalListPanel.java
package UI.Components.Panels;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.Box; // Import Box directly to check instanceof

import UI.Components.Misc.MuBox;

/**
 * A panel that arranges components vertically using BoxLayout
 * and automatically adds fixed spacing between them.
 */
public class MuVerticalListPanel extends MuPanel {

    private int spacing;

    /**
     * Creates a vertical list panel with default spacing (10px).
     */
    public MuVerticalListPanel() {
        this(20); // Default spacing
    }

    /**
     * Creates a vertical list panel with specified spacing.
     *
     * @param spacing The vertical space (in pixels) between components.
     */
    public MuVerticalListPanel(int spacing) {
        super(); // Call MuPanel constructor
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        this.spacing = spacing;
    }

    /**
     * Adds a component to the panel. If this is not the first component,
     * vertical spacing is added before it.
     *
     * @param comp The component to add.
     * @return The added component.
     */
    @Override
    public Component add(Component comp) {
        if (getComponentCount() > 0) {
            super.add(MuBox.createVerticalStrut(spacing));
        }
        return super.add(comp);
    }

    /**
     * Adds a component to the panel at the specified index.
     * Handles adding spacing appropriately based on position.
     * Note: Adding components out of sequential order might lead to
     * unexpected spacing. It's best to add components sequentially.
     *
     * @param comp  The component to add.
     * @param index The position at which to insert the component.
     * @return The added component.
     */
    @Override
    public Component add(Component comp, int index) { // Changed return type to Component
        // Simplified logic: Add spacing before if index > 0 and component at index-1
        // isn't already spacing
        // More robust logic might be needed for complex insertion scenarios.
        if (index > 0 && getComponentCount() > 0 && index <= getComponentCount()) { // Added bounds check
            // Check if the component *before* the target index is a strut
            if (!(getComponent(index - 1) instanceof Box.Filler)) {
                super.add(MuBox.createVerticalStrut(spacing), index);
                super.add(comp, index + 1); // Add the actual component after the strut
            } else {
                super.add(comp, index); // Add component directly if previous is already a strut
                // Check if there's a component *after* the newly added one and add spacing if
                // needed
                if (index < getComponentCount() - 1 && !(getComponent(index + 1) instanceof Box.Filler)) {
                    super.add(MuBox.createVerticalStrut(spacing), index + 1);
                }
            }
        } else { // Handles adding at index 0 or when the list is empty
            super.add(comp, index);
            // Add spacing after if this wasn't the last component added
            if (index < getComponentCount() - 1) {
                // Check if the component *after* the newly added one is already a strut
                if (!(getComponent(index + 1) instanceof Box.Filler)) {
                    super.add(MuBox.createVerticalStrut(spacing), index + 1);
                }
            } else if (index == 0 && getComponentCount() > 1) { // Special case: adding first element when others exist
                if (!(getComponent(1) instanceof Box.Filler)) {
                    super.add(MuBox.createVerticalStrut(spacing), 1);
                }
            }
        }
        return comp; // Return the added component
    }

    // Consider overriding other add methods (add(String, Component), add(Component,
    // Object))
    // if necessary, ensuring spacing is handled correctly.

    /**
     * Sets the vertical spacing between components.
     * Note: This does not update spacing for already added components.
     *
     * @param spacing The new spacing value.
     */
    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    /**
     * Gets the current vertical spacing.
     *
     * @return The spacing value.
     */
    public int getSpacing() {
        return spacing;
    }
}