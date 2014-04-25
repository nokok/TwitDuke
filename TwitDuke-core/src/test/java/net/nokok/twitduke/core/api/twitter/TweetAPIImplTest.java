/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.api.twitter;

import org.junit.Test;

/**
 *
 * @author noko <nokok.kz at gmail.com>
 */
public class TweetAPIImplTest {

    @Test(expected = NullPointerException.class)
    public void nullConstracterParameter() {
        new SendTweetAPIImpl(null);
    }
}
