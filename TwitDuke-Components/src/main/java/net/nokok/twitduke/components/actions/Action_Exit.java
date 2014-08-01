package net.nokok.twitduke.components.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by wtnbsts on 2014/07/28.
 */
public class Action_Exit implements ActionListener {

    @Override
    public void actionPerformed(final ActionEvent e) {
        System.exit(0);
    }
}
