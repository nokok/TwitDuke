package net.nokok.twitduke.view;

import net.nokok.twitduke.view.ui.TWMenuItem;
import net.nokok.twitduke.view.ui.color.DefaultColor;

import javax.swing.*;
import java.awt.event.ActionListener;

public class TweetPopupMenu extends JPopupMenu {

    private final TWMenuItem reply      = new TWMenuItem("リプライ");
    private final TWMenuItem favorite   = new TWMenuItem("お気に入り");
    private final TWMenuItem retweet    = new TWMenuItem("リツイート");
    private final TWMenuItem openURL    = new TWMenuItem("URLを開く");
    private final TWMenuItem search     = new TWMenuItem("Googleで検索");
    private final TWMenuItem delete     = new TWMenuItem("削除");

    public TweetPopupMenu() {
        super("アクション");
        this.setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        this.add(reply);
        this.add(favorite);
        this.add(retweet);
        this.add(openURL);
        this.add(search);
        this.add(delete);
    }

    public void setReplyAction(ActionListener listener) {
        this.reply.addActionListener(listener);
    }

    public void setFavoriteAction(ActionListener listener) {
        this.favorite.addActionListener(listener);
    }

    public void setRetweetAction(ActionListener listener) {
        this.retweet.addActionListener(listener);
    }

    public void setOpenURLAction(ActionListener listener) {
        this.openURL.addActionListener(listener);
    }

    public void setSearchAction(ActionListener listener) {
        this.search.addActionListener(listener);
    }

    public void setDeleteAction(ActionListener listener) {
        this.delete.addActionListener(listener);
    }

}
