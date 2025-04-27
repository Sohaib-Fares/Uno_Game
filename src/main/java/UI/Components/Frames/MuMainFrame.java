package UI.Components.Frames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import UI.Components.Utils.MuMainBackgroundPanel;

public class MuMainFrame extends JFrame {
    public MuMainFrame() {
        super.setSize(900, 900);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("src/main/java/UI/Assets/JUNO.png");
        super.setIconImage(image.getImage());

        MuMainBackgroundPanel backgroundPanel = new MuMainBackgroundPanel();

        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(new GridBagLayout());
        MuMenuPanel menu = new MuMenuPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        gbc.weightx = 1.0; // Request horizontal space
        gbc.weighty = 1.0; // Request vertical space
        gbc.anchor = GridBagConstraints.CENTER; // Center the component

        backgroundPanel.add(menu, gbc);
        super.setLocationByPlatform(false);
        super.setVisible(true);
    }

}