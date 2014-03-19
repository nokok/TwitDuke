package net.nokok.twitduke.model.thread;

import net.nokok.twitduke.view.ui.TWLabel;

public interface IAsyncImageLoader {

    void imageLoading();

    void imageLoaded(TWLabel label);
}
