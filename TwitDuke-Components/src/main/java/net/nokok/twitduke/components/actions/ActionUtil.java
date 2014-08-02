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

    /**
     * 文字列と、その文字列中で選択されている場所を渡すと、その選択されている場所の行末の番号を計算する
     *
     * @param txt           文字列
     * @param caretPosition 文字列中で選択されている場所
     *
     * @return 行末の番号
     */
    public static int getEndOfLine(final String txt, final int caretPosition) {
        final int lineSepPosition = txt.indexOf(System.lineSeparator(), caretPosition);
        return lineSepPosition < 0 ? txt.length() : lineSepPosition;
    }

    /**
     * 文字列と、その文字列中で選択されている場所を渡すと、その選択されている場所の行頭の番号を計算する
     *
     * @param txt           文字列
     * @param caretPosition 文字列中で選択されている場所
     *
     * @return 行頭の番号
     */
    public static int getBeginningOfLine(final String txt, final int caretPosition) {
        final String subtext = txt.substring(0, caretPosition);
        final int lineSepPosition = subtext.lastIndexOf(System.lineSeparator(), caretPosition);
        return lineSepPosition < 0 ? 0 : lineSepPosition + System.lineSeparator().length();
    }

    /**
     * 文字列と、その文字列中で選択されている場所を渡すと、その選択されている場所の上にある文字の番号を計算する
     *
     * @param txt           文字列
     * @param caretPosition 文字列中で選択されている場所
     *
     * @return 上にある文字の番号
     */
    public static int getUpSide(final String txt, final int caretPosition) {
        final int lineStartPosit = getBeginningOfLine(txt, caretPosition);
        if ( lineStartPosit == 0 ) {
            // 行の先頭が0番目の文字なら、上に行は存在しない
            return -1;
        }
        final int upSideSepIndex = getEndOfLine(txt, lineStartPosit - 1);//一行上の末尾
        final int upSideStartIndex = getBeginningOfLine(txt, upSideSepIndex);// 一行上の先頭
        final int currentColm = caretPosition - lineStartPosit;
        if ( currentColm > (upSideSepIndex - upSideStartIndex) ) {
            return upSideSepIndex;
        } else {
            return upSideStartIndex + currentColm;
        }
    }

    /**
     * 文字列と、その文字列中で選択されている場所を渡すと、その選択されている場所の下にある文字の番号を計算する
     *
     * @param txt           文字列
     * @param caretPosition 文字列中で選択されている場所
     *
     * @return 下にある文字の番号
     */
    public static int getDownSide(final String txt, final int caretPosition) {
        final int lineEndPosit = getEndOfLine(txt, caretPosition);
        if ( lineEndPosit == txt.length() ) {
            return -1;
        }
        final int downSideSepIndex = getEndOfLine(txt, lineEndPosit + 1);
        final int downSideStartIndex = getBeginningOfLine(txt, downSideSepIndex);
        final int currentColm = caretPosition - getBeginningOfLine(txt, caretPosition);
        if ( currentColm > (downSideSepIndex - downSideStartIndex) ) {
            return downSideSepIndex;
        } else {
            return downSideStartIndex + currentColm;
        }
    }
}
