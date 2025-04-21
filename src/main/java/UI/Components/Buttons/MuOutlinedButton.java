package UI.Components.Buttons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

public class MuOutlinedButton extends JButton {
    public MuOutlinedButton(String text, Color bgColor, Color textColor) {
        super(text);

        super.setFont(new Font("Arial", Font.BOLD, 16));
        super.setBackground(bgColor);
        super.setForeground(textColor);
        super.setFocusPainted(false);
        super.setBorderPainted(false);
        super.setContentAreaFilled(false);
        super.setOpaque(false);
        super.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.setMaximumSize(new Dimension(300, 50));
        super.setPreferredSize(new Dimension(300, 50));

        super.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
        super.paintComponent(g);
    }

    // @Override
    // protected void paintBorder(Graphics g) {
    // Graphics2D g2 = (Graphics2D) g;
    // g2.setColor(getBackground().darker());
    // g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
    // }
}
