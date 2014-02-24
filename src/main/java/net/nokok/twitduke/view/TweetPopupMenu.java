package net.nokok.twitduke.view;

import java.awt.event.ActionListener;
import javax.swing.JPopupMenu;
import net.nokok.twitduke.view.ui.TWMenuItem;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class TweetPopupMenu extends JPopupMenu {

    private final TWMenuItem reply      = new TWMenuItem("リプライ");
    private final TWMenuItem favorite   = new TWMenuItem("お気に入り");
    private final TWMenuItem retweet    = new TWMenuItem("リツイート");
    private final TWMenuItem allURLOpen = new TWMenuItem("URLをすべて開く");
    private final TWMenuItem highlight  = new TWMenuItem("選択したセルのユーザーをハイライト/解除");
    private final TWMenuItem search     = new TWMenuItem("選択したテキストをGoogleで検索");
    private final TWMenuItem delete     = new TWMenuItem("削除");

    public TweetPopupMenu() {
        super("アクション");
        setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        add(reply);
        add(favorite);
        add(retweet);
        add(highlight);
        add(allURLOpen);
        add(search);
        add(delete);
    }

    public void setReplyAction(ActionListener listener) {
        reply.addActionListener(listener);
    }

    public void setFavoriteAction(ActionListener listener) {
        favorite.addActionListener(listener);
    }

    public void setRetweetAction(ActionListener listener) {
        retweet.addActionListener(listener);
    }

    public void setAllURLOpenAction(ActionListener listener) {
        allURLOpen.addActionListener(listener);
    }

    public void setHighlightAction(ActionListener listener) {
        highlight.addActionListener(listener);
    }

    public void setSearchAction(ActionListener listener) {
        search.addActionListener(listener);
    }

    public void setDeleteAction(ActionListener listener) {
        delete.addActionListener(listener);
    }

    public void addMenuItem(TWMenuItem menuItem) {
        add(menuItem);
    }
}
