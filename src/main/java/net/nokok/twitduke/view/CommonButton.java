package net.nokok.twitduke.view;

import javax.swing.*;
import java.awt.*;

public class CommonButton extends JButton {

    public CommonButton(String title) {
        super(title);
        this.setBackground(new Color(10, 10, 50));
        this.setForeground(new Color(250, 250, 250));
        this.setOpaque(true);
        this.setBorderPainted(false);
        this.setPreferredSize(new Dimension(100, 30));
        this.setFont(new Font("ヒラギノ角ゴ Pro W3", Font.PLAIN, 14));
    }
}
