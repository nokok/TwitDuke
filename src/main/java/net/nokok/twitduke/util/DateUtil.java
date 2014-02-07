package net.nokok.twitduke.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {
    private static final Calendar calendar = new GregorianCalendar(Locale.JAPANESE);

    private DateUtil() {

    }

    /**
     * 現在時刻の時の部分を24時間表記で返します
     * 例:   1:35 -> 1
     * 例:   15:20 -> 15
     *
     * @return 24時間表記の時
     */
    public static int nowHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}
