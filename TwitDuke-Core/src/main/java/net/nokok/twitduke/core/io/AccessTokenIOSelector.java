package net.nokok.twitduke.core.io;

import net.nokok.twitduke.base.io.Writer;
import net.nokok.twitduke.base.io.Reader;
import net.nokok.twitduke.core.type.ScreenName;
import twitter4j.auth.AccessToken;

public class AccessTokenIOSelector {

    private final Reader<AccessToken> reader;
    private final Writer<AccessToken> writer;

    public AccessTokenIOSelector(ScreenName screenName) {
        reader = new AccessTokenPropertyReader(screenName);
        writer = new AccessTokenPropertyWriter(screenName);
    }

    public Reader<AccessToken> getReader() {
        return reader;
    }

    public Writer<AccessToken> getWriter() {
        return writer;
    }
}
