/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.impl.factory;

import net.nokok.twitduke.core.impl.twitter.TwitterStreamInstanceGeneratorImpl;
import twitter4j.TwitterStream;
import twitter4j.auth.AccessToken;

/**
 * ストリームを生成するstaticファクトリーメソッドが定義されています。
 * このクラスを使用するとストリームを実装の影響なしで生成することが出来ます。
 *
 * @author noko < nokok.kz at gmail.com >
 */
public class TwitterStreamFactory {

    /**
     * ストリームを新たに生成します
     *
     * @param accessToken アクセストークン
     *
     * @return 生成されたストリーム
     */
    public static TwitterStream newInstance(AccessToken accessToken) {
        TwitterStream twitterStream = new TwitterStreamInstanceGeneratorImpl().generate(accessToken);
        return twitterStream;
    }
}
