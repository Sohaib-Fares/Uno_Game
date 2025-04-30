package UI.Components.Misc;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;

/**
 * Wrapper for javax.swing.ImageIcon.
 * Represents an icon loaded from an image.
 */
public class MuImageIcon extends ImageIcon {

    /**
     * Creates an uninitialized image icon.
     */
    public MuImageIcon() {
        super();
    }

    /**
     * Creates an ImageIcon from an image file.
     * 
     * @param filename the file name
     */
    public MuImageIcon(String filename) {
        super(filename);
    }

    /**
     * Creates an ImageIcon from an existing Image object.
     * 
     * @param image the image
     */
    public MuImageIcon(Image image) {
        super(image);
    }

    /**
     * Creates an ImageIcon from the specified URL.
     * 
     * @param location the URL for the image
     */
    public MuImageIcon(URL location) {
        super(location);
    }

    /**
     * Creates an ImageIcon from an array of bytes.
     * 
     * @param imageData an array of pixels in an image format supported by the AWT
     *                  Toolkit
     */
    public MuImageIcon(byte[] imageData) {
        super(imageData);
    }

    /**
     * Creates an ImageIcon from an image file, with a description.
     * 
     * @param filename    the file name
     * @param description a brief textual description of the image
     */
    public MuImageIcon(String filename, String description) {
        super(filename, description);
    }

    /**
     * Creates an ImageIcon from an existing Image object, with a description.
     * 
     * @param image       the image
     * @param description a brief textual description of the image
     */
    public MuImageIcon(Image image, String description) {
        super(image, description);
    }

    /**
     * Creates an ImageIcon from the specified URL, with a description.
     * 
     * @param location    the URL for the image
     * @param description a brief textual description of the image
     */
    public MuImageIcon(URL location, String description) {
        super(location, description);
    }

    /**
     * Creates an ImageIcon from an array of bytes, with a description.
     * 
     * @param imageData   an array of pixels in an image format supported by the AWT
     *                    Toolkit
     * @param description a brief textual description of the image
     */
    public MuImageIcon(byte[] imageData, String description) {
        super(imageData, description);
    }
}