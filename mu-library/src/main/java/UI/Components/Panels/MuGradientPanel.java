package UI.Components.Panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

public class MuGradientPanel extends MuPanel {

    // Store gradient parameters instead of the final Paint object
    private Point2D relativeCenter; // e.g., (0.5f, 0.5f) for center
    private float radius;
    private float[] dist;
    private Color[] colors;
    private boolean useRelativeCenter = false; // Flag to indicate dynamic center calculation

    private Paint customPaint; // For setting a pre-made Paint object

    public MuGradientPanel() {
        super();
        setOpaque(false);
    }

    public MuGradientPanel(LayoutManager layout) {
        super();
        setLayout(layout);
        setOpaque(false);
    }

    /**
     * Sets a radial gradient where the center is calculated dynamically
     * based on the panel's current size during painting.
     * 
     * @param relativeCenter The center point relative to panel dimensions (e.g.,
     *                       0.5f, 0.5f for true center).
     * @param radius         The radius of the gradient.
     * @param dist           The distribution of colors along the radius.
     * @param colors         The colors to use in the gradient.
     */
    public void setRelativeRadialGradient(Point2D relativeCenter, float radius, float[] dist, Color[] colors) {
        this.relativeCenter = relativeCenter;
        this.radius = radius;
        this.dist = dist;
        this.colors = colors;
        this.useRelativeCenter = true;
        this.customPaint = null; // Clear any custom paint
        repaint();
    }

    /**
     * Sets a fixed radial gradient paint for the background using an absolute
     * center point.
     * Use this if the center point should not change with panel size.
     * 
     * @param center The absolute center point of the gradient.
     * @param radius The radius of the gradient.
     * @param dist   The distribution of colors along the radius.
     * @param colors The colors to use in the gradient.
     */
    public void setAbsoluteRadialGradient(Point2D center, float radius, float[] dist, Color[] colors) {
        if (center == null || dist == null || colors == null) {
            this.customPaint = null;
        } else {
            this.customPaint = new RadialGradientPaint(center, radius, dist, colors);
        }
        this.useRelativeCenter = false; // Don't use dynamic calculation
        repaint();
    }

    /**
     * Sets a custom Paint object for the background.
     * Allows using other gradient types (LinearGradientPaint) or textures.
     * 
     * @param paint The Paint object to use.
     */
    public void setBackgroundPaint(Paint paint) {
        this.customPaint = paint;
        this.useRelativeCenter = false; // Don't use dynamic calculation
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Paint currentPaint = null;

        if (useRelativeCenter && relativeCenter != null && dist != null && colors != null) {
            // Calculate actual center based on current size
            int width = getWidth();
            int height = getHeight();
            Point2D actualCenter = new Point2D.Float(width * (float) relativeCenter.getX(),
                    height * (float) relativeCenter.getY());
            // Create the paint object dynamically
            currentPaint = new RadialGradientPaint(actualCenter, radius, dist, colors);
        } else if (customPaint != null) {
            // Use pre-set custom/absolute paint
            currentPaint = customPaint;
        }

        if (currentPaint != null) {
            g2d.setPaint(currentPaint);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        } else {
            // Fallback if no gradient is set
            if (isOpaque()) {
                g2d.setColor(getBackground());
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }

        g2d.dispose();

        // Call super.paintComponent AFTER drawing background to paint children
        super.paintComponent(g);
    }
}