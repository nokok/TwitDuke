package net.nokok.twitduke.util;

/**
 * MIT License. http://opensource.org/licenses/mit-license.php
 * Copyright (c) 2014 noko
 */
public interface ILog {
    void fatal(String msg);

    void fatal(String msg, Throwable e);

    void error(String msg);

    void error(String msg, Throwable e);

    void warning(String msg);

    void warning(String msg, Throwable e);

    void info(String msg);

    void info(String msg, Throwable e);

    void debug(String msg);

    void debug(String msg, Throwable e);

    void trace(String msg);

    void trace(String msg, Throwable e);
}
