package net.nokok.twitduke.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import net.nokok.twitduke.util.KeyUtil;

/**
 * MIT License. http://opensource.org/licenses/mit-license.php
 * Copyright (c) 2014 noko
 */
class SendTweetKeyListenerImpl extends KeyAdapter {

    private final MainViewController mainViewController;

    SendTweetKeyListenerImpl(MainViewController mainViewController) {

        this.mainViewController = mainViewController;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyUtil.isEnterAndShiftKey(e)) {
            mainViewController.sendTweet();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        KeyUtil.reset();
    }
}
