package net.nokok.twitduke.model;

import net.nokok.twitduke.model.listener.NotificationListener;
import twitter4j.RateLimitStatusEvent;
import twitter4j.RateLimitStatusListener;

/**
 * MIT License. http://opensource.org/licenses/mit-license.php
 * Copyright (c) 2014 noko
 */
public class RateLimitStatusListenerImpl implements RateLimitStatusListener {
    private final NotificationListener notificationListener;

    public RateLimitStatusListenerImpl(NotificationListener notificationListener) {

        this.notificationListener = notificationListener;
    }

    @Override
    public void onRateLimitStatus(RateLimitStatusEvent event) {
        notificationListener.setNotification("onRateLimitStatus:" + event.getRateLimitStatus());
    }

    @Override
    public void onRateLimitReached(RateLimitStatusEvent event) {
        notificationListener.setNotification("onRateLimitReached:" + event.getRateLimitStatus());
    }
}
