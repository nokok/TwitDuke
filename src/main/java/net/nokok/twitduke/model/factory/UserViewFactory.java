package net.nokok.twitduke.model.factory;

import java.net.URL;
import javax.swing.ImageIcon;
import net.nokok.twitduke.util.URLUtil;
import net.nokok.twitduke.view.UserView;
import twitter4j.User;

public class UserViewFactory {

    /**
     * 渡されたUserデータを用いてUserViewを生成します
     *
     * @param user UserViewを生成するUserのデータ
     * @return 生成されたUserView
     */
    public UserView createUserView(User user) {
        URL iconURL = URLUtil.createURL(user.getBiggerProfileImageURLHttps());
        UserView view = new UserView(new ImageIcon(iconURL),
                                     user.getName(),
                                     user.getScreenName() + (user.isProtected() ? "[Lock]" : ""),
                                     user.getStatusesCount(),
                                     user.getFriendsCount(),
                                     user.getFollowersCount(),
                                     user.getFavouritesCount(),
                                     user.getLocation(),
                                     user.getDescription());
        view.setLocationRelativeTo(null);
        view.pack();
        return view;
    }
}
