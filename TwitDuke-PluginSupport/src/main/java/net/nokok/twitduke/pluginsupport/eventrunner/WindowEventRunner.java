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
package net.nokok.twitduke.pluginsupport.eventrunner;

import java.awt.Dimension;
import java.util.Collections;
import java.util.List;
import net.nokok.twitduke.core.event.WindowEventListener;
import net.nokok.twitduke.pluginsupport.PluginObjectName;
import net.nokok.twitduke.pluginsupport.plugin.Plugin;
import net.nokok.twitduke.pluginsupport.plugin.PluginRegistrable;

public class WindowEventRunner implements WindowEventListener, PluginRegistrable {

    private final EventRunner runner = new EventRunner(PluginObjectName.WINDOW);
    private final List<WindowEventListener> listeners = Collections.emptyList();

    @Override
    public void add(WindowEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void addPlugin(Plugin p) {
        runner.addPlugin(p);
    }

    @Override
    public void closed() {
        listeners.parallelStream().forEach(l -> l.closed());
        runner.invokeAll("closed");
    }

    @Override
    public void closing() {
        listeners.parallelStream().forEach(l -> l.closing());
        runner.invokeAll("closing");
    }

    @Override
    public void opened() {
        listeners.parallelStream().forEach(l -> l.opened());
        runner.invokeAll("opened");
    }

    @Override
    public void opening() {
        listeners.parallelStream().forEach(l -> l.opening());
        runner.invokeAll("opening");
    }

    @Override
    public void sizeChanged(Dimension d) {
        listeners.parallelStream().forEach(l -> l.sizeChanged(d));
        runner.invokeAll("sizeChanged", d);
    }

    @Override
    public void titleChanged(String title) {
        listeners.parallelStream().forEach(l -> l.titleChanged(title));
        runner.invokeAll("titleChanged", title);
    }

}
