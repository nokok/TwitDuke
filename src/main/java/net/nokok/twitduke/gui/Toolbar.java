package net.nokok.twitduke.gui;

import net.nokok.twitduke.util.config.DefaultConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author noko
 *         ツールバーを構成するパネルのクラスです
 */
public class Toolbar extends JPanel {

    public Toolbar() {
        this.setLayout(new GridLayout());

        this.setSize(DefaultConfig.TOOLBAR_SIZE);

        //TODO:Java8がリリースされたらラムダで書きなおす

        ToolButton settingsButton = new ToolButton("設定");
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsWindow.getInstance().setVisible(true);
            }
        });
        this.add(settingsButton);

        ToolButton refreshButton = new ToolButton("更新");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO:Twitterのラッパの実装が終わったらコーディングする
            }
        });
        this.add(refreshButton);

        ToolButton userSwitcher = new ToolButton("ユーザー");
        userSwitcher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO:複数ユーザーに対応したらコーディングする
            }
        });
        this.add(userSwitcher);

        ToolButton sendButton = new ToolButton("送信");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO:Twitterのラッパの実装が終わったらコーディングする
            }
        });
        this.add(sendButton);
    }
}
