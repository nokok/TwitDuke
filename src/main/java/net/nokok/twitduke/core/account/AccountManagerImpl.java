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
package net.nokok.twitduke.core.account;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.nokok.twitduke.core.type.ScreenName;
import twitter4j.auth.AccessToken;

/**
 * TwitDukeのアカウントに関する操作をするクラスです。
 *
 */
public class AccountManagerImpl implements AccountManager {

    @Override
    public void addAccount(AccessToken accessToken) {
        AccessTokenWriter writer = new AccessTokenPropertyWriter();
        writer.writeAccessToken(accessToken);
    }

    @Override
    public boolean hasAccount(ScreenName screenName) {
        return DirectoryHelper.getAccountDirectory(screenName.get()).exists();
    }

    @Override
    public boolean hasValidAccount() {
        return !readAccountDirFileList().isEmpty();
    }

    @Override
    public Optional<AccessToken> readAccessToken(ScreenName screenName) {
        AccessTokenReader2 reader = new AccessTokenPropertyReader();
        return reader.readAccessToken(screenName);
    }

    @Override
    public List<ScreenName> readAccountList() {
        List<File> accountFiles = readAccountDirFileList();
        return accountFiles
                .stream()
                .map(f -> new ScreenName(f.getName()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<File> readAccountDirFileList() {
        File accountDir = new File(AccountPath.ACCOUNTS_PATH);
        File[] files = accountDir.listFiles();
        if ( files == null ) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(files);
    }

    @Override
    public void removeAccount(AccessToken accessToken) {
        File accountDir = DirectoryHelper.getAccountDirectory(accessToken.getScreenName());
        accountDir.delete();
    }

    @Override
    public void removeAccount(ScreenName screenName) {
        File accountDir = DirectoryHelper.getAccountDirectory(screenName.get());
        accountDir.delete();
    }

}
