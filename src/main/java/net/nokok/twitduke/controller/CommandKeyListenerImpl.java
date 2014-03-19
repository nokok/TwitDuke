package net.nokok.twitduke.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import net.nokok.twitduke.model.CommandParser;
import net.nokok.twitduke.model.IParser;
import net.nokok.twitduke.model.ParsingResultListener;

class CommandKeyListenerImpl extends KeyAdapter {
    private final ParsingResultListener listener;

    public CommandKeyListenerImpl(ParsingResultListener listener) {
        this.listener = listener;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        IParser parser = new CommandParser(listener);

    }
}
