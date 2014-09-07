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

import net.nokok.twitduke.core.event.BootEventListener;
import net.nokok.twitduke.pluginsupport.PluginObjectName;
import net.nokok.twitduke.pluginsupport.plugin.Plugin;
import net.nokok.twitduke.pluginsupport.plugin.PluginRegistrable;

public class BootEventRunner implements BootEventListener, PluginRegistrable {

    private final EventRunner runner = new EventRunner(PluginObjectName.BOOT);

    @Override
    public void addPlugin(Plugin p) {
        runner.addPlugin(p);
    }

    @Override
    public void completed() {
        runner.invokeAll("completed");
    }

    @Override
    public void starting() {
        runner.invokeAll("starting");
    }
}
