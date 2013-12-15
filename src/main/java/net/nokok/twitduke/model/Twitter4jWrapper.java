package net.nokok.twitduke.model;

import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class Twitter4jWrapper {

    private static final String CONSUMER_KEY        = "VOIW6nzPVPEGyILu0kgMRQ";
    private static final String CONSUMER_SECRET_KEY = "x42tjv2Xrzsi3p5hfiGSYSiNLfa7VZv8Ozd0VHEaQ";

    private AccessToken accessToken;
    private static final AsyncTwitter     twitter  = AsyncTwitterFactory.getSingleton();
    private static final Twitter4jWrapper instance = new Twitter4jWrapper();

    private Twitter4jWrapper() {
        File propertiesFile = new File("twitter4j.properties");
        if (!propertiesFile.exists()) {
            twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET_KEY);
            showOAuthDialog();
        }
    }

    public static Twitter4jWrapper getInstance() {
        return instance;
    }

    public static AsyncTwitter getTwitter() {
        return twitter;
    }

    private void showOAuthDialog() {
        final JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setTitle("認証");
        dialog.setSize(new Dimension(450, 150));
        dialog.setLayout(new BorderLayout());
        dialog.add(new JLabel("以下のURLを開いてログイン後、表示された数字を入力して下さい"), BorderLayout.NORTH);
        final JTextField textField = new JTextField("ここに入力");
        dialog.add(textField, BorderLayout.SOUTH);
        JButton applyButton = new JButton("OK");
        dialog.add(applyButton, BorderLayout.EAST);

        JButton openURL = new JButton("開く");
        dialog.add(openURL, BorderLayout.CENTER);

        dialog.setVisible(true);
        final RequestToken requestToken;

        try {
            requestToken = twitter.getOAuthRequestToken();

            openURL.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI(requestToken.getAuthenticationURL()));
                    } catch (URISyntaxException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            applyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        accessToken = twitter.getOAuthAccessToken(requestToken, textField.getText());
                        twitter.setOAuthAccessToken(accessToken);
                        saveAccessToken(accessToken);
                    } catch (TwitterException ex) {
                        ex.printStackTrace();
                    }
                    dialog.dispose();
                }
            });

        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private void saveAccessToken(AccessToken accessToken) {
        Properties properties = new Properties();
        try {
            System.out.println(twitter);
            properties.setProperty("oauth.consumerKey", "VOIW6nzPVPEGyILu0kgMRQ");
            properties.setProperty("oauth.consumerSecret", "x42tjv2Xrzsi3p5hfiGSYSiNLfa7VZv8Ozd0VHEaQ");
            properties.setProperty("oauth.accessToken", accessToken.getToken());
            properties.setProperty("oauth.accessTokenSecret", accessToken.getTokenSecret());
            properties.store(new OutputStreamWriter(new FileOutputStream("twitter4j.properties")), "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
