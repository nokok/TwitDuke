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
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.nokok.twitduke.base.io.Reader;
import net.nokok.twitduke.base.io.Writer;
import net.nokok.twitduke.base.optional.OptionalUtil;
import net.nokok.twitduke.core.io.AccessTokenIOSelector;
import net.nokok.twitduke.core.io.AccountPath;
import net.nokok.twitduke.core.io.DirectoryHelper;
import net.nokok.twitduke.core.type.ScreenName;
import twitter4j.auth.AccessToken;

/**
 * TwitDukeのアカウントに関する操作をするクラスです。
 *
 */
class AccountManagerImpl implements AccountManager {

    @Override
    public void addAccount(AccessToken accessToken) {
        boolean isPrimary = !hasValidAccount();
        File dir = DirectoryHelper.createAccountDirectory(accessToken.getScreenName());
        if ( isPrimary ) {
            try {
                new File(dir, "primary").createNewFile();
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
        Writer<AccessToken> writer = new AccessTokenIOSelector(new ScreenName(accessToken.getScreenName())).getWriter();
        writer.write(accessToken);
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
        Reader<AccessToken> reader = new AccessTokenIOSelector(screenName).getReader();
        return reader.read();
    }

    private Optional<AccessToken> readAccessToken(String name) {
        return readAccessToken(new ScreenName(name));
    }

    @Override
    public List<ScreenName> readAccountList() {
        List<File> accountFiles = readAccountDirFileList();
        return accountFiles
            .stream()
            .map(f -> f.getName())
            .map(this::readAccessToken)
            .filter(OptionalUtil::isPresent)
            .map(OptionalUtil::get)
            .map(t -> t.getScreenName())
            .map(ScreenName::new)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Optional<AccessToken> readPrimaryAccount() {
        for ( File file : readAccountDirFileList() ) {
            File[] fs = file.listFiles();
            if ( fs == null ) {
                continue;
            }
            for ( File f : fs ) {
                if ( f.getName().equals("primary") ) {
                    return readAccessToken(file.getName());
                }
            }
        }
        return Optional.empty();
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
