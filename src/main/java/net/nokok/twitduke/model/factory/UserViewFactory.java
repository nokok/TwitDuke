package net.nokok.twitduke.model.factory;

import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import net.nokok.twitduke.view.UserView;
import twitter4j.User;

public class UserViewFactory {

    public UserView createUserView(User user) {
        URL iconURL;
        try {
            iconURL = new URL(user.getBiggerProfileImageURL());
        } catch (MalformedURLException e) {
            throw new InternalError("アイコンの取得中にエラーが発生しました。URLが不正です" + e.getMessage());
        }
        UserView userView = new UserView(new ImageIcon(iconURL),
                                         user.getName(),
                                         user.getScreenName() + (user.isProtected() ? "[Lock]" : ""),
                                         user.getStatusesCount(),
                                         user.getFriendsCount(),
                                         user.getFollowersCount(),
                                         user.getFavouritesCount(),
                                         user.getLocation(),
                                         user.getDescription());
        userView.setTitle(user.getName() + " の詳細");
        userView.pack();
        return userView;
    }
}
