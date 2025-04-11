package AppTools.UI;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;

import javax.swing.*;
import java.awt.*;

public class UnoTheme {
    // UNO brand colors
    public static final Color UNO_RED = new Color(237, 28, 36);
    public static final Color UNO_BLUE = new Color(0, 114, 188);
    public static final Color UNO_GREEN = new Color(0, 175, 68);
    public static final Color UNO_YELLOW = new Color(255, 242, 0);

    // Material3 surface colors
    public static final Color SURFACE = new Color(255, 251, 254);
    public static final Color SURFACE_VARIANT = new Color(231, 224, 236);
    public static final Color ON_SURFACE = new Color(28, 27, 31);
    public static final Color ON_SURFACE_VARIANT = new Color(73, 69, 78);

    // Shadow colors
    public static final Color SHADOW_COLOR = new Color(0, 0, 0, 60);

    public static void setupTheme() {
        try {
            // Set up FlatLaf theme
            FlatLightLaf.setup();

            // Apply UNO custom colors
            UIManager.put("Button.arc", 16);
            UIManager.put("Component.arc", 12);
            UIManager.put("ProgressBar.arc", 12);
            UIManager.put("TextComponent.arc", 12);

            // Set font
            Font baseFont = new Font("Inter", Font.PLAIN, 14);
            UIManager.put("defaultFont", baseFont);

            // Primary color
            UIManager.put("Button.default.background", UNO_RED);
            UIManager.put("Button.default.foreground", Color.WHITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void applyThemeChange(Runnable action) {
        FlatAnimatedLafChange.showSnapshot();
        action.run();
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }
}