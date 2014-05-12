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
package net.nokok.twitduke.components;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.text.JTextComponent;
import net.nokok.twitduke.components.basic.TWTextArea;
import net.nokok.twitduke.core.api.twitter.SendTweetAPI;
import net.nokok.twitduke.core.type.Tweet;

/**
 * ツイート入力用のテキストエリアです。
 *
 */
public class TweetTextArea extends TWTextArea {

    private static final long serialVersionUID = -7817374896981739389L;

    private final SendTweetAPI tweetAPI;

    /**
     * Shift+Enterキーでツイート送信可能なテキストエリアを生成します。
     * ツイート送信の処理は渡されたSendTweetAPI実装クラスに委譲されます。
     * デフォルトキーショートカット(Shift + Enter)でこのテキストエリアに入力された文字列を送信できます。
     * このクラスで文字列周りのエラー処理はしません。
     *
     * @param tweetAPI ツイート送信可能なオブジェクト
     *
     * @exception NullPointerException 渡されたSendTweetAPIオブジェクトがnullだった場合
     */
    public TweetTextArea(SendTweetAPI tweetAPI) {
        if ( tweetAPI == null ) {
            throw new NullPointerException("SendTweetAPIインターフェースがnullです");
        }
        this.tweetAPI = tweetAPI;
        addKeyListener(new DefaultSendTweetKeyListener());
    }

    /**
     * 独自のキーショートカットでツイート送信可能なテキストエリアを生成します。
     * ツイート送信の処理は渡されたSendTweetAPI実装クラスに委譲されます。
     * このクラスで文字列周りのエラー処理はしません。
     *
     * @param tweetAPI    ツイート送信可能なオブジェクト
     * @param keyListener 独自のキーリスナー
     *
     * @exception NullPointerException 渡されたSendTweetAPIオブジェクトがnullの場合
     */
    public TweetTextArea(SendTweetAPI tweetAPI, KeyListener keyListener) {
        this(tweetAPI);
        addKeyListener(keyListener);
    }

    private class DefaultSendTweetKeyListener extends KeyAdapter {

        private boolean isShiftKeyPressed;
        private boolean isEnterKeyPressed;

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if ( keyEvent.getKeyCode() == KeyEvent.VK_ENTER ) {
                isEnterKeyPressed = true;
            }
            if ( keyEvent.getKeyCode() == KeyEvent.VK_SHIFT ) {
                isShiftKeyPressed = true;
            }
            if ( isShiftKeyPressed && isEnterKeyPressed ) {
                JTextComponent component = (JTextComponent) keyEvent.getComponent();
                String text = component.getText();
                tweetAPI.sendTweet(new Tweet(text));
                component.setText("");
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            isShiftKeyPressed = false;
            isEnterKeyPressed = false;
        }
    }
}
