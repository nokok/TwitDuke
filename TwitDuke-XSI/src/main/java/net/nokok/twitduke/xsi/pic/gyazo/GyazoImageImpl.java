package net.nokok.twitduke.xsi.pic.gyazo;

import java.net.URL;
import net.nokok.twitduke.xsi.pic.AbstractPictureFetcher;
import net.nokok.twitduke.xsi.pic.PictureFetcher;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GyazoImageImpl extends AbstractPictureFetcher implements PictureFetcher {

    public GyazoImageImpl(URL url) {
        super(url);
    }

    @Override
    public String selectImage(Document document) {
        Element image = document.getElementsByTag("img").get(2);
        return image.attr("src");
    }

}
