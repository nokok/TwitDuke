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
 * お気に入りボタンです
 *
 */
public class FavoriteButton extends TWButton {

    private static final long serialVersionUID = 2609656566714323682L;

    /**
     * ボタンの背景色です
     */
    public static final Color FAV_BACKGROUND_COLOR = new Color(241, 196, 15);

    /**
     * お気に入りボタンを生成します
     */
    public FavoriteButton() {
        setBackground(FAV_BACKGROUND_COLOR);
        setPreferredSize(new Dimension(60, 15));
    }
}
