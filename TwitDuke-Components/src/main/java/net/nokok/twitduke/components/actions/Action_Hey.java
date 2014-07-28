package net.nokok.twitduke.components.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by a13612 on 2014/07/28.
 */
public class Action_Hey implements ActionListener {
    @Override
    public void actionPerformed(final ActionEvent e) {
        JOptionPane.showMessageDialog(null, "HEY!");
    }
}
