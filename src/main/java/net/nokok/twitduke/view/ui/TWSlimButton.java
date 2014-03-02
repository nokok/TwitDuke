package net.nokok.twitduke.view.ui;

import net.nokok.twitduke.main.Config;

public class TWSlimButton extends TWButton {

    public TWSlimButton(String title) {
        super(title);
        setPreferredSize(Config.ComponentSize.SLIM_BUTTON);
    }
}
