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
package net.nokok.twitduke.core.twitter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;
import twitter4j.auth.AccessToken;

/**
 * ツイートのステータスを元に様々なオブジェクトを生成します。
 *
 */
public class StatusParser {

    private final Twitter twitter;

    /**
     * 指定されたTwitterオブジェクトでStatusParserを生成します
     *
     * @param twitter Twitterオブジェクト
     */
    public StatusParser(Twitter twitter) {
        this.twitter = twitter;
    }

    /**
     * 指定されたアクセストークンでStatusParserを生成します
     *
     * @param accessToken アクセストークン
     */
    public StatusParser(AccessToken accessToken) {
        this.twitter = new TwitterFactory().getInstance(accessToken);
    }

    /**
     * 渡されたステータスのリプライをたどってリストにします。
     *
     * @param status リプライをたどるステータス
     *
     * @return リプライをたどったリスト
     */
    public LinkedList<Status> createReplyChain(Status status) {
        LinkedList<Status> linkedList = new LinkedList<>();
        Status currentStatus = status;
        long currentStatusId;
        while ( (currentStatusId = currentStatus.getInReplyToStatusId()) > 0 ) {
            linkedList.add(currentStatus);
            try {
                currentStatus = twitter.showStatus(currentStatusId);
            } catch (TwitterException e) {
                return linkedList;
            }
        }
        return linkedList;
    }

    /**
     * ステータスIDからIterableなステータスを生成します
     *
     * @param statusId ステータスID
     *
     * @return Iterableなステータス
     */
    public Iterable<Status> createReplyChain(long statusId) {
        LinkedList<Status> linkedList = new LinkedList<>();
        try {
            Status status = twitter.showStatus(statusId);
            linkedList.add(status);
            linkedList.addAll(createReplyChain(status));
            return linkedList;
        } catch (TwitterException ex) {
            return linkedList;
        }
    }

    /**
     * ステータスに含まれるURLエンティティをリストにします。
     *
     * @param status ステータス
     *
     * @return URLエンティティのリスト
     */
    public List<URLEntity> createURLEntityList(Status status) {
        return Stream.of(status.getURLEntities())
            .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * ステータスに含まれるメンションエンティティをリストにします
     *
     * @param status ステータス
     *
     * @return メンションエンティティのリスト
     */
    public List<UserMentionEntity> createUserMentionEntityList(Status status) {
        return Stream.of(status.getUserMentionEntities())
            .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * ステータスに含まれるハッシュタグエンティティをリストにします
     *
     * @param status ステータス
     *
     * @return ハッシュタグエンティティのリスト
     */
    public List<HashtagEntity> createHashtagEntityList(Status status) {
        return Stream.of(status.getHashtagEntities())
            .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * ステータスに含まれるメディアエンティティをリストにします
     *
     * @param status ステータス
     *
     * @return メディアエンティティのリスト
     */
    public List<MediaEntity> createMediaEntityList(Status status) {
        return Stream.of(status.getMediaEntities())
            .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * ステータスに含まれるシンボルエンティティをリストにします
     *
     * @param status ステータス
     *
     * @return シンボルエンティティのリスト
     */
    public List<SymbolEntity> createSymbolEntityList(Status status) {
        return Stream.of(status.getSymbolEntities())
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
