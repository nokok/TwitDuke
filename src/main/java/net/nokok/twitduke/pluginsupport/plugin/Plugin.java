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
package net.nokok.twitduke.pluginsupport.plugin;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import net.nokok.twitduke.pluginsupport.InvocablePlugin;

public class Plugin implements PluginInfo, ReadablePlugin, InvocablePlugin {

    private final PluginInfo pluginInfo;
    private final String pluginPath;
    private final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");

    public Plugin(String pluginPath, PluginInfo pluginInfo) {
        this.pluginInfo = pluginInfo;
        this.pluginPath = pluginPath;
    }

    @Override
    public String author() {
        return pluginInfo.author();
    }

    @Override
    public String description() {
        return pluginInfo.description();
    }

    @Override
    public Reader getReader() {
        try {
            return new FileReader(pluginPath);
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Invocable invocable() {
        return (Invocable) scriptEngine;
    }

    @Override
    public void invokeMethod(String objectName, String methodName, Object... args) {
        Invocable invocable = invocable();
        try {
            invocable.invokeMethod(scriptEngine.get(objectName), methodName, args);
        } catch (NoSuchMethodException | ScriptException ignored) {

        }
    }

    public ScriptEngine scriptEngine() {
        return scriptEngine;
    }

    @Override
    public String name() {
        return pluginInfo.name();
    }

    @Override
    public String version() {
        return pluginInfo.version();
    }
}
