package net.nokok.twitduke.model.impl;

import net.nokok.twitduke.model.listener.ParserStateListener;
import net.nokok.twitduke.view.MainView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * MIT License. http://opensource.org/licenses/mit-license.php
 * Copyright (c) 2014 noko
 */
public class ParserStateListenerImpl implements ParserStateListener {

    private final Log logger = LogFactory.getLog(ParserStateListenerImpl.class);
    private final MainView mainView;

    public ParserStateListenerImpl(MainView mainView) {

        this.mainView = mainView;
    }

    @Override
    public void enabled() {
        mainView.setTitle("TD >_ [Ready]");
        mainView.clearTextField();

        logger.info("スクリプトが有効になりました");
    }

    @Override
    public void disabled() {
        mainView.setTitle("TwitDuke");
        mainView.clearTextField();

        logger.info("スクリプトが無効になりました");
    }

    @Override
    public void ready() {
        logger.info("スクリプトは実行可能です");

        mainView.setTitle("TD >_ [Ready]");
    }

    @Override
    public void error() {
        logger.error("スクリプトにエラーが発生しています");

        mainView.setTitle("TD >_ [Error]");
    }
}
