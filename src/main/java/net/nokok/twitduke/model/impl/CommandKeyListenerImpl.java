package net.nokok.twitduke.model.impl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import net.nokok.twitduke.model.CommandParser;
import net.nokok.twitduke.model.IParser;
import net.nokok.twitduke.model.ParsingResultListener;

public class CommandKeyListenerImpl extends KeyAdapter {
    private ParsingResultListener listener;

    public CommandKeyListenerImpl() {

    }

    public CommandKeyListenerImpl(ParsingResultListener listener) {
        this.listener = listener;
    }

    public void setParsingResultListener(ParsingResultListener listener) {
        this.listener = listener;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        IParser parser = new CommandParser(listener);
    }
}
