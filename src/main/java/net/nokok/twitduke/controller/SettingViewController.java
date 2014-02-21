package net.nokok.twitduke.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.nokok.twitduke.view.SettingView;

public class SettingViewController {
    private final SettingView view;

    public SettingViewController(final SettingView view) {
        this.view = view;
        view.selectAccount();
        view.setAccountButtonAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.selectAccount();
            }
        });
        view.setUIButtonAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.selectUI();
            }
        });
        view.setTweetBarButtonAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.selectTweet();
            }
        });
        view.setAboutButtonAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.selectAbout();
            }
        });
    }

    /**
     * 設定画面を表示します
     */
    public void show() {
        view.setVisible(true);
    }
}
