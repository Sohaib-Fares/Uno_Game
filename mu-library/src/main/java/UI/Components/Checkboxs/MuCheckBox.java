package UI;

import javax.swing.JCheckBox;
import javax.swing.*;

public class MuCheckBox extends JCheckBox {

   public MuCheckBox() {
      super((String) null, (Icon) null, false);
   }

   public MuCheckBox(Icon icon) {
      super((String) null, icon, false);
   }

   public MuCheckBox(Icon icon, boolean selected) {
      super((String) null, icon, selected);
   }

   public MuCheckBox(String text) {
      super(text, (Icon) null, false);
   }

   public MuCheckBox(Action a) {
      super();
      super.setAction(a);
   }

   public MuCheckBox(String text, boolean selected) {
      super(text, (Icon) null, selected);
   }

   public MuCheckBox(String text, Icon icon) {
      super(text, icon, false);
   }
}