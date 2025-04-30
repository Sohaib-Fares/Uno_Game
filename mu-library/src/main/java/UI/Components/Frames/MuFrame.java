package UI.Components.Frames;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MuFrame extends JFrame {
    public MuFrame(String title) {
        super(title);
        super.setSize(900, 900);
        super.setPreferredSize(new Dimension(900, 900));
        super.setMinimumSize(new Dimension(900, 700));
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);

        super.setLocationByPlatform(false);
    }
}
