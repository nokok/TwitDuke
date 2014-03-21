package net.nokok.twitduke.model.impl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import net.nokok.twitduke.model.CommandParser;
import net.nokok.twitduke.model.IParser;
import net.nokok.twitduke.model.ParsingResultListener;

public class CommandKeyListenerImpl extends KeyAdapter {
    private       ParsingResultListener listener;
    private final JTextArea             textArea;
    private final IParser               parser;

    public CommandKeyListenerImpl(ParsingResultListener listener, JTextArea textArea) {
        this.listener = listener;
        this.textArea = textArea;
        parser = new CommandParser(listener);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        parser.parse(textArea.getText());
    }
}
