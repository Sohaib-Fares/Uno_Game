package UI.Components.Labels;

import javax.swing.JPanel;

import UI.Components.Misc.MuImageIcon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Image;

public class MuCircleLabel extends JPanel {
    private Color circleColor;
    private float strokeWidth = 8f;
    private int size;
    private boolean showPersonIcon = false;
    private MuImageIcon personIcon;

    public MuCircleLabel(Color color) {
        // Default size that was 40
        this(color, 50);
    }

    public MuCircleLabel(Color color, int size) {
        this(color, size, false);
    }

    public MuCircleLabel(Color color, int size, boolean showPersonIcon) {
        this.circleColor = color;
        this.size = size;
        this.showPersonIcon = showPersonIcon;
        setOpaque(false);

        // Load the person icon if needed
        if (showPersonIcon) {
            loadPersonIcon();
        }

        // Set preferred, minimum, and maximum sizes
        setPreferredSize(new Dimension(size, size));
        setMinimumSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));
    }

    private void loadPersonIcon() {
        try {
            personIcon = new MuImageIcon(
                    "src/main/java/UI/Assets/png/person_24dp_000000_FILL0_wght400_GRAD0_opsz24.png");
            // Scale the icon to fit inside the circle (about 60% of circle size)
            int iconSize = (int) (size * 0.6);
            Image scaledImage = personIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
            personIcon = new MuImageIcon(scaledImage);
        } catch (Exception e) {
            System.err.println("Error loading person icon: " + e.getMessage());
            personIcon = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Use the actual width/height but ensure drawing is centered in the square
        int actualWidth = getWidth();
        int actualHeight = getHeight();
        int diameter = Math.min(actualWidth, actualHeight) - (int) strokeWidth; // Diameter based on smallest dimension
        int x = (actualWidth - diameter) / 2;
        int y = (actualHeight - diameter) / 2;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(circleColor);
        // Use calculated x, y, and diameter for drawing
        g2.fillOval(x, y, diameter, diameter);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        // Use calculated x, y, and diameter for drawing the border
        g2.drawOval(x, y, diameter, diameter);

        // Draw person icon if enabled, centered within the circle
        if (showPersonIcon && personIcon != null) {
            // Recalculate icon size based on the actual drawn diameter
            int iconSize = (int) (diameter * 0.6);
            if (personIcon.getIconWidth() != iconSize || personIcon.getIconHeight() != iconSize) {
                // Rescale if necessary (e.g., if size changed)
                Image scaledImage = personIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
                personIcon = new MuImageIcon(scaledImage);
            }

            int iconX = (actualWidth - iconSize) / 2;
            int iconY = (actualHeight - iconSize) / 2;
            personIcon.paintIcon(this, g, iconX, iconY);
        }

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        // Ensure the component prefers a square shape
        return new Dimension(size, size);
    }

    @Override
    public Dimension getMinimumSize() {
        // Ensure the component minimum size is square
        return new Dimension(size, size);
    }

    @Override
    public Dimension getMaximumSize() {
        // Ensure the component maximum size is square
        return new Dimension(size, size);
    }

    public void setCircleColor(Color color) {
        this.circleColor = color;
        repaint();
    }

    public Color getCircleColor() {
        return this.circleColor;
    }

    public void setSize(int size) {
        this.size = size;
        // Update preferred, min, max sizes
        setPreferredSize(new Dimension(size, size));
        setMinimumSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));
        if (showPersonIcon) {
            loadPersonIcon(); // Reload and rescale icon when size changes
        }
        revalidate();
        repaint();
    }

    public int getCircleSize() {
        return this.size;
    }

    public void setShowPersonIcon(boolean showPersonIcon) {
        this.showPersonIcon = showPersonIcon;
        if (showPersonIcon && personIcon == null) {
            loadPersonIcon();
        }
        repaint();
    }

    public boolean isShowingPersonIcon() {
        return this.showPersonIcon;
    }
}
