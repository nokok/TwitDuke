package net.nokok.twitduke.util;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;

public class URLExpander {

    public static String extendURL(Status status) {
        String statusText = status.getText();
        for (URLEntity entity : status.getURLEntities()) {
            statusText = statusText.replaceAll(entity.getURL(), entity.getDisplayURL());
        }
        for (MediaEntity entity : status.getMediaEntities()) {
            statusText = statusText.replaceAll(entity.getURL(), entity.getDisplayURL());
        }
        return statusText;
    }
}
