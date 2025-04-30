package UI.Components.Containers;

import java.awt.Component;
import javax.swing.JScrollPane;

/**
 * Wrapper for javax.swing.JScrollPane.
 * A component that provides a scrollable view of another component.
 */
public class MuScrollPane extends JScrollPane {

    /**
     * Creates an empty (no viewport view) JScrollPane.
     */
    public MuScrollPane() {
        super();
    }

    /**
     * Creates a JScrollPane that displays the contents of the specified component.
     * 
     * @param view the component to display in the scrollpane's viewport
     */
    public MuScrollPane(Component view) {
        super(view);
    }

    /**
     * Creates a JScrollPane that displays the view component in a viewport
     * whose view position can be controlled with scrollbars.
     * 
     * @param view      the component to display in the scrollpane's viewport
     * @param vsbPolicy an integer that specifies the vertical scrollbar policy
     * @param hsbPolicy an integer that specifies the horizontal scrollbar policy
     */
    public MuScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
    }

    /**
     * Creates an empty (no viewport view) JScrollPane with specified
     * scrollbar policies.
     * 
     * @param vsbPolicy an integer that specifies the vertical scrollbar policy
     * @param hsbPolicy an integer that specifies the horizontal scrollbar policy
     */
    public MuScrollPane(int vsbPolicy, int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
    }
}