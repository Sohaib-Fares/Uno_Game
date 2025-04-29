package UI.Components.Frames;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import UI.Components.Panels.BottomBarPanel;
import UI.Components.Panels.CenterGamePanel;
import UI.Components.Panels.TopBarPanel;

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
