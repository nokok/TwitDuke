package net.nokok.twitduke.view.ui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import net.nokok.twitduke.main.Config;
import net.nokok.twitduke.view.ui.color.DefaultColor;

public class TWButton extends JButton {

    private Color bgColor;
    private Color fgColor;

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
            public void mousePressed(MouseEvent e) {
                setForeground(getForeground().brighter());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setForeground(fgColor);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(getBackground().darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(bgColor);
            }
        });
    }

    @Override
    public void setBackground(Color bg) {
        bgColor = getBackground();
        super.setBackground(bg);
    }

    @Override
    public void setForeground(Color fg) {
        fgColor = getForeground();
        super.setForeground(fg);
    }
}
