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
package net.nokok.twitduke.pluginsupport.boot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import javax.script.Invocable;
import javax.script.ScriptException;
import net.nokok.twitduke.pluginsupport.AbstractPlugin;
import net.nokok.twitduke.pluginsupport.PluginInfo;

/**
 * 起動時に実行されるプラグインです
 */
public class BootPlugin extends AbstractPlugin implements BootEventListener, PluginInfo {

    private final Reader reader;
    private final PluginInfo plugin;

    public BootPlugin(String path) throws FileNotFoundException {
        super();
        this.reader = new FileReader(path);
        try {
            scriptEngine.eval("var imports = new JavaImporter(java.io,java.lang);");
            scriptEngine.eval("var boot = {}");
            scriptEngine.eval(reader);

        } catch (ScriptException ignored) {

        }
        plugin = (PluginInfo) scriptEngine.get("plugin");
    }

    @Override
    public String author() {
        return plugin.author();
    }

    @Override
    public void completed() {
        invoke("completed");
    }

    @Override
    public String description() {
        return plugin.description();
    }

    @Override
    public String name() {
        return plugin.name();
    }

    @Override
    public void starting() {
        invoke("starting");
    }

    @Override
    public String version() {
        return plugin.version();
    }

    private void invoke(String methodName) {
        try {
            Invocable invocable = (Invocable) scriptEngine;
            invocable.invokeMethod(scriptEngine.get("boot"), methodName, "");
        } catch (NoSuchMethodException | ScriptException ignored) {

        }
    }
}
