package net.nokok.twitduke.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import net.nokok.twitduke.model.CommandParser;
import net.nokok.twitduke.model.IParser;
import net.nokok.twitduke.util.KeyUtil;

class TweetTextAreaKeyListener implements KeyListener {
    private final MainViewController mainViewController;

    public TweetTextAreaKeyListener(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        IParser parser = new CommandParser(mainViewController);
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
