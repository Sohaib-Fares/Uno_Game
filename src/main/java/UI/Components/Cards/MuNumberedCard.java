package UI.Components.Cards;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;

public class MuNumberedCard extends MuCard {

    public MuNumberedCard(Color color, String label) {
        super(color);

        Color fontColor;
        if (color.getGreen() == (new Color(0xffd600).getGreen())) {
            fontColor = new Color(0x212121);

        } else {
            fontColor = Color.WHITE;
        }

        JLabel centerLabel = new JLabel(label);
        centerLabel.setFont(new Font("Lato", Font.BOLD, 40));
        centerLabel.setForeground(fontColor);

        JLabel topLeftLabel = new JLabel(label);
        topLeftLabel.setFont(new Font("Lato", Font.BOLD, 14));
        topLeftLabel.setForeground(fontColor);

        JLabel bottomRightLabel = new JLabel(label);
        bottomRightLabel.setFont(new Font("Lato", Font.BOLD, 14));
        bottomRightLabel.setForeground(fontColor);

        // --- GridBagConstraints ---
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Allow component to spread horizontally if needed
        gbc.weighty = 1.0; // Allow component to spread vertically if needed
        gbc.fill = GridBagConstraints.NONE; // Don't resize the component itself

        // Add Center Label
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0); // Reset insets
        add(centerLabel, gbc);

        // Add Top Left Label
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(8, 8, 0, 0); // Add some padding (top, left, bottom, right)
        add(topLeftLabel, gbc);

        // Add Bottom Right Label
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(0, 0, 8, 8); // Add some padding
        add(bottomRightLabel, gbc);
    }
}
