package UI.Components.Panels.MuGamePlayPanels;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import UI.Components.Frames.MuMainFrame;

public class MuGamePlayPanel extends JPanel {
    public MuGamePlayPanel(MuMainFrame mainFrame) {
        // ***********set the frame*********************
        setLayout(new BorderLayout(0, 0));
        setOpaque(true); // Keep this panel opaque
        setBackground(Color.WHITE); // Or any desired background if children don't fully cover it

        // **************Top panel
        add(new TopBarPanel(mainFrame), BorderLayout.NORTH);

        // *****************bottom panel***************************
        add(new BottomBarPanel(), BorderLayout.SOUTH);

        // ******************center of the frame********************
        add(new CenterGamePanel(), BorderLayout.CENTER);

    }
}
