/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.components.actions;

/**
 *
 * @author wtnbsts
 */
public class ActionUtil {

    public static int getEndOfLine(final String txt, final int caretPosition) {
        int lineSepPosition = txt.indexOf(System.lineSeparator(), caretPosition);
        return lineSepPosition < 0 ? txt.length() : lineSepPosition;
    }

    public static int getBeginningOfLine(final String txt, final int caretPosition) {
        String subtext = txt.substring(0, caretPosition);
        int lineSepPosition = subtext.lastIndexOf(System.lineSeparator(), caretPosition);
        return lineSepPosition < 0 ? 0 : lineSepPosition + System.lineSeparator().length();
    }

    public static int getUpSide(final String txt, final int caretPosition) {
        int lineStartPosit = getBeginningOfLine(txt, caretPosition);
        if ( lineStartPosit == 0 ) {
            // 行の先頭が0番目の文字なら、上に行は存在しない
            return -1;
        }
        int upSideSepIndex = getEndOfLine(txt, lineStartPosit - 1);//一行上の末尾
        int upSideStartIndex = getBeginningOfLine(txt, upSideSepIndex);// 一行上の先頭
        int currentColm = caretPosition - lineStartPosit;
        if ( currentColm > (upSideSepIndex - upSideStartIndex) ) {
            return upSideSepIndex;
        } else {
            return upSideStartIndex + currentColm;
        }
    }

    public static int getDownSide(final String txt, final int caretPosition) {
        int lineEndPosit = getEndOfLine(txt, caretPosition);
        if ( lineEndPosit == txt.length() ) {
            return -1;
        }
        int downSideSepIndex = getEndOfLine(txt, lineEndPosit + 1);
        int downSideStartIndex = getBeginningOfLine(txt, downSideSepIndex);
        int currentColm = caretPosition - getBeginningOfLine(txt, caretPosition);
        if ( currentColm > (downSideSepIndex - downSideStartIndex) ) {
            return downSideSepIndex;
        } else {
            return downSideStartIndex + currentColm;
        }
    }
}
