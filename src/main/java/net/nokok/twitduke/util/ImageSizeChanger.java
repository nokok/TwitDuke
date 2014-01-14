package net.nokok.twitduke.util;

import java.awt.Image;
import javax.swing.ImageIcon;

public class ImageSizeChanger {

    public static ImageIcon createThumbnail(ImageIcon image) {
        return new ImageIcon(image.getImage().getScaledInstance(256, -1, Image.SCALE_SMOOTH));
    }
}
