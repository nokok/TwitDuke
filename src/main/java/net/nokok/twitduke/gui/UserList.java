package net.nokok.twitduke.gui;

/**
 * @author noko
 *         ユーザーのリストを保持するクラスです
 */
public class UserList extends ElementList {
    private static final UserList instance = new UserList();

    private UserList() {

    }

    public UserList getInstance() {
        return instance;
    }
}
