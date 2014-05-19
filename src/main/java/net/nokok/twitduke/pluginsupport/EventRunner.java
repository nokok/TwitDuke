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

import java.util.ArrayList;
import java.util.List;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import net.nokok.twitduke.core.type.UncheckedScriptException;

class EventRunner {

    private final List<Plugin> plugins = new ArrayList<>();
    private final String objectName;

    EventRunner(String objectName) {
        this.objectName = objectName;
    }

    void addPlugin(Plugin plugin) {
        this.plugins.add(plugin);
    }

    void invokeAll(String methodName, Object... args) {
        plugins.forEach(plugin -> {
            ScriptEngine scriptEngine = plugin.scriptEngine;
            Invocable invocable = (Invocable) scriptEngine;
            try {
                invocable.invokeMethod(scriptEngine.get(objectName), methodName, args);
            } catch (ScriptException | NoSuchMethodException e) {
                throw new UncheckedScriptException(e);
            }
        });
    }

}
