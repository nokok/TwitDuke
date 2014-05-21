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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import net.nokok.twitduke.core.twitter.AsyncTwitterInstanceGeneratorImpl;
import net.nokok.twitduke.core.twitter.UpdateProfileImpl;
import net.nokok.twitduke.core.type.UncheckedScriptException;
import net.nokok.twitduke.pluginsupport.plugin.Plugin;
import net.nokok.twitduke.pluginsupport.util.FileToPlugin;
import twitter4j.StatusAdapter;
import twitter4j.StatusListener;
import twitter4j.auth.AccessToken;

public class PluginManager {

    private final List<Plugin> plugins = new ArrayList<>();
    private AccessToken accessToken;

    public PluginManager(String pluginDirectoryPath) {
        File[] files = new File(pluginDirectoryPath).listFiles();
        Stream.of(files)
                .filter(f -> f.getName().endsWith(".js"))
                .map(FileToPlugin::encode)
                .filter(p -> p.isPresent())
                .map(p -> p.get())
                .forEach(plugins::add);
    }

    public PluginManager(String pluginDirectoryPath, AccessToken accessToken) {
        this(pluginDirectoryPath);
        this.accessToken = accessToken;
        resolvePermission();
    }

    private void resolvePermission() {
        for ( Plugin plugin : plugins ) {
            ScriptEngine scriptEngine = plugin.scriptEngine();
            try {
                scriptEngine.eval("var " + ObjectName.BOOT + " = {};");
                scriptEngine.eval("var " + ObjectName.WINDOW + " = {};");
                scriptEngine.eval("var " + ObjectName.WINDOW_TITLE + " = '';");
                scriptEngine.eval("var " + ObjectName.STREAM + " = {};");
            } catch (ScriptException e) {
                throw new UncheckedScriptException(e);
            }
            scriptEngine.put(ObjectName.PROFILE, new UpdateProfileImpl(accessToken));
            scriptEngine.put(ObjectName.TWITTER_API, new AsyncTwitterInstanceGeneratorImpl().generate(accessToken));
        }
    }

    public StatusListener getStatusListener() {
        return new StatusAdapter();
    }
}
