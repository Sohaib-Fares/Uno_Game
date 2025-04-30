package UI.Components.Labels;

import javax.swing.Icon;
import javax.swing.JLabel;

public class MuLabel extends JLabel {
    public MuLabel(String text, int horizontalAlignment) {
        super(text, (Icon) null, horizontalAlignment);
    }

    public MuLabel(String text) {
        super(text, (Icon) null, 10);
    }

    public MuLabel(Icon image, int horizontalAlignment) {
        super((String) null, image, horizontalAlignment);
    }

    public MuLabel(Icon image) {
        super((String) null, image, 0);
    }

    public MuLabel() {
        super("", (Icon) null, 10);
    }
}