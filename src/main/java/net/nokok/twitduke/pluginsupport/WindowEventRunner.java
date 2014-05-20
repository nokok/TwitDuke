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
package net.nokok.twitduke.pluginsupport;

import net.nokok.twitduke.pluginsupport.ObjectName;
import java.awt.Dimension;
import net.nokok.twitduke.pluginsupport.plugin.Plugin;
import net.nokok.twitduke.pluginsupport.plugin.PluginRegistrable;
import net.nokok.twitduke.pluginsupport.window.WindowEventListener;

public class WindowEventRunner implements WindowEventListener, PluginRegistrable {

    private final EventRunner runner = new EventRunner(ObjectName.WINDOW);

    @Override
    public void addPlugin(Plugin p) {
        runner.addPlugin(p);
    }

    @Override
    public void closed() {
        runner.invokeAll("closed");
    }

    @Override
    public void closing() {
        runner.invokeAll("closing");
    }

    @Override
    public void sizeChanged(Dimension d) {
        runner.invokeAll("sizeChanged", d);
    }

    @Override
    public void titleChanged(String title) {
        runner.invokeAll("titleChanged", title);
    }

}
