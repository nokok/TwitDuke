/*
 * The MIT License
 *
 * Copyright 2014 noko
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
package net.nokok.twitduke.base.event;

import java.awt.Dimension;

/**
 * ウィンドウに関するイベントが取得できます
 *
 */
public interface WindowEventListener extends ListenerRegistrable<WindowEventListener> {

    /**
     * サイズ変更完了時に呼ばれます
     *
     * @param d 変更後のサイズ
     */
    void sizeChanged(Dimension d);

    /**
     * ウィンドウのタイトルが変更された時に呼ばれます
     *
     * @param title 変更後のタイトル
     */
    void titleChanged(String title);

    /**
     * ウィンドウが開かれようとする時に呼ばれます。
     */
    void opening();

    /**
     * ウィンドウが開かれた時に呼ばれます
     */
    void opened();

    /**
     * ウィンドウが閉じられようとする時に呼ばれます
     */
    void closing();

    /**
     * ウィンドウが閉じられた時に呼ばれます
     */
    void closed();
}
