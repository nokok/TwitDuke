package net.nokok.twitduke.model.thread;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import net.nokok.twitduke.util.CacheUtil;
import net.nokok.twitduke.util.ImageUtil;
import net.nokok.twitduke.util.URLUtil;
import net.nokok.twitduke.view.ImageView;
import net.nokok.twitduke.view.ui.TWLabel;

public class AsyncImageLoader extends Thread {

    private final String            imageURLString;
    private final IAsyncImageLoader loader;
    private final CacheUtil cacheUtil = CacheUtil.getInstance();

    public AsyncImageLoader(String imageURLString, IAsyncImageLoader loader) {
        this.imageURLString = imageURLString;
        this.loader = loader;
    }

    @Override
    public void run() {
        loader.imageLoading();
        TWLabel label = (TWLabel) cacheUtil.get(imageURLString);
        if (label == null) {
            final URL imageURL = URLUtil.createURL(imageURLString);
            label = new TWLabel(ImageUtil.createThumbnail(new ImageIcon(imageURL)));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    ImageView view = new ImageView(imageURL);
                    view.setVisible(true);
                }
            });
            cacheUtil.set(imageURLString, label);
        }
        loader.imageLoaded(label);
    }
}
