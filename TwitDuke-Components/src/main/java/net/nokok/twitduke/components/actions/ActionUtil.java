/*
 * The MIT License
 *
 * Copyright 2014 satanabe1.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
