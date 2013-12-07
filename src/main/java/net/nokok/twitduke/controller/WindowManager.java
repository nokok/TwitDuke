package net.nokok.twitduke.controller;

import net.nokok.twitduke.view.MainView;

public class WindowManager {

    private MainView mainView;

    public void initWindow() {
        mainView = new MainView();
        mainView.setVisible(true);
    }

    public MainView getMainView() {
        return mainView;
    }

}
