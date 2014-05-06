/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.components.tweetcell;

import java.awt.Font;
import net.nokok.twitduke.components.basic.TWLabel;

/**
 * ユーザー名を表示するラベルです
 *
 * @author noko < nokok.kz at gmail.com >
 */
public class UserNameLabel extends TWLabel {

    private static final long serialVersionUID = -9107275301608425430L;

    /**
     * ユーザー名のフォントです
     */
    public static final Font FONT = new Font("", Font.BOLD, 14);

    /**
     * ユーザー名を表示するラベルを生成します
     *
     * @param userName ユーザー名
     */
    public UserNameLabel(String userName) {
        setText(userName);
        setFont(FONT);
    }
}
