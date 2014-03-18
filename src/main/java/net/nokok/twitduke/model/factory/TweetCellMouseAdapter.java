package net.nokok.twitduke.model.factory;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.nokok.twitduke.util.MouseUtil;
import net.nokok.twitduke.view.TweetPopupMenu;
import net.nokok.twitduke.view.UserView;
import net.nokok.twitduke.view.tweetcell.TweetCell;
import twitter4j.Status;

class TweetCellMouseAdapter extends MouseAdapter {
    private final TweetPopupMenu popupMenu;
    private final Status         status;
    private final TweetCell      cell;

    public TweetCellMouseAdapter(TweetPopupMenu popupMenu, Status status, TweetCell cell) {
        this.popupMenu = popupMenu;
        this.status = status;
        this.cell = cell;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (MouseUtil.isRightButtonClicked(e)) {
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
        if (MouseUtil.isDoubleClicked(e)) {
            UserView view;
            if (status.isRetweet()) {
                view = UserViewFactory.createUserView(status.getRetweetedStatus().getUser());
            } else {
                view = UserViewFactory.createUserView(status.getUser());
            }
            view.setLocation(e.getLocationOnScreen());
            view.setVisible(true);
            cell.clearSelectedText();
        }
    }
}
