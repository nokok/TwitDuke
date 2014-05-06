 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.components.tweetcell;

import java.awt.Color;
import java.awt.Dimension;
import net.nokok.twitduke.components.basic.TWButton;

/**
 * リツイートボタンです
 *
 */
public class RetweetButton extends TWButton {

    private static final long serialVersionUID = 2349270506630754751L;

    /**
     * ボタンの背景色です
     */
    public static final Color RT_BACKGROUND_COLOR = new Color(39, 174, 96);

    /**
     * リツイートボタンを生成します
     */
    public RetweetButton() {
        setBackground(RT_BACKGROUND_COLOR);
        setPreferredSize(new Dimension(60, 15));
    }
}
