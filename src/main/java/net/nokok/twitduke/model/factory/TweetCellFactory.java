package net.nokok.twitduke.model.factory;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import javax.swing.ImageIcon;
import net.nokok.twitduke.model.AccessTokenManager;
import net.nokok.twitduke.view.ImageView;
import net.nokok.twitduke.view.TweetCell;
import net.nokok.twitduke.view.TweetPopupMenu;
import net.nokok.twitduke.view.UserView;
import net.nokok.twitduke.view.ui.TWLabel;
import net.nokok.twitduke.view.ui.TWMenuItem;
import net.nokok.twitduke.wrapper.Twitter4jAsyncWrapper;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;

public class TweetCellFactory {

    private final Twitter4jAsyncWrapper wrapper;

    private final String ICON_INTERNAL_ERROR_MESSAGE = "ユーザーのアイコン取得中にエラーが発生しました";

    private final UserViewFactory userViewFactory = new UserViewFactory();

    public TweetCellFactory(Twitter4jAsyncWrapper twitter) {
        this.wrapper = twitter;
    }

    public TweetCell createTweetCell(final Status status) {

        boolean isMention = isMention(status);

        final TweetCell cell;

        if (status.isRetweet()) {
            cell = createRetweetCell(isMention, status);
        } else {
            cell = createNormalCell(isMention, status);
        }
        setCommonActionListener(cell, status);
        setThumbnail(cell, status);
        return cell;
    }

    private void setThumbnail(TweetCell cell, Status status) {
        try {
            for (MediaEntity entity : status.getMediaEntities()) {
                final URL thumbnailURL = new URL(entity.getMediaURLHttps());

                ImageIcon before = new ImageIcon(thumbnailURL);

                ImageIcon resized = new ImageIcon(before.getImage().getScaledInstance(256, -1, Image.SCALE_SMOOTH));
                TWLabel image = new TWLabel(resized);

                image.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            ImageView view = new ImageView(thumbnailURL);
                            view.setVisible(true);
                        }
                    }
                });
                cell.setThumbnail(image);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private boolean isMention(Status status) {
        return status.getText().contains("@" + AccessTokenManager.getInstance().getUserName()) && !status.isRetweet();
    }

    private TweetCell createNormalCell(boolean isMention, Status status) {
        try {
            URL userIconURL = new URL(status.getUser().getProfileImageURLHttps());
            return new TweetCell(isMention,
                                 status.getId(),
                                 new ImageIcon(userIconURL),
                                 status.getUser().getScreenName(),
                                 extendURL(status));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new InternalError(ICON_INTERNAL_ERROR_MESSAGE);
        }
    }

    private TweetCell createRetweetCell(boolean isMention, Status status) {
        try {
            URL userIconURL, retweetIconURL;
            userIconURL = new URL(status.getRetweetedStatus().getUser().getProfileImageURLHttps());
            retweetIconURL = new URL(status.getUser().getProfileImageURLHttps());
            Image retweetUserImage = new ImageIcon(retweetIconURL).getImage().getScaledInstance(15, 15, Image.SCALE_FAST);
            return new TweetCell(isMention,
                                 status.getId(),
                                 new ImageIcon(userIconURL),
                                 new ImageIcon(retweetUserImage),
                                 "Retweet: " + status.getRetweetedStatus().getUser().getScreenName() + " by " + status.getUser().getScreenName(),
                                 extendURL(status.getRetweetedStatus()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new InternalError(ICON_INTERNAL_ERROR_MESSAGE);
        }
    }

    private String extendURL(Status status) {
        String tweetText = status.getText();
        for (URLEntity entity : status.getURLEntities()) {
            tweetText = tweetText.replaceAll(entity.getURL(), entity.getDisplayURL());
        }

        for (MediaEntity entity : status.getMediaEntities()) {
            tweetText = tweetText.replaceAll(entity.getURL(), entity.getDisplayURL());

        }
        return tweetText;
    }

    private void setCommonActionListener(final TweetCell cell, final Status status) {
        cell.setFavoriteAction(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                favorite(cell, status.getId());
            }
        });
        cell.setRetweetAction(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                retweet(cell, status.getId());
            }
        });

        final TweetPopupMenu functionPanel = new TweetPopupMenu();

        MouseAdapter functionPanelMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    functionPanel.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };

        MouseAdapter userViewMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    UserView view = userViewFactory.createUserView(status.isRetweet() ? status.getRetweetedStatus().getUser() : status.getUser());
                    view.setVisible(true);
                    cell.clearSelectedText();
                }
            }
        };

        cell.addMouseListener(functionPanelMouseAdapter);
        cell.addMouseListener(userViewMouseAdapter);
        cell.setTextAreaAction(functionPanelMouseAdapter);
        cell.setTextAreaAction(userViewMouseAdapter);

        functionPanel.setReplyAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (status.isRetweet()) {
                    wrapper.replyPreprocess(status.getRetweetedStatus());
                    return;
                }
                wrapper.replyPreprocess(status);
            }
        });

        functionPanel.setFavoriteAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                favorite(cell, status.getId());
            }
        });

        functionPanel.setRetweetAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retweet(cell, status.getId());
            }
        });

        functionPanel.setAllURLOpenAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allURLEntitiesOpen(status.getURLEntities());
                allMediaEntitiesOpen(status.getMediaEntities());
            }
        });

        functionPanel.setSearchAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchString = cell.getSelectedText();
                if (searchString == null) {
                    return;
                }
                try {
                    Desktop.getDesktop().browse(new URI("http://www.google.co.jp/search?q=" + URLEncoder.encode(cell.getSelectedText(), "utf-8")));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });

        functionPanel.setDeleteAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wrapper.deleteTweet(status.getId());
            }
        });

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
            functionPanel.addURLOpenButton(menuItem);
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
            functionPanel.addURLOpenButton(menuItem);
        }
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
