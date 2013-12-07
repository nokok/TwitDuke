package net.nokok.twitduke.controller;

public class Initializer {

    public void startApp() {
        WindowManager manager = new WindowManager();
        manager.initWindow();
        new MainController(manager.getMainView());
    }
}
