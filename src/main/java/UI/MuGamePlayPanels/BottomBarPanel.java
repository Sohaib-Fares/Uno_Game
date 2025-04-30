package UI.MuGamePlayPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import UI.Components.Buttons.MuOutlinedButton;
import UI.Components.Cards.MuNumberedCard;
import UI.Components.Labels.MuCircleLabel;
import UI.Components.Labels.MuLabel;
import UI.Components.Panels.MuPanel;

public class BottomBarPanel extends JPanel {
    public BottomBarPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(0, 220));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(
            1, 0, 0, 0, new Color(255, 221, 0)
        ));
        setOpaque(true);

        //top panel (current player name + draw card)
        MuPanel toppanel = new MuPanel();
        toppanel.setLayout(new BorderLayout());
        toppanel.setBackground(Color.WHITE);

        //current player label
        MuPanel leftpanel2 = new MuPanel();
        leftpanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        leftpanel2.setOpaque(false);

        MuLabel currentplayer = new MuLabel();
        currentplayer.setText("Player1");
        currentplayer.setForeground(Color.BLACK);
        currentplayer.setOpaque(false);
        currentplayer.setPreferredSize(new Dimension(100, 35));
        currentplayer.setFont(new Font("Arial", Font.BOLD, 20));
        currentplayer.setHorizontalAlignment(SwingConstants.LEFT);
        currentplayer.setVerticalAlignment(SwingConstants.CENTER);

        MuCircleLabel circleplayer = new MuCircleLabel(Color.RED, 40, true);

        leftpanel2.add(circleplayer);
        leftpanel2.add(currentplayer);
        //add left (current player) panel to top panel
        toppanel.add(leftpanel2, BorderLayout.WEST);
        
        //draw card button
        MuPanel rightpanel2 = new MuPanel();
        rightpanel2.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        rightpanel2.setBackground(Color.WHITE);

        MuOutlinedButton drawcard = new MuOutlinedButton(
            "Draw Card",
             new Color(255, 221, 0),
              Color.BLACK,
               15,
                120,
                 40
                  );

        rightpanel2.add(drawcard);
        //add button (left) panel to top panel
        toppanel.add(rightpanel2, BorderLayout.EAST);

        //add top panel to top of bottom bar
        add(toppanel, BorderLayout.NORTH);

        //cards that cuurent player has
        MuPanel cardspanel = new MuPanel();
        cardspanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15 , 0));
        cardspanel.setBackground(Color.white);
        cardspanel.add(createPlayerCards("1", Color.GREEN));
        cardspanel.add(createPlayerCards("7", Color.RED));
        cardspanel.add(createPlayerCards("3", Color.YELLOW));
        //add them to the center of bottom bar
        add(cardspanel, BorderLayout.CENTER);
    }

    private MuNumberedCard createPlayerCards(String label, Color color) {
        MuNumberedCard card = new MuNumberedCard(color, label);
        card.setPreferredSize(new Dimension(80, 120));
        return card;
    }
}
