import java.io.IOException;

import javax.swing.SwingUtilities;

import UI.Components.Frames.MuMainFrame;

public class MainApp {
    public static void main(String[] args) throws IOException {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MuMainFrame(); // Create and show the frame on the EDT
            }
        });
    }
}