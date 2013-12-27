package net.nokok.twitduke.model;

import net.nokok.twitduke.view.TweetCell;
import net.nokok.twitduke.view.ui.color.DefaultColor;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.Status;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

public class TweetCellFactory {

    private final Twitter4jAsyncWrapper wrapper;

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
                                     status.getRetweetedStatus().getText());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new InternalError("ユーザーのアイコン取得中にエラーが発生しました");
            }
        } else {
            try {
                userIconURL = new URL(status.getUser().getProfileImageURL());
                cell = new TweetCell(isMention, status.getId(), new ImageIcon(userIconURL), status.getUser().getScreenName(), status.getText());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new InternalError("ユーザーのアイコン取得中にエラーが発生しました");
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
}
