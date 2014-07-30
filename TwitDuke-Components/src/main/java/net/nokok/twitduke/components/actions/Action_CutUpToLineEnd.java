package net.nokok.twitduke.components.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JTextArea;

/**
 * Created by wtnbsts on 2014/07/26.
 */
public class Action_CutUpToLineEnd implements ActionListener {

    private String lastText;
    private int lastSelectionStart = -1;

    @Override
    public void actionPerformed(final ActionEvent event) {
        try {
            JTextArea src = (JTextArea) event.getSource();
            int cursor = src.getSelectionStart();
            if ( cursor == src.getText().length() ) {
                return;
            }
            String txt = src.getText();
            int lineSepPosition = txt.indexOf(System.lineSeparator(), cursor);
            lineSepPosition = lineSepPosition < 0 ? txt.length() : lineSepPosition;
            String selected = txt.substring(cursor, lineSepPosition);
            if ( selected.length() == 0 ) {
                selected = System.lineSeparator();
                lineSepPosition = cursor + selected.length();
            }

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();

            if ( txt.equals(lastText)
                 && lastSelectionStart == src.getSelectionStart()
                 && lastSelectionStart == src.getSelectionEnd() ) {
                selected = txt.equals(lastText) ? clipboard.getData(DataFlavor.stringFlavor) + selected : selected;
            }

            StringSelection ss = new StringSelection(selected);
            clipboard.setContents(ss, null);
            StringBuilder sb = new StringBuilder(txt);
            sb.delete(cursor, lineSepPosition);
            src.setText(sb.toString());
            src.setSelectionStart(cursor);
            src.setSelectionEnd(cursor);
            lastSelectionStart = cursor;

            lastText = sb.toString();
        } catch (UnsupportedFlavorException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
