package UI.Components.Buttons;

import java.awt.Color;

/**
 * An elevated button variant with a moderate corner radius of 25.
 * This button style gives a slightly raised appearance for primary actions.
 * Inherits core functionality from MuButton while enforcing consistent corner
 * radius.
 */
public class MuFilledButton extends MuButton {
    private static final int ELEVATED_RADIUS = 50;

    /**
     * Creates an elevated button with the specified text, styling, and outline
     * properties.
     * Uses a fixed corner radius of 25 pixels.
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

    public MuFilledButton(String text, Color bgColor, Color textColor, int fontSize, int width, int height) {
        super(text, bgColor, textColor, fontSize, width, height, ELEVATED_RADIUS);

    }

    public MuFilledButton(String text, Color bgColor, Color textColor, int fontSize, int width, int height,
            int outlineThickness, Color outlineColor) {
        super(text, bgColor, textColor, fontSize, width, height, ELEVATED_RADIUS, outlineThickness, outlineColor);
    }
}