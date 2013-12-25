package net.nokok.twitduke.util;

import net.nokok.twitduke.model.AccessTokenManager;
import net.nokok.twitduke.view.TweetCell;
import twitter4j.Status;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

public class TweetCellFactory {

    public static TweetCell createTweetCell(Status status) {

        URL userIconURL, retweetIconURL;

        int result = status.getText().indexOf("@" + AccessTokenManager.getInstance().getUserName());
        boolean isMention = (result != -1);

        TweetCell cell;
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

        cell.getFunctionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        cell.getFunctionButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        return cell;
    }
}
