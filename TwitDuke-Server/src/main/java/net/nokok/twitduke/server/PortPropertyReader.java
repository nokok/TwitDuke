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
package net.nokok.twitduke.server;

import java.util.Properties;
import net.nokok.twitduke.core.io.PropertyReader;
import net.nokok.twitduke.core.io.PropertyWriter;
import net.nokok.twitduke.core.type.Port;

/**
 * ポートの設定情報が保存されたプロパティを読み込みます
 */
class PortPropertyReader {

    private final Properties DEFAULT_PROPERTIES = new Properties();
    private final Port port;

    PortPropertyReader() {
        //Propertiesは文字列で表現する必要があるためここでPortオブジェクトは用いない
        DEFAULT_PROPERTIES.put(WebServicePropertyKey.PORT_KEY, "8192");
        PropertyReader reader = new PropertyReader(WebConfig.WEB_CONFIG_PATH_STR);
        if ( reader.read().isPresent() ) {
            PropertyWriter writer = new PropertyWriter(WebConfig.WEB_CONFIG_PATH_STR);
            writer.write(DEFAULT_PROPERTIES, WebConfig.WEB_CONFIG_FILE_NAME);
        }
        Properties prop = reader.read().orElse(DEFAULT_PROPERTIES);
        String portString = prop.getProperty(WebServicePropertyKey.PORT_KEY);
        port = new Port(portString);
    }

    int getPort() {
        return port.get();
    }
}
