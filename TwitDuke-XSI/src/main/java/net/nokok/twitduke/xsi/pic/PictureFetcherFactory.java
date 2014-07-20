package net.nokok.twitduke.xsi.pic;

import java.net.URL;
import javafx.scene.image.Image;
import net.nokok.twitduke.base.async.AsyncTaskOnSuccess;
import net.nokok.twitduke.base.async.ThrowableReceivable;
import net.nokok.twitduke.xsi.pic.gyazo.GyazoImageImpl;

public class PictureFetcherFactory {

    public static PictureFetcher newInstance(URL url) {
        if ( url.getHost().contains("gyazo.com") ) {
            return new GyazoImageImpl(url);
        } else {
            return new NopPictureFetcher();
        }
    }

    static class NopPictureFetcher implements PictureFetcher {

        @Override
        public void asyncFetchImage() {
        }

        @Override
        public void onError(ThrowableReceivable throwableReceiver) {
        }

        @Override
        public void onSuccess(AsyncTaskOnSuccess<Image> imageReceiver) {
        }

    }
}
