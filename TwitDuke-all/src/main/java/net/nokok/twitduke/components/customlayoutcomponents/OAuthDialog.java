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
package net.nokok.twitduke.components.customlayoutcomponents;

import net.nokok.twitduke.components.Dialog;
import net.nokok.twitduke.components.DialogResultListener;
import net.nokok.twitduke.components.basic.TextFieldDialog;

/**
 * OAuthダイアログはOAuth認証時に表示されるPINの入力画面を担当するクラスです。
 * このクラスはあくまでViewのみでPIN入力関連の実際の処理はDialogResultListener実装クラスで
 * 行われます。
 */
public class OAuthDialog implements Dialog<String> {

    private final Dialog<String> dialog = new TextFieldDialog("認証して下さい", "ログイン後、表示された数字を半角数字で入力して下さい");

    @Override
    public void dispose() {
        dialog.dispose();
    }

    @Override
    public void setDialogResultListener(DialogResultListener<String> dialogResultListener) {
        this.dialog.setDialogResultListener(dialogResultListener);
    }

    @Override
    public void show() {
        dialog.show();
    }
}
