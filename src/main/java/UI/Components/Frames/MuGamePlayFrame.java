package UI.Components.Frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import UI.Components.Panels.BottomBarPanel;
import UI.Components.Panels.CenterGamePanel;
import UI.Components.Panels.TopBarPanel;


public class MuGamePlayFrame extends JFrame {
    public MuGamePlayFrame() {
        // ***********set the frame*********************
        setTitle("JavUno");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());

        //**************Top panel
        add(new TopBarPanel(), BorderLayout.NORTH);

        //*****************bottom panel***************************
        add(new BottomBarPanel(), BorderLayout.SOUTH);

        //******************center of the frame********************
        add(new CenterGamePanel(), BorderLayout.CENTER);

        //set visible to true
        setVisible(true);
    }
}

