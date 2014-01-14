package net.nokok.twitduke.model.factory;

import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
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
        URL iconURL;
        try {
            iconURL = new URL(user.getBiggerProfileImageURLHttps());
        } catch (MalformedURLException e) {
            throw new InternalError("アイコンの取得中にエラーが発生しました。URLが不正です" + e.getMessage());
        }
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
