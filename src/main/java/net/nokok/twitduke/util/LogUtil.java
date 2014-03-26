package net.nokok.twitduke.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * MIT License. http://opensource.org/licenses/mit-license.php
 * Copyright (c) 2014 noko
 */
public enum LogUtil implements ILog {

    INSTANCE;
    private static final Log logger = LogFactory.getLog("net.nokok.twitduke");


    @Override
    public void fatal(String msg) {
        logger.fatal(msg);
    }

    @Override
    public void fatal(String msg, Throwable e) {
        logger.fatal(msg, e);
    }

    @Override
    public void error(String msg) {
        logger.error(msg);
    }

    @Override
    public void error(String msg, Throwable e) {
        logger.error(msg, e);
    }

    @Override
    public void warning(String msg) {
        logger.warn(msg);
    }

    @Override
    public void warning(String msg, Throwable e) {
        logger.warn(msg, e);
    }

    @Override
    public void info(String msg) {
        logger.info(msg);
    }

    @Override
    public void info(String msg, Throwable e) {
        logger.info(msg, e);
    }

    @Override
    public void debug(String msg) {
        logger.debug(msg);
    }

    @Override
    public void debug(String msg, Throwable e) {
        logger.debug(msg, e);
    }

    @Override
    public void trace(String msg) {
        logger.trace(msg);
    }

    @Override
    public void trace(String msg, Throwable e) {
        logger.trace(msg, e);
    }


}