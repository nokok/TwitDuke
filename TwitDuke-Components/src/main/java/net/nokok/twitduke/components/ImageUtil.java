/*
 * The MIT License
 *
 * Copyright 2014 noko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.nokok.twitduke.components;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageUtil {

    public static ImageIcon to50x50(ImageIcon imageIcon) {
        return resizeTo(imageIcon, new Dimension(50, 50));
    }

    public static ImageIcon to25x25(ImageIcon imageIcon) {
        return resizeTo(imageIcon, new Dimension(25, 25));
    }

    public static ImageIcon resizeTo(ImageIcon icon, Dimension dimension) {
        Image s = icon.getImage().getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH);
        return new ImageIcon(s);
    }

    public static ImageIcon resizeTo(ImageIcon icon, int width, int height) {
        return resizeTo(icon, new Dimension(width, height));
    }

    public static Image iconToImage(Icon icon) {
        if ( icon instanceof ImageIcon ) {
            return ((ImageIcon) icon).getImage();
        } else {
            int w = icon.getIconWidth();
            int h = icon.getIconHeight();
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice device = env.getDefaultScreenDevice();
            GraphicsConfiguration config = device.getDefaultConfiguration();
            BufferedImage image = config.createCompatibleImage(w, h);
            Graphics2D g = image.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();
            return image;
        }
    }
}
