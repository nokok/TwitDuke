package net.nokok.twitduke.util;

import net.nokok.twitduke.view.TweetCell;
import twitter4j.Status;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class TweetCellFactory {

    public static TweetCell createTweetCell(Status status) {

        URL userIconURL, retweetIconURL;
        if (status.isRetweet()) {
            try {
                userIconURL = new URL(status.getRetweetedStatus().getUser().getProfileImageURL());
                retweetIconURL = new URL(status.getUser().getProfileImageURL());
                Image retweetUserImage = new ImageIcon(retweetIconURL).getImage().getScaledInstance(15, 15, Image.SCALE_FAST);
                return new TweetCell(status.getId(),
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
                return new TweetCell(status.getId(), new ImageIcon(userIconURL), status.getUser().getScreenName(), status.getText());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new InternalError("ユーザーのアイコン取得中にエラーが発生しました");
            }
        }
    }
}
