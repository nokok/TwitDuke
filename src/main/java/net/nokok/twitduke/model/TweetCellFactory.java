package net.nokok.twitduke.model;

import net.nokok.twitduke.view.TweetCell;
import net.nokok.twitduke.view.TweetPopupMenu;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

public class TweetCellFactory {

    private final Twitter4jAsyncWrapper wrapper;

    private final String ICON_INTERNAL_ERROR_MESSAGE = "ユーザーのアイコン取得中にエラーが発生しました";

    public TweetCellFactory(Twitter4jAsyncWrapper twitter) {
        this.wrapper = twitter;
    }

    public TweetCell createTweetCell(final Status status) {

        boolean isMention = isMention(status);

        final TweetCell cell;

        if (status.isRetweet()) {
            cell = createRetweetCell(isMention, status);
        } else {
            cell = createNormalCell(isMention, status);
        }
        setCommonActionListener(cell, status);

        return cell;
    }

    private boolean isMention(Status status) {
        return !status.getText().contains("@" + AccessTokenManager.getInstance().getUserName()) && !status.isRetweet();
    }

    private TweetCell createNormalCell(boolean isMention, Status status) {
        try {
            URL userIconURL = new URL(status.getUser().getProfileImageURL());
            return new TweetCell(isMention,
                                 status.getId(),
                                 new ImageIcon(userIconURL),
                                 status.getUser().getScreenName(),
                                 extendURL(status));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new InternalError(ICON_INTERNAL_ERROR_MESSAGE);
        }
    }

    private TweetCell createRetweetCell(boolean isMention, Status status) {
        try {
            URL userIconURL, retweetIconURL;
            userIconURL = new URL(status.getRetweetedStatus().getUser().getProfileImageURL());
            retweetIconURL = new URL(status.getUser().getProfileImageURL());
            Image retweetUserImage = new ImageIcon(retweetIconURL).getImage().getScaledInstance(15, 15, Image.SCALE_FAST);
            return new TweetCell(isMention,
                                 status.getId(),
                                 new ImageIcon(userIconURL),
                                 new ImageIcon(retweetUserImage),
                                 "Retweet: " + status.getRetweetedStatus().getUser().getScreenName() + " by " + status.getUser().getScreenName(),
                                 extendURL(status.getRetweetedStatus()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new InternalError(ICON_INTERNAL_ERROR_MESSAGE);
        }
    }

    private String extendURL(Status status) {
        String tweetText = status.getText();
        for (URLEntity entity : status.getURLEntities()) {
            tweetText = tweetText.replaceAll(entity.getURL(), entity.getDisplayURL());
        }

        for (MediaEntity entity : status.getMediaEntities()) {
            tweetText = tweetText.replaceAll(entity.getURL(), entity.getDisplayURL());
        }
        return tweetText;
    }

    private void setCommonActionListener(final TweetCell cell, final Status status) {
        cell.setFavoriteAction(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cell.toggleFavoriteState()) {
                    wrapper.removeFavoriteTweet(status.getId());
                } else {
                    wrapper.favoriteTweet(status.getId());
                }
            }
        });
        cell.setRetweetAction(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                wrapper.retweetTweet(status.getId());
            }
        });

        final TweetPopupMenu functionPanel = new TweetPopupMenu();

        MouseAdapter functionPanelMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    functionPanel.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };

        cell.addMouseListener(functionPanelMouseAdapter);
        cell.setTextAreaAction(functionPanelMouseAdapter);
    }
}
