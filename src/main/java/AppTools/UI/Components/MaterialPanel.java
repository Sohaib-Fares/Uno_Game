package AppTools.UI.Components;

import AppTools.UI.UnoTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MaterialPanel extends JPanel {
    private final int cornerRadius;
    private final int shadowSize;
    private final Color backgroundColor;
    private final boolean drawShadow;

    public MaterialPanel() {
        this(12, 5, UnoTheme.SURFACE, true);
    }
    
    public MaterialPanel(int cornerRadius, int shadowSize, Color backgroundColor, boolean drawShadow) {
        this.cornerRadius = cornerRadius;
        this.shadowSize = shadowSize;
        this.backgroundColor = backgroundColor;
        this.drawShadow = drawShadow;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(shadowSize, shadowSize, shadowSize, shadowSize));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth() - (2 * shadowSize);
        int height = getHeight() - (2 * shadowSize);
        
        // Draw shadow if enabled
        if (drawShadow) {
            for (int i = shadowSize; i > 0; i--) {
                float alpha = 0.4f * (float) i / shadowSize;
                g2.setColor(new Color(0, 0, 0, (int) (alpha * 255)));
                g2.fill(new RoundRectangle2D.Double(shadowSize - i + 1, shadowSize + i - 1, 
                        width + 2 * i - 2, height + 2 * i - 2, cornerRadius + i, cornerRadius + i));
            }
        }
        
        // Draw background
        g2.setColor(backgroundColor);
        g2.fill(new RoundRectangle2D.Double(shadowSize, shadowSize, width, height, cornerRadius, cornerRadius));
        
        g2.dispose();
    }
}