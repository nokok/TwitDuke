package net.nokok.twitduke.model.listener;

import twitter4j.Status;

/**
 * MIT License. http://opensource.org/licenses/mit-license.php
 * Copyright (c) 2014 noko
 */
public interface CellInsertionListener {

    void insertCell(Status status);
}
