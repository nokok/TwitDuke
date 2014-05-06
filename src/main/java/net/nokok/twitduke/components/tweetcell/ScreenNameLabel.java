/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.components.tweetcell;

import java.awt.Font;
import net.nokok.twitduke.components.basic.TWLabel;

/**
 * スクリーンネーム(@から始まる20文字以内のID)を表示するラベルです
 *
 * @author noko < nokok.kz at gmail.com >
 */
public class ScreenNameLabel extends TWLabel {

    private static final long serialVersionUID = 3793205792620809321L;

    /**
     * スクリーンネームのフォントです
     */
    public static final Font FONT = new Font("", Font.PLAIN, 12);

    /**
     * スクリーンネームを表示するラベルを生成します
     *
     * @param screenName スクリーンネーム
     */
    public ScreenNameLabel(String screenName) {
        setText(screenName);
        setFont(FONT);
    }
}
