package UI.MuGamePlayPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import UI.Components.Cards.MuNumberedCard;
import UI.Components.Borders.MuRoundedBorder;
import UI.Components.Labels.MuCircleLabel;
import UI.Components.Labels.MuLabel;
import UI.Components.Panels.MuPanel;

public class CenterGamePanel extends JPanel {
    public CenterGamePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 239, 170));

        // ****direction label****
        MuLabel directionlabel = new MuLabel();
        directionlabel.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, new Color(255, 221, 0)));
        directionlabel.setText("Direction ➜");
        directionlabel.setHorizontalAlignment(SwingConstants.CENTER);
        directionlabel.setVerticalAlignment(SwingConstants.CENTER);
        directionlabel.setForeground(Color.BLACK);
        directionlabel.setOpaque(true);
        directionlabel.setBackground(new Color(255, 239, 170));
        directionlabel.setPreferredSize(new Dimension(1000, 40));
        directionlabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        // add the label to the top
        add(directionlabel, BorderLayout.NORTH);

        // *****Players panels******
        MuPanel playersPanel = new MuPanel();
        playersPanel.setLayout(new GridLayout(1, 3, 20, 10));
        playersPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        playersPanel.setBackground(new Color(237, 241, 252));

        playersPanel.add(createPlayerPanel("Player 2", Color.BLUE, 5));
        playersPanel.add(createPlayerPanel("Player 3", Color.GREEN, 3));
        playersPanel.add(createPlayerPanel("Player 4", Color.YELLOW, 8));

        // add players panel to the center
        add(playersPanel, BorderLayout.CENTER);

        // Current card
        MuPanel currentCardPanel = new MuPanel();
        currentCardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 60));
        currentCardPanel.setBackground(new Color(237, 241, 252));
        currentCardPanel.setPreferredSize(new Dimension(1000, 250));

        MuNumberedCard currentcard = new MuNumberedCard(new Color(0, 171, 240), "4");

        currentCardPanel.add(currentcard);

        // add current card panel to bottom
        add(currentCardPanel, BorderLayout.SOUTH);
    }

    private MuPanel createPlayerPanel(String name, Color color, int cardCount) {
        MuPanel panel = new MuPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new MuRoundedBorder(20),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        panel.setPreferredSize(new Dimension(100, 140));

        MuCircleLabel circleplayer = new MuCircleLabel(color, 40, true);

        MuLabel label = new MuLabel();
        label.setText(name);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        MuLabel cards = new MuLabel();
        cards.setText(cardCount + " cards");
        cards.setHorizontalAlignment(SwingConstants.CENTER);
        cards.setVerticalAlignment(SwingConstants.CENTER);

        panel.add(circleplayer, BorderLayout.NORTH);
        panel.add(label, BorderLayout.CENTER);
        panel.add(cards, BorderLayout.SOUTH);
        return panel;
    }
}
