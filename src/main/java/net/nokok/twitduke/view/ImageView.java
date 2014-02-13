package net.nokok.twitduke.view;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        TWLabel image = new TWLabel();
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
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                URLUtil.openInBrowser(imageURL);
            }
        });
        add(open, BorderLayout.NORTH);
        add(image, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void close() {
        setVisible(false);
        dispose();
    }
}
