package net.nokok.twitduke.view.ui;

import java.awt.Dimension;
import java.awt.Font;

public class TWSmallButton extends TWButton {

    public TWSmallButton(String title) {
        super(title);
        setPreferredSize(new Dimension(70, 20));
        setFont(new Font("", Font.PLAIN, 12));
    }
}
