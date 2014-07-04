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
package net.nokok.twitduke.xsi.shindanmaker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.Properties;
import net.nokok.twitduke.core.io.PropertyReader;

public class ShindanmakerProperty {

    private final Properties properties;

    public ShindanmakerProperty() {
        Optional<Properties> oProp = new PropertyReader(ShindanmakerConfig.SHINDANMAKER_CONFIG_FILE)
                .read();
        if ( oProp.isPresent() ) {
            this.properties = oProp.get();
        } else {
            this.properties = new Properties();
        }
    }

    public Optional<String> name() {
        return Optional.ofNullable(properties.getProperty("name"));
    }

    public void writeName(String name) {
        properties.setProperty("name", name);
        try {
            properties.store(new FileWriter(ShindanmakerConfig.SHINDANMAKER_CONFIG_FILE), null);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
