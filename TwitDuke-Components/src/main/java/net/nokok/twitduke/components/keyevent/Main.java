package net.nokok.twitduke.components.keyevent;


import net.nokok.twitduke.components.actions.Action_CutUpToLineEnd;
import net.nokok.twitduke.components.actions.Action_Paste;

import javax.swing.*;
import java.io.FileInputStream;

/**
 * Created by a13612 on 2014/07/22.
 */
public class Main extends JFrame {

    public Main() throws Exception {
        init();
    }

    public static void main(String[] args) throws Exception {
        Main m = new Main();
        m.setVisible(true);
    }


    private void init() throws Exception {
        setSize(400, 300);
        JTextArea textArea = new JTextArea();
//        add(textArea);

        IKeyMapStore store = IKeyMapStore.newInstance();
        IKeyMapSetting setting = store.load(new FileInputStream("resources/default.xml"));
        IActionRegister register = IActionRegister.newInstance(this);
        register.registerKeyMap(setting);


        if ( textArea instanceof JComponent ) {
            //((JComponent) c).registerKeyboardAction(new Action_Paste(), KeyStroke.getKeyStroke(""), 0);
            textArea.registerKeyboardAction(
                    new Action_CutUpToLineEnd(),
                    KeyStroke.getKeyStroke("ctrl K"), JComponent.WHEN_FOCUSED
            );
            textArea.registerKeyboardAction(
                    new Action_Paste(),
                    KeyStroke.getKeyStroke("ctrl Y"), JComponent.WHEN_FOCUSED
            );
        }
//        textArea.registerKeyboardAction(a2, KeyStroke.getKeyStroke("ctrl K"), JComponent.UNDEFINED_CONDITION);
//        textArea.unregisterKeyboardAction(KeyStroke.getKeyStroke("ctrl K"));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
