package net.nokok.twitduke.xsi.pic;

import java.io.IOException;
import java.net.URL;
import javafx.scene.image.Image;
import net.nokok.twitduke.base.async.AsyncTaskOnSuccess;
import net.nokok.twitduke.base.async.ThrowableReceivable;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class AbstractPictureFetcher implements PictureFetcher {

    protected final URL url;
    private ThrowableReceivable throwableReceiver;
    private AsyncTaskOnSuccess<Image> imageReceiver;

    public AbstractPictureFetcher(URL url) {
        this.url = url;
    }

    @Override
    public void asyncFetchImage() {
        Connection connection = Jsoup.connect(url.toString());
        try {
            imageReceiver.onSuccess(new Image(selectImage(connection.get())));
        } catch (IOException e) {
            throwableReceiver.onError(e);
        }
    }

    @Override
    public void onError(ThrowableReceivable throwableReceiver) {
        this.throwableReceiver = throwableReceiver;
    }

    @Override
    public void onSuccess(AsyncTaskOnSuccess<Image> imageReceiver) {
        this.imageReceiver = imageReceiver;
    }

    public abstract String selectImage(Document document);

}
