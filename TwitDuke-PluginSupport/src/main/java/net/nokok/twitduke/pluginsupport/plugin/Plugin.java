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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.Properties;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import net.nokok.twitduke.pluginsupport.InvocablePlugin;
import net.nokok.twitduke.pluginsupport.UncheckedScriptException;

/**
 * プラグインオブジェクトを表現します。
 */
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

    /**
     * プラグインを実行します。
     *
     * @exception UncheckedScriptException プラグインの実行中にエラーが発生した場合
     */
    public void eval() {
        try {
            scriptEngine.eval(new FileReader(pluginPath));
        } catch (ScriptException | FileNotFoundException e) {
            throw new UncheckedScriptException(e);
        }
    }

    /**
     * @return プラグインのInvocableインターフェース実装オブジェクト
     */
    public Invocable invocable() {
        return (Invocable) scriptEngine;
    }

    @Override
    public void invokeMethod(String objectName, String methodName, Object... args) {
        Invocable invocable = invocable();
        try {
            invocable.invokeMethod(scriptEngine.get(objectName), methodName, args);
        } catch (NoSuchMethodException | ScriptException ignored) {
            //プラグイン側は実装していないメソッド及び関数がほとんどなので無視
            //この部分を修正したらEventRunnerのinvokeAllを修正したほうが良いかも
        }
    }

    /**
     * @return ScriptEngineオブジェクト
     */
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

    /**
     * ファイルオブジェクトからプラグインオブジェクトに変換するコンバータメソッドです。
     * 変換に失敗した場合Optional.empty()が返ります。
     *
     * @param file ファイル
     *
     * @return プラグインオブジェクトまたはOptional.empty()
     */
    public static Optional<Plugin> encode(File file) {
        String propertyPath = file.getAbsolutePath().replace(".js", ".properties");
        final Properties properties = new Properties();
        Plugin plugin;
        try {
            properties.load(new FileReader(propertyPath));
            plugin = new Plugin(file.getAbsolutePath(), new PluginInfo() {

                @Override
                public String author() {
                    return properties.getProperty("author");
                }

                @Override
                public String description() {
                    return properties.getProperty("description");
                }

                @Override
                public String name() {
                    return properties.getProperty("name");
                }

                @Override
                public String version() {
                    return properties.getProperty("version");
                }
            });
        } catch (IOException e) {
            return Optional.empty();
        }
        return Optional.of(plugin);
    }
}
