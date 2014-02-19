package net.nokok.twitduke.model.thread;

import net.nokok.twitduke.view.ui.TWLabel;

public interface IAsyncImageLoader {

    public void imageLoading();

    public void imageLoaded(TWLabel label);
}
