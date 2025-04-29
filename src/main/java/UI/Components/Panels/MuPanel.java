package UI.Components.Panels;

import javax.swing.*;
import java.awt.*;

public class MuPanel extends JPanel {
    public MuPanel() {
        super();
    }

    public static JPanel createInformationPanel(String title, String description) {
        // Create main panel with vertical layout
        JPanel panel = new JPanel();
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setBackground(new Color(254, 250, 234));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create and style the title label
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Lato", Font.BOLD, 16));
        titleLabel.setForeground(new Color(145, 63, 12)); // light text
        titleLabel.setOpaque(false);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create the separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(200, 200, 200)); // Light gray line
        separator.setMaximumSize(new Dimension(Short.MAX_VALUE, 2)); // Makes it a thin line

        // Create and style the description label
        JLabel descriptionLabel = new JLabel("<html>" + description + "</html>");
        descriptionLabel.setFont(new Font("Lato", Font.BOLD, 12));
        descriptionLabel.setBackground(Color.WHITE);
        descriptionLabel.setForeground(Color.BLACK);
        descriptionLabel.setOpaque(true);
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add all components to the panel
        panel.add(titleLabel);
        panel.add(separator);
        panel.add(descriptionLabel);

        return panel;
    }
}
