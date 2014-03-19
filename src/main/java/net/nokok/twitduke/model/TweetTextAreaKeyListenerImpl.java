package net.nokok.twitduke.model;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import net.nokok.twitduke.model.listener.SendTweetListener;
import net.nokok.twitduke.util.KeyUtil;

public class TweetTextAreaKeyListenerImpl extends KeyAdapter {
    private SendTweetListener sendTweetListener;

    public TweetTextAreaKeyListenerImpl(SendTweetListener sendTweetListener) {
        this.sendTweetListener = sendTweetListener;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyUtil.isEnterAndShiftKey(e)) {
            sendTweetListener.sendTweet();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        KeyUtil.reset();
    }
}
