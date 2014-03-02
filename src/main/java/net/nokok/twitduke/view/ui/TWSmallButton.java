package net.nokok.twitduke.view.ui;

import net.nokok.twitduke.main.Config;

public class TWSmallButton extends TWButton {

    public TWSmallButton(String title) {
        super(title);
        setPreferredSize(Config.ComponentSize.SMALL_BUTTON);
        setFont(Config.FontConfig.SMALL_BUTTON);
    }
}
