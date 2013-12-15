package net.nokok.twitduke.main;

import net.nokok.twitduke.controller.Initializer;

/**
 * @author noko
 *         TwitDukeメインクラス
 */
class Main {
    public static void main(String[] args) {
        Initializer init = new Initializer();
        init.startApp();
    }
}
