package net.nokok.twitduke.components.keyevent;

import javafx.scene.input.KeyCombination;

/**
 * Created by wtnbsts on 2014/07/23.
 */
public class KeyBind implements Comparable<KeyBind> {

    /**
     * キー入力の文字列表現
     * 例) ctrl+a
     */
    private KeyCombination keyStroke;
    /**
     * キーボードショートカットが有効になるコンポーネントの条件 (現在はクラス名の完全一致)
     * 例) net.nokok.twitduke.components.TWButton
     */
    private String selector;

    /**
     * キー入力の文字列表現を取得する
     *
     * @return キー入力の文字列表現
     */
    public KeyCombination getKeyStroke() {
        return keyStroke;
    }

    /**
     * キー入力の文字列表現を設定する
     *
     * @param keyStroke キー入力の文字列表現
     */
    public void setKeyStroke(final KeyCombination keyStroke) {
        this.keyStroke = keyStroke;
    }

    /**
     * キーボードショートカットが有効になるコンポーネントの条件を取得する
     *
     * @return キーボードショートカットが有効になるコンポーネントの条件
     */
    public String getSelector() {
        return selector;
    }

    /**
     * キーボードショートカットが有効になるコンポーネントの条件を取得する
     *
     * @param selector キーボードショートカットが有効になるコンポーネントの条件
     */
    public void setSelector(final String selector) {
        this.selector = selector;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final KeyBind other = (KeyBind) obj;
        return true;
    }

    @Override
    public int compareTo(final KeyBind keyBind) {
        if ( keyBind == null ) {
            throw new NullPointerException();
        }

        int namedif = (selector == null ? "" : selector).compareTo(
                keyBind.selector == null ? "" : keyBind.selector
        );
        if ( namedif != 0 ) {
            return namedif;
        }

        int keydif = (keyStroke == null ? "" : keyStroke.getName()).compareTo(
                keyBind.keyStroke == null ? "" : keyBind.keyStroke.getName()
        );
        if ( keydif != 0 ) {
            return keydif;
        }

        return 0;
    }
}
