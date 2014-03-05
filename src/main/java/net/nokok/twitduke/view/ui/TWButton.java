package net.nokok.twitduke.view.ui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class TWButton extends JButton {

    private Color bgColor;

    public TWButton() {
        this("");
    }

    public TWButton(String title) {
        super(title);
        setBackground(DefaultColor.TWButton.DEFAULT_BACKGROUND);
        setForeground(DefaultColor.TWButton.DEFAULT_FOREGROUND);
        setOpaque(true);
        setBorderPainted(false);
        setFont(Config.FontConfig.BUTTON);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(getBackground().darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(getBackground().brighter());
            }
        });
    }
}
