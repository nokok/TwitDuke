package net.nokok.twitduke.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import net.nokok.twitduke.util.KeyUtil;

class TweetTextAreaKeyListener extends KeyAdapter {
    private final MainViewController mainViewController;

    public TweetTextAreaKeyListener(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    @Override
    public void keyTyped(KeyEvent e) {

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
