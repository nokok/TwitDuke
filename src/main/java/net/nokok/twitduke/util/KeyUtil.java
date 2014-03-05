package net.nokok.twitduke.util;

import java.awt.event.KeyEvent;

public class KeyUtil {

    private static boolean isShiftKey;
    private static boolean isEnterKey;

    public static boolean isEnterAndShiftKey(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SHIFT:
                isShiftKey = true;
                break;
            case KeyEvent.VK_ENTER:
                isEnterKey = true;
                break;
            default:
        }
        return isShiftKey && isEnterKey;
    }

    public static void reset() {
        isShiftKey = false;
        isEnterKey = false;
    }
}
