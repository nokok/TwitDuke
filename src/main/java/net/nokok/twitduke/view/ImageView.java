package net.nokok.twitduke.view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import net.nokok.twitduke.util.URLUtil;
import net.nokok.twitduke.view.ui.TWButton;
import net.nokok.twitduke.view.ui.TWLabel;

public class ImageView extends JFrame {

    public ImageView(final URL imageURL) {
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        final TWLabel image = new TWLabel();
        image.setIcon(new ImageIcon(imageURL));
        image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    close();
                }
            }
        });

        TWButton open = new TWButton("ブラウザで見る");
        open.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                URLUtil.openInBrowser(imageURL);
            }
        });
        this.add(open, BorderLayout.NORTH);
        this.add(image, BorderLayout.CENTER);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void close() {
        this.setVisible(false);
        this.dispose();
    }
}
