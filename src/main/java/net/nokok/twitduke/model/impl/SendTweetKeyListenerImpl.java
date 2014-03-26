package net.nokok.twitduke.model.impl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import net.nokok.twitduke.model.listener.SendTweetListener;
import net.nokok.twitduke.util.KeyUtil;

/**
 * MIT License. http://opensource.org/licenses/mit-license.php
 * Copyright (c) 2014 noko
 */
public class SendTweetKeyListenerImpl extends KeyAdapter {

    private final SendTweetListener sendTweetListener;

    public SendTweetKeyListenerImpl(SendTweetListener sendTweetListener) {

        this.sendTweetListener = sendTweetListener;
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
