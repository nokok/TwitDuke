package net.nokok.twitduke.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {
    public static final int      UPDATE_INTERVAL = 1800000; //30分
    private static      long     lastUpdated     = System.currentTimeMillis();
    private static      Calendar calendar        = new GregorianCalendar(Locale.JAPANESE);

    private DateUtil() {

    }

    /**
     * 現在時刻の時の部分を24時間表記で返します。また、結果は30分キャッシュされます
     * 例:   1:35 -> 1
     * 例:   15:20 -> 15
     *
     * @return 24時間表記の時
     */
    public static int nowHour() {
        long current = System.currentTimeMillis();
        if ((lastUpdated - current) > UPDATE_INTERVAL) {
            calendar = new GregorianCalendar(Locale.JAPANESE);
            lastUpdated = current;
        }
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 現在時刻が0〜5時かどうかを返します
     *
     * @return 0〜5時ならtrue
     */
    public static boolean isMealTerroTime() {
        return nowHour() < 6;
    }
}
