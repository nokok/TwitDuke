package net.nokok.twitduke.model.factory;

import com.google.common.base.Strings;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import net.nokok.twitduke.view.TweetCell;
import net.nokok.twitduke.view.TweetPopupMenu;
import net.nokok.twitduke.view.UserView;
import net.nokok.twitduke.view.ui.TWMenuItem;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

public class PopUpMenuFactory {

    private final UserViewFactory userViewFactory = new UserViewFactory();
    private final Twitter4jAsyncWrapper wrapper;

    public PopUpMenuFactory(Twitter4jAsyncWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public TweetPopupMenu createPopupMenu(final TweetCell cell, final Status status) {
        final TweetPopupMenu popupMenu = new TweetPopupMenu();
        MouseAdapter functionPanelMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };

        MouseAdapter userViewMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    UserView view = userViewFactory.createUserView(status.isRetweet() ? status.getRetweetedStatus().getUser() : status.getUser());
                    view.setLocation(e.getLocationOnScreen());
                    view.setVisible(true);
                    cell.clearSelectedText();
                }
            }
        };

        cell.addMouseListener(functionPanelMouseAdapter);
        cell.addMouseListener(userViewMouseAdapter);
        cell.setTextAreaAction(functionPanelMouseAdapter);
        cell.setTextAreaAction(userViewMouseAdapter);
        popupMenu.setReplyAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (status.isRetweet()) {
                    wrapper.replyPreprocess(status.getRetweetedStatus());
                    return;
                }
                wrapper.replyPreprocess(status);
            }
        });

        popupMenu.setFavoriteAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                favorite(cell, status.getId());
            }
        });

        popupMenu.setRetweetAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retweet(cell, status.getId());
            }
        });

        popupMenu.setAllURLOpenAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allURLEntitiesOpen(status.getURLEntities());
                allMediaEntitiesOpen(status.getMediaEntities());
            }
        });

        popupMenu.setSearchAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchString = cell.getSelectedText();
                if (Strings.isNullOrEmpty(searchString)) {
                    return;
                }
                try {
                    Desktop.getDesktop().browse(new URI("http://www.google.co.jp/search?q=" + URLEncoder.encode(cell.getSelectedText(), "utf-8")));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });

        popupMenu.setDeleteAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wrapper.deleteTweet(status.getId());
            }
        });

        for (final UserMentionEntity entity : status.getUserMentionEntities()) {
            TWMenuItem menuItem = new TWMenuItem(entity.getScreenName() + " の詳細を開く");
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    wrapper.getUserInfomation(entity.getId());
                }
            });
            popupMenu.addMenuItem(menuItem);
        }

        for (final URLEntity entity : status.getURLEntities()) {
            TWMenuItem menuItem = new TWMenuItem(entity.getDisplayURL() + "を開く");
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI(entity.getExpandedURL()));
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            popupMenu.addMenuItem(menuItem);
        }

        for (final MediaEntity entity : status.getMediaEntities()) {
            TWMenuItem menuItem = new TWMenuItem(entity.getDisplayURL() + "を開く");
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI(entity.getExpandedURL()));
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            popupMenu.addMenuItem(menuItem);
        }
        return popupMenu;
    }

    private void allURLEntitiesOpen(URLEntity[] entities) {
        for (URLEntity entity : entities) {
            try {
                Desktop.getDesktop().browse(new URI(entity.getExpandedURL()));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void allMediaEntitiesOpen(MediaEntity[] entities) {
        for (URLEntity entity : entities) {
            try {
                Desktop.getDesktop().browse(new URI(entity.getExpandedURL()));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void favorite(TweetCell cell, long id) {
        if (cell.toggleFavoriteState()) {
            wrapper.favoriteTweet(id);
        } else {
            wrapper.removeFavoriteTweet(id);
        }
    }

    private void retweet(TweetCell cell, long id) {
        wrapper.retweetTweet(id);
        cell.toggleRetweetState();
    }
}
