package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import controllers.NavController;
import view.MuGamePlayPanels.BottomBarPanel;
import view.MuGamePlayPanels.CenterGamePanel;
import view.MuGamePlayPanels.TopBarPanel;

public class MuGamePlayPanel extends JPanel {

    public MuGamePlayPanel(NavController navController) {
        // ***********set the frame*********************
        setLayout(new BorderLayout(0, 0));
        setOpaque(true); // Keep this panel opaque
        setBackground(Color.WHITE); // Or any desired background if children don't fully cover it

        // **************Top panel

        add(new TopBarPanel(navController), BorderLayout.NORTH);

        // *****************bottom panel***************************
        add(new BottomBarPanel(), BorderLayout.SOUTH);

        // ******************center of the frame********************
        add(new CenterGamePanel(), BorderLayout.CENTER);

    }

}
