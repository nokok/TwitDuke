package net.nokok.twitduke.util.url;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author noko
 *         短縮URLを作成する際に必要な共通メソッドをまとめたクラスです。
 *         このクラス単独では動作出来ません。
 */
public class CommonURLCreator {

    Boolean isValidURLFormat(String str) {
        String regex = "/^(https?|ftp)(:\\/\\/[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%#]+)$/";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(str);

        return matcher.find();
    }

    Boolean checkURL(String url) {
        if (url.length() == 0) {
            throw new IllegalArgumentException("URLが空です");
        }
        if (isValidURLFormat(url) == false) {
            throw new IllegalArgumentException("不正なURLです。URL:" + url);
        }
        return true;
    }
}
