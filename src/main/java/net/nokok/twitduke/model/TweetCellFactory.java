package net.nokok.twitduke.model;

import net.nokok.twitduke.view.TweetCell;
import net.nokok.twitduke.view.ui.color.DefaultColor;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
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

        URL userIconURL, retweetIconURL;

        int result = status.getText().indexOf("@" + AccessTokenManager.getInstance().getUserName());
        boolean isMention = (result != -1);

        final TweetCell cell;
        if (status.isRetweet()) {
            try {
                userIconURL = new URL(status.getRetweetedStatus().getUser().getProfileImageURL());
                retweetIconURL = new URL(status.getUser().getProfileImageURL());
                Image retweetUserImage = new ImageIcon(retweetIconURL).getImage().getScaledInstance(15, 15, Image.SCALE_FAST);
                cell = new TweetCell(isMention,
                                     status.getId(),
                                     new ImageIcon(userIconURL),
                                     new ImageIcon(retweetUserImage),
                                     "Retweet: " + status.getRetweetedStatus().getUser().getScreenName() + " by " + status.getUser().getScreenName(),
                                     formatTweet(status.getRetweetedStatus()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new InternalError(ICON_INTERNAL_ERROR_MESSAGE);
            }
        } else {
            try {
                userIconURL = new URL(status.getUser().getProfileImageURL());
                cell = new TweetCell(isMention,
                                     status.getId(),
                                     new ImageIcon(userIconURL),
                                     status.getUser().getScreenName(),
                                     formatTweet(status));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new InternalError(ICON_INTERNAL_ERROR_MESSAGE);
            }
        }

        cell.getFavoriteButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cell.isFavorited()) {
                    wrapper.removeFavoriteTweet(status.getId());
                    cell.getFavoriteButton().setBackground(DefaultColor.TweetCell.FAVORITE_BUTTON);
                    cell.setFavorited(false);
                } else {
                    wrapper.favoriteTweet(status.getId());
                    cell.getFavoriteButton().setBackground(DefaultColor.TweetCell.FAVORITED_BACKGROUND);
                    cell.setFavorited(true);
                }
            }
        });

        if (!status.getUser().isProtected() || status.getUser().getScreenName().equals(AccessTokenManager.getInstance().getUserName())) {
            cell.getRetweetButton().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    wrapper.retweetTweet(status.getId());
                    cell.getRetweetButton().setBackground(DefaultColor.TweetCell.RETWEETED_BACKGROUND);
                }
            });
        }

        return cell;
    }

    private String formatTweet(Status status) {
        return extendURL(status);
    }

    private String extendURL(Status status) {
        String tweetText = status.getText();
        for (URLEntity entity : status.getURLEntities()) {
            System.out.println(entity.getURL());
            System.out.println(entity.getDisplayURL());
            tweetText = tweetText.replaceAll(entity.getURL(), entity.getDisplayURL());
        }
        return tweetText;
    }
}
