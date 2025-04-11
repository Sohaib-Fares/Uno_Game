package AppTools.UI.Components;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class MaterialButton extends JButton {
    private boolean isHovered = false;
    private boolean isPressed = false;
    private Color backgroundColor;
    private Color hoverColor;
    private Color textColor;
    private int elevation = 2;
    private final int cornerRadius = 16;

    public MaterialButton(String text, Color bgColor) {
        super(text);
        this.backgroundColor = bgColor;
        this.hoverColor = deriveHoverColor(bgColor);
        this.textColor = isLightColor(bgColor) ? Color.BLACK : Color.WHITE;

        setupButton();
    }

    private void setupButton() {
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(textColor);
        setFont(getFont().deriveFont(Font.BOLD, 14f));

        // Add padding
        Insets insets = new Insets(12, 24, 12, 24);
        setMargin(insets);

        // Add mouse listeners for hover and press effects
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                elevation = 1;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                elevation = 2;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Draw shadow
        if (!isPressed && isEnabled()) {
            for (int i = 0; i < elevation; i++) {
                float alpha = 0.3f * (1 - (float) i / elevation);
                g2.setColor(new Color(0, 0, 0, (int) (alpha * 255)));
                g2.fill(new RoundRectangle2D.Double(i, i + 1, width - 2 * i, height - 2 * i, cornerRadius,
                        cornerRadius));
            }
        }

        // Draw background
        Color currentBg = isHovered ? hoverColor : backgroundColor;
        if (!isEnabled()) {
            currentBg = new Color(currentBg.getRed(), currentBg.getGreen(), currentBg.getBlue(), 120);
        }

        g2.setColor(currentBg);
        g2.fill(new RoundRectangle2D.Double(0, 0, width, height, cornerRadius, cornerRadius));

        // Calculate text position
        FontMetrics fm = g2.getFontMetrics();
        int textX = (width - fm.stringWidth(getText())) / 2;
        int textY = ((height - fm.getHeight()) / 2) + fm.getAscent();

        // Draw text
        g2.setColor(textColor);
        if (!isEnabled()) {
            g2.setColor(new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), 120));
        }
        g2.setFont(getFont());
        g2.drawString(getText(), textX, textY);

        g2.dispose();
    }

    private Color deriveHoverColor(Color baseColor) {
        // Lighten or darken the color for hover effect
        if (isLightColor(baseColor)) {
            return new Color(
                    Math.max(0, baseColor.getRed() - 20),
                    Math.max(0, baseColor.getGreen() - 20),
                    Math.max(0, baseColor.getBlue() - 20));
        } else {
            return new Color(
                    Math.min(255, baseColor.getRed() + 20),
                    Math.min(255, baseColor.getGreen() + 20),
                    Math.min(255, baseColor.getBlue() + 20));
        }
    }

    private boolean isLightColor(Color color) {
        double luminance = (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue()) / 255;
        return luminance > 0.5;
    }
}