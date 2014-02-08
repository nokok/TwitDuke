package net.nokok.twitduke.util;

import java.awt.event.MouseEvent;

public class MouseUtil {

    public static boolean isRightButtonClicked(MouseEvent e) {
        return e.getButton() == MouseEvent.BUTTON3;
    }

    public static boolean isDoubleClicked(MouseEvent e) {
        return e.getClickCount() > 1;
    }
}
