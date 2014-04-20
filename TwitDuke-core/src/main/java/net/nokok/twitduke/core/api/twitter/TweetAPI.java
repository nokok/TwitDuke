/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.api.twitter;

import net.nokok.twitduke.core.type.Tweet;

/**
 *
 * @author noko <nokok.kz at gmail.com>
 */
public interface TweetAPI {

    /**
     * ツイートを送信します。
     * <p>
     * @param tweet
     */
    void sendTweet(Tweet tweet);
}
