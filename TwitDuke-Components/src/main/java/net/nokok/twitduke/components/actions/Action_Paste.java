package net.nokok.twitduke.components.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JTextArea;

/**
 * Created by wtnbsts on 2014/07/26.
 */
public class Action_Paste implements ActionListener {

    @Override
    public void actionPerformed(final ActionEvent event) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        try {
            String str = (String) toolkit.getSystemClipboard().getData(DataFlavor.stringFlavor);
            JTextArea src = (JTextArea) event.getSource();
            int selectionStart = src.getSelectionStart();
            int selectionEnd = src.getSelectionEnd();
            StringBuilder sb = new StringBuilder(src.getText());
            sb.delete(selectionStart, selectionEnd);
            sb.insert(selectionStart, str);
            src.setText(sb.toString());
            src.setSelectionStart(selectionStart + str.length());
            src.setSelectionEnd(selectionEnd);
        } catch (UnsupportedFlavorException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
