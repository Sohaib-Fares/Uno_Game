package UI.Components.Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import UI.Components.Cards.MuNumberedCard;
import UI.Components.Cards.MuWildCard;
import UI.Components.Utils.MuMainBackgroundPanel;
import UI.Constatnts.MuColors;

public class MuMainFrame extends JFrame {
    public MuMainFrame() {
        super.setSize(900, 900);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("src/main/java/UI/Assets/JUNO.png");
        super.setIconImage(image.getImage());

        Color[] colors = { MuColors.MuBlue, MuColors.MuGreen, MuColors.MuRed, MuColors.MuYellow };
        String[] symbols = { "⊘", "⟲", "+2", "W", "+4" };

        // JPanel mainCardArea = new JPanel();
        // mainCardArea.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        // mainCardArea.setBackground(Color.gray);

        // for (Color color : colors) {

        // for (int i = 0; i < 10; i++) {
        // MuNumberedCard card = new MuNumberedCard(color, String.valueOf(i));
        // mainCardArea.add(card);
        // }
        // for (String symbol : symbols) {
        // if ("W".equals(symbol) || "+4".equals(symbol)) {
        // MuWildCard wildCard = new MuWildCard(symbol);
        // mainCardArea.add(wildCard);
        // } else {
        // MuNumberedCard card = new MuNumberedCard(color, symbol);
        // mainCardArea.add(card);
        // }
        // }

        // }
        // JScrollPane scrol = new JScrollPane(mainCardArea);

        // super.add(scrol);

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