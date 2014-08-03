package net.nokok.twitduke.components.keyevent;

import java.util.function.Consumer;

/**
 * Created by wtnbsts on 2014/07/23.
 */
public class KeyBind implements Comparable<KeyBind> {

    /**
     * キー入力の文字列表現
     * 例) ctrl+a
     */
    private String keyStroke;
    /**
     * キーボードショートカットが有効になるコンポーネントの条件 (現在はクラス名の完全一致)
     * 例) net.nokok.twitduke.components.TWButton
     */
    private String selector;
    /**
     * コンポーネントから、キーボードショートカットを無効化する為の処理
     */
    private Consumer<KeyBind> removeFunc;

    /**
     * キー入力の文字列表現を取得する
     *
     * @return キー入力の文字列表現
     */
    public String getKeyStroke() {
        return keyStroke;
    }

    /**
     * キー入力の文字列表現を設定する
     *
     * @param keyStroke キー入力の文字列表現
     */
    public void setKeyStroke(final String keyStroke) {
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

    /**
     * コンポーネントから、キーボードショートカットを無効化する為の処理を設定する
     *
     * @param removeFunc コンポーネントから、キーボードショートカットを無効化する為の処理
     */
    public void setRemoveFunc(final Consumer<KeyBind> removeFunc) {
        this.removeFunc = removeFunc;
    }

    /**
     * コンポーネントから、キーボードショートカットを無効化する為の処理を実行する
     */
    public void remove() {
        if ( removeFunc != null ) {
            removeFunc.accept(this);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        KeyBind keyBind = (KeyBind) o;

        if ( keyStroke != null ? !keyStroke.equals(keyBind.keyStroke) : keyBind.keyStroke != null ) {
            return false;
        }
        if ( removeFunc != null ? !removeFunc.equals(keyBind.removeFunc) : keyBind.removeFunc != null ) {
            return false;
        }
        return !(selector != null ? !selector.equals(keyBind.selector)
                 : keyBind.selector != null);

    }

    @Override
    public int hashCode() {
        int result = keyStroke != null ? keyStroke.hashCode() : 0;
        result = 31 * result + (selector != null ? selector.hashCode() : 0);
        result = 31 * result + (removeFunc != null ? removeFunc.hashCode() : 0);
        return result;
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

        int keydif = (keyStroke == null ? "" : keyStroke).compareTo(
                keyBind.keyStroke == null ? "" : keyBind.keyStroke
        );
        if ( keydif != 0 ) {
            return keydif;
        }

        if ( removeFunc == null && keyBind.removeFunc != null ) {
            return -1;
        } else if ( removeFunc != null && keyBind.removeFunc == null ) {
            return 1;
        }

        return 0;
    }
}
