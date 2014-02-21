package net.nokok.twitduke.view.ui;

import net.nokok.twitduke.main.Config;

public class TWSmallButton extends TWButton {

    public TWSmallButton(String title) {
        super(title);
        setPreferredSize(Config.ComponentSize.SMALL_BUTTON_SIZE);
        setFont(Config.FontConfig.SMALL_BUTTON_FONT);
    }
}
