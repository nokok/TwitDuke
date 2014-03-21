package net.nokok.twitduke.model.impl;

import net.nokok.twitduke.model.listener.ParserStateListener;
import net.nokok.twitduke.view.MainView;

/**
 * MIT License. http://opensource.org/licenses/mit-license.php
 * Copyright (c) 2014 noko
 */
public class ParserStateListenerImpl implements ParserStateListener {

    private final MainView mainView;

    public ParserStateListenerImpl(MainView mainView) {

        this.mainView = mainView;
    }

    @Override
    public void enabled() {
        mainView.setTitle("TD >_ [Ready]");
        mainView.clearTextField();
    }

    @Override
    public void disabled() {
        mainView.setTitle("TwitDuke");
        mainView.clearTextField();
    }

    @Override
    public void ready() {
        mainView.setTitle("TD >_ [Ready]");
    }

    @Override
    public void error() {
        mainView.setTitle("TD >_ [Error]");
    }
}
