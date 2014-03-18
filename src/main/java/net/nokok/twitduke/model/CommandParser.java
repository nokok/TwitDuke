package net.nokok.twitduke.model;

import net.nokok.twitduke.controller.MainViewController;

public class CommandParser implements IParser {

    private final MainViewController mainViewController;

    public CommandParser(MainViewController mainViewController) {

        this.mainViewController = mainViewController;
    }

    @Override
    public void parse(String text) {

    }
}
