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
package net.nokok.twitduke.core.impl.account;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.nokok.twitduke.core.api.account.AccessTokenReader;
import twitter4j.auth.AccessToken;

/**
 * AccessTokenをデシリアライズする事によって読み込むクラスです
 *
 */
public class AccessTokenDeserializer implements AccessTokenReader {

    /**
     * 利用可能なAccessTokenをOptionalでラップされたオブジェクトのリストの形で返します。
     *
     * @return Optionalなアクセストークン
     */
    @Override
    public ArrayList<Optional<AccessToken>> getAccessTokenList() {
        File[] files = new File(".").listFiles();
        ArrayList<Optional<AccessToken>> list;
        list = Stream.of(files)
                .filter(p -> p.isFile())
                .map(f -> getAccessToken(f.getAbsolutePath()))
                .filter(e -> e.isPresent())
                .collect(Collectors.toCollection(ArrayList::new));
        return list;
    }

    /**
     * 指定したファイル名のファイルを読み込み、AccessTokenに変換して返します。
     * 失敗した場合Optional.empty()が返ります
     *
     * @param fileName 読み込むファイル
     *
     * @return 読み込みに成功した場合はAccessTokenオブジェクト
     *         失敗した場合はOptional.empty();
     */
    public Optional<AccessToken> getAccessToken(String fileName) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            AccessToken mayBeAccessToken = (AccessToken) objectInputStream.readObject();
            return Optional.of(mayBeAccessToken);
        } catch (ClassNotFoundException | IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AccessToken> readFirstAccessToken() {
        ArrayList<Optional<AccessToken>> accessTokenList = getAccessTokenList();
        Optional<Optional<AccessToken>> mayBeAccessTokenOptional
                                        = accessTokenList.stream().findFirst();
        if ( mayBeAccessTokenOptional.isPresent() ) {
            return mayBeAccessTokenOptional.get();
        } else {
            return Optional.empty();
        }
    }
}
