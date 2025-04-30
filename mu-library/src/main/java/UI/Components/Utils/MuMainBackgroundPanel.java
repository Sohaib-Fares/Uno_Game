package UI.Components.Utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

public class MuMainBackgroundPanel extends JPanel {

    public MuMainBackgroundPanel() {
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();

        float radius = Math.min(width, height) / 5.5f;
        Point2D center = new Point2D.Float(width / 2f, height / 2f);

        float[] dist = { 0f, 0.4f, 0.7f, 1f };
        Color[] colors = { new Color(0x9a0000), new Color(0xc41212),
                new Color(0xe21b1b), new Color(0xff3b3b) };
        RadialGradientPaint paint = new RadialGradientPaint(center, radius, dist, colors, CycleMethod.REPEAT);

        g2.setPaint(paint);
        g2.fillRect(0, 0, width, height);
        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }
}
