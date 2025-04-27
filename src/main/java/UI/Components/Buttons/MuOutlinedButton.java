package UI.Components.Buttons;

import java.awt.Color;

/**
 * An outlined button variant with a larger corner radius of 40.
 * This button style is designed for secondary actions with a more rounded
 * appearance.
 * Inherits core functionality from MuButton while enforcing a consistent corner
 * radius.
 */
public class MuOutlinedButton extends MuButton {
    private static final int OUTLINED_RADIUS = 40;

    /**
     * Creates an outlined button with the specified text and styling properties.
     * Uses a fixed corner radius of 40 pixels.
     *
     * @param text             Button label text
     * @param bgColor          Background color
     * @param textColor        Text color
     * @param fontSize         Text font size
     * @param width            Button width in pixels
     * @param height           Button height in pixels
     * @param outlineThickness Thickness of the outline border
     * @param outlineColor     Color of the outline border
     */

    public MuOutlinedButton(String text, Color bgColor, Color textColor, int fontSize, int width, int height) {
        super(text, bgColor, textColor, fontSize, width, height, OUTLINED_RADIUS);
    }

    public MuOutlinedButton(String text, Color bgColor, Color textColor, int fontSize, int width, int height,
            int outlineThickness, Color outlineColor) {
        super(text, bgColor, textColor, fontSize, width, height, OUTLINED_RADIUS, outlineThickness, outlineColor);
    }
}