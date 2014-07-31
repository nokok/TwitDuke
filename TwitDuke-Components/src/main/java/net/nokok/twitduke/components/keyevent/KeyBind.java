package net.nokok.twitduke.components.keyevent;

import java.util.function.Consumer;

/**
 * Created by wtnbsts on 2014/07/23.
 */
public class KeyBind implements Comparable<KeyBind> {

    private String keyStroke;
    private String targetComponentName;
    /**
     * @see javax.swing.JComponent#WHEN_FOCUSED ...
     */
    private int targetComponentCondition;
    private Consumer<KeyBind> removeFunc;

    public String getKeyStroke() {
        return keyStroke;
    }

    public void setKeyStroke(final String keyStroke) {
        this.keyStroke = keyStroke;
    }

    public String getTargetComponentName() {
        return targetComponentName;
    }

    public void setTargetComponentName(final String targetComponentName) {
        this.targetComponentName = targetComponentName;
    }

    @Deprecated
    public int getTargetComponentCondition() {
        return targetComponentCondition;
    }

    public void setTargetComponentCondition(final int targetComponentCondition) {
        this.targetComponentCondition = targetComponentCondition;
    }

    public void setRemoveFunc(final Consumer<KeyBind> removeFunc) {
        this.removeFunc = removeFunc;
    }

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

        if ( targetComponentCondition != keyBind.targetComponentCondition ) {
            return false;
        }
        if ( keyStroke != null ? !keyStroke.equals(keyBind.keyStroke) : keyBind.keyStroke != null ) {
            return false;
        }
        if ( removeFunc != null ? !removeFunc.equals(keyBind.removeFunc) : keyBind.removeFunc != null ) {
            return false;
        }
        return !(targetComponentName != null ? !targetComponentName.equals(keyBind.targetComponentName)
                 : keyBind.targetComponentName != null);

    }

    @Override
    public int hashCode() {
        int result = keyStroke != null ? keyStroke.hashCode() : 0;
        result = 31 * result + (targetComponentName != null ? targetComponentName.hashCode() : 0);
        result = 31 * result + targetComponentCondition;
        result = 31 * result + (removeFunc != null ? removeFunc.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(final KeyBind keyBind) {
        if ( keyBind == null ) {
            return 1;
        }

        int namedif = (targetComponentName == null ? "" : targetComponentName).compareTo(
                keyBind.targetComponentName == null ? "" : keyBind.targetComponentName
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

        if ( targetComponentCondition != keyBind.targetComponentCondition ) {
            return targetComponentCondition - keyBind.targetComponentCondition;
        }

        if ( removeFunc == null && keyBind.removeFunc != null ) {
            return -1;
        } else if ( removeFunc != null && keyBind.removeFunc == null ) {
            return 1;
        }

        return 0;
    }
}
