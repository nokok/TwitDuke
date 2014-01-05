package net.nokok.twitduke.view;

import net.nokok.twitduke.model.AccountManager;
import net.nokok.twitduke.model.ConsumerKey;
import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class OAuthDialog extends JDialog {

    private final JTextField   textField = new JTextField("");
    private final JButton      okButton  = new JButton("表示されたPINを入力後クリック");
    private final AsyncTwitter twitter   = AsyncTwitterFactory.getSingleton();

    public OAuthDialog() {
        this.setLayout(new BorderLayout());
        this.setTitle("認証してください");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.add(textField, BorderLayout.NORTH);
        this.add(okButton, BorderLayout.SOUTH);

        try {

            twitter.setOAuthConsumer(ConsumerKey.TWITTER_CONSUMER_KEY, ConsumerKey.TWITTER_CONSUMER_SECRET);
            final RequestToken requestToken = twitter.getOAuthRequestToken();

            try {
                Desktop.getDesktop().browse(new URI(requestToken.getAuthenticationURL()));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
                throw new InternalError("認証URLオープンエラー");
            }

            textField.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }
            });
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        okButtonClicked(requestToken);
                    } catch (TwitterException ex) {
                        ex.printStackTrace();
                    }
                }
            });

        } catch (TwitterException e) {
            e.printStackTrace();
        }
        this.pack();
    }

    private void okButtonClicked(RequestToken requestToken) throws TwitterException {
        this.setTitle("認証処理/設定書き込み中");
        AccessToken token = twitter.getOAuthAccessToken(requestToken, textField.getText());
        AccountManager.getInstance().addAccount(token);
        this.dispose();
    }
}
