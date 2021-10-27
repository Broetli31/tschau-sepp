package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class ImageHandler {

    public static ImageIcon loadImage(String path) {
        BufferedImage image;
        ImageIcon imageIcon;
        try {
            image = ImageIO.read(ImageHandler.class.getResource(path));
            imageIcon = new ImageIcon(image);
        } catch (Exception e) {
            e.printStackTrace();
            image = null;
            imageIcon = null;
        }
        return imageIcon;
    }
}
