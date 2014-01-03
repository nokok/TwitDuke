package net.nokok.twitduke.view;

import net.nokok.twitduke.view.ui.TWLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class ImageView extends JFrame {

    public ImageView(final URL imageURL) {
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        final TWLabel image = new TWLabel();
        image.setIcon(new ImageIcon(imageURL));
        image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() >= 2) {
                    try {
                        Desktop.getDesktop().browse(imageURL.toURI());
                        close();
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        this.add(image, BorderLayout.CENTER);
        this.pack();
    }

    private void close() {
        this.setVisible(false);
        this.dispose();
    }
}
