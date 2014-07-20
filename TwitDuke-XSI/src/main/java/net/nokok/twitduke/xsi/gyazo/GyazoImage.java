package net.nokok.twitduke.xsi.gyazo;

import javafx.scene.image.Image;
import net.nokok.twitduke.base.async.AsyncTaskOnSuccess;
import net.nokok.twitduke.base.async.ThrowableReceivable;

public interface GyazoImage {

    public void onError(ThrowableReceivable receiver);

    public void onSuccess(AsyncTaskOnSuccess<Image> imageReceiver);

    public void asyncFetchImage(String url);
}
