/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.impl.factory;

import net.nokok.twitduke.core.api.account.AccountManager;
import net.nokok.twitduke.core.impl.account.AccountManagerImpl;

/**
 * アカウントマネージャーを生成するstaticファクトリーメソッドが定義されています。
 * このクラスを使用するとアカウントマネージャーを実装の影響なしで生成することが出来ます。
 *
 * @author noko < nokok.kz at gmail.com >
 */
public class AccountManagerFactory {

    /**
     * アカウントマネージャーを新たに生成します
     *
     * @return 新たに生成されたアカウントマネージャー
     */
    public static AccountManager createNewInstance() {
        AccountManager accountManager = new AccountManagerImpl();
        return accountManager;
    }
}
