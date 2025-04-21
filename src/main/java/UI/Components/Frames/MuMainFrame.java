package UI.Components.Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import UI.Components.Cards.MuNumberedCard;
import UI.Components.Cards.MuWildCard;
import UI.Components.Utils.MuMainBackgroundPanel;

public class MuMainFrame extends JFrame {
    public MuMainFrame() {
        super.setSize(900, 900);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setLocationRelativeTo(null);

        Color[] colors = { new Color(0xe21b1b), new Color(0x0098dc), new Color(0x00aa4e), new Color(0xffd600) };
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

        // MainBackgroundPanel background = new MainBackgroundPanel(new
        // OvalGradientPaintSurface());

        // super.add(background);
        super.add(new MuMainBackgroundPanel(), BorderLayout.CENTER);
        super.setLocationByPlatform(false);
        super.setVisible(true);
    }

}