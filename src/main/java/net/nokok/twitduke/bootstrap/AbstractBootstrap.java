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
package net.nokok.twitduke.bootstrap;

import java.util.ArrayList;
import java.util.Optional;
import net.nokok.twitduke.core.api.account.AccountManager;
import net.nokok.twitduke.core.impl.factory.AccountManagerFactory;
import twitter4j.auth.AccessToken;

/**
 * 起動可能なオブジェクトです。
 */
abstract public class AbstractBootstrap {

    protected final ArrayList<AccessToken> accessTokenList;
    protected final AccountManager accountManager;

    public AbstractBootstrap() {
        accountManager = AccountManagerFactory.newInstance();
        ArrayList<Optional<AccessToken>> accessTokenListOptional = accountManager.getAccessTokenList();
        accessTokenList = new ArrayList<>(accessTokenListOptional.size());
        accessTokenListOptional
                .stream()
                .filter(token -> token.isPresent())
                .forEach(token -> accessTokenList.add(token.get()));
    }

    /**
     * 起動処理を開始します
     */
    abstract public void startInitialize();
}
