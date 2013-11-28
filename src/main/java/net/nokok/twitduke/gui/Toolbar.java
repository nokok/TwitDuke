package net.nokok.twitduke.gui;

import net.nokok.twitduke.util.config.DefaultConfig;

import javax.swing.*;
import java.awt.*;

/**
 * @author noko
 *         ツールバーを構成するパネルのクラスです
 */
public class Toolbar extends JPanel {

    public Toolbar() {
        this.setLayout(new GridLayout());

        this.setSize(DefaultConfig.TOOLBAR_SIZE);

        this.add(new ToolButton("設定"));
        this.add(new ToolButton("更新"));
        this.add(new ToolButton("ユーザー切り替え"));

        this.add(new ToolButton("送信"));
    }
}
