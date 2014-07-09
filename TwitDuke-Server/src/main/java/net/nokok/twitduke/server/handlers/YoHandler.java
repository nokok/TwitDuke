package net.nokok.twitduke.server.handlers;

import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.nokok.twitduke.core.factory.TAsyncTwitterFactory;
import net.nokok.twitduke.core.type.Retrievable;
import org.mortbay.jetty.Handler;
import twitter4j.AsyncTwitter;
import twitter4j.auth.AccessToken;

public class YoHandler implements Retrievable<Handler> {

    private final AsyncTwitter asyncTwitter;
    private final AbstractGetRequestHandler handler = new AbstractGetRequestHandler("/v1/Yo") {

        @Override
        public void doHandle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            StringBuilder stringBuilder = new StringBuilder("Yo");
            IntStream.range(0, new Random().nextInt(50)).mapToObj(i -> "　").forEach(stringBuilder::append);
            asyncTwitter.updateStatus(stringBuilder.toString());
            sendOK();
        }
    };

    public YoHandler(AccessToken accessToken) {
        this.asyncTwitter = TAsyncTwitterFactory.newInstance(accessToken);
    }

    @Override
    public Handler get() {
        return handler;
    }

}
