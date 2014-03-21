package net.nokok.twitduke.util;

import java.awt.Image;
import javax.swing.ImageIcon;
import net.nokok.twitduke.main.Config;

/**
 * このクラスは画像サイズを変更する機能を提供します
 */
public class ImageUtil {

    /**
     * サムネイルに適したサイズ(横256px)の画像を生成します。
     * 画像の縦横比は保持されます。渡された画像の横方向のサイズが256px未満の場合は、256pxまで拡大されます
     *
     * @param image サムネイルを生成したい元の画像
     * @return 横方向のサイズが256pxの画像
     */
    public static ImageIcon createThumbnail(ImageIcon image) {
        return resizeImage(image, Config.ComponentSize.THUMBNAIL_WIDTH);
    }

    /**
     * 渡された画像を任意の幅でリサイズします。
     * 画像の縦横比は保持されます。渡された画像の横方向のサイズが指定したサイズ未満の場合は、そのサイズまで拡大されます。
     *
     * @param image リサイズしたい元の画像
     * @param width 横方向のサイズ
     * @return 横方向のサイズが指定したサイズの画像
     */
    private static ImageIcon resizeImage(ImageIcon image, int width) {
        return new ImageIcon(image.getImage().getScaledInstance(width, -1, Image.SCALE_SMOOTH));
    }
}
