package UI.Components.Dialogs;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * Wrapper for javax.swing.JOptionPane static methods.
 * Provides methods for showing standard dialog boxes.
 */
public class MuJOptionPane {

    // Constants mirroring JOptionPane
    public static final int YES_NO_OPTION = JOptionPane.YES_NO_OPTION;
    public static final int YES_OPTION = JOptionPane.YES_OPTION;
    // Add other constants as needed...

    // Private constructor to prevent instantiation
    private MuJOptionPane() {
    }

    /**
     * Shows a confirmation dialog.
     * 
     * @param parentComponent the parent Component
     * @param message         the message to display
     * @param title           the title string for the dialog
     * @param optionType      the options available (e.g., YES_NO_OPTION)
     * @return an integer indicating the option selected by the user
     * @see javax.swing.JOptionPane#showConfirmDialog(java.awt.Component,
     *      java.lang.Object, java.lang.String, int)
     */
    public static int showConfirmDialog(Component parentComponent, Object message, String title, int optionType) {
        return JOptionPane.showConfirmDialog(parentComponent, message, title, optionType);
    }

    // Add wrappers for other JOptionPane static methods (showMessageDialog,
    // showInputDialog, etc.) as needed.
}