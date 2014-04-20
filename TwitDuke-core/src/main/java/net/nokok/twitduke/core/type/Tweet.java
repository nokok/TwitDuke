/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.type;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ツイート(String)のラッパクラスです
 * <p>
 * @author noko <nokok.kz at gmail.com>
 */
public class Tweet implements Cloneable, Serializable, Retrievable<String> {

    private final String text;

    public Tweet(String text) {
        if ( text == null ) {
            throw new NullPointerException("渡された文字列がnullです");
        }
        if ( text.length() > 140 ) {
            throw new IllegalArgumentException("140文字を超えています");
        }
        this.text = text;
    }

    @Override
    public String get() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null || (obj instanceof Tweet) ) {
            return false;
        }
        Tweet tweet = (Tweet) obj;
        return this.text.equals(tweet.text);
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Tweet.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalError(ex);
        }
    }
}
