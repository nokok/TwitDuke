package net.nokok.twitduke.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import net.nokok.twitduke.model.account.AccessTokenManager;
import net.nokok.twitduke.util.URLUtil;
import twitter4j.AsyncTwitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class OAuthDialog extends JDialog {

    private final JTextField textField = new JTextField("");
    private final JButton    okButton  = new JButton("表示されたPINを入力後クリック");
    private final AsyncTwitter       asyncTwitter;
    private final AccessTokenManager tokenManager;

    public OAuthDialog(AsyncTwitter asyncTwitter, AccessTokenManager tokenManager) {
        this.asyncTwitter = asyncTwitter;
        this.tokenManager = tokenManager;
        setLayout(new BorderLayout());
        setTitle("認証してください");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(textField, BorderLayout.NORTH);
        add(okButton, BorderLayout.SOUTH);

        final RequestToken requestToken;
        try {
            requestToken = asyncTwitter.getOAuthRequestToken();
        } catch (TwitterException e) {
            throw new InternalError("認証中にTwitter側のエラーが発生しました" + e.getErrorMessage());
        }
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okButtonClicked(requestToken);
            }
        });
        URLUtil.openInBrowser(requestToken.getAuthenticationURL());
        pack();
    }

    private void okButtonClicked(RequestToken requestToken) {
        setTitle("認証処理/設定書き込み中");
        AccessToken token;
        try {
            token = asyncTwitter.getOAuthAccessToken(requestToken, textField.getText());
        } catch (TwitterException e) {
            throw new InternalError("認証中にTwitter側のエラーが発生しました: " + e.getErrorMessage());
        }
        asyncTwitter.setOAuthAccessToken(token);
        tokenManager.createPrimaryAccount(token);
        dispose();
    }
}
