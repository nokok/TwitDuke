package net.nokok.twitduke.xsi.pic;

import javafx.scene.image.Image;
import net.nokok.twitduke.base.async.AsyncTaskOnSuccess;
import net.nokok.twitduke.base.async.ThrowableReceivable;

public interface PictureFetcher {

    public void onError(ThrowableReceivable throwableReceiver);

    public void onSuccess(AsyncTaskOnSuccess<Image> imageReceiver);

    public void asyncFetchImage();
}
