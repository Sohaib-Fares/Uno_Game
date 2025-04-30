package UI.Components.Panels.MuGamePlayPanels;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import UI.Components.Frames.MuMainFrame;

public class MuGamePlayPanel extends JPanel {
    public MuGamePlayPanel(MuMainFrame mainFrame) {
        // ***********set the frame*********************
        setLayout(new BorderLayout());

        // **************Top panel
        add(new TopBarPanel(mainFrame), BorderLayout.NORTH);

        // *****************bottom panel***************************
        add(new BottomBarPanel(), BorderLayout.SOUTH);

        // ******************center of the frame********************
        add(new CenterGamePanel(), BorderLayout.CENTER);

        // set visible to true
        setVisible(true);
    }
}
