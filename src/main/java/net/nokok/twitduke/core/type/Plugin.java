/*
 * The MIT License
 *
 * Copyright 2014 noko <nokok.kz at gmail.com>.
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
package net.nokok.twitduke.core.type;

import javax.script.CompiledScript;
import javax.script.ScriptException;

/**
 * プラグインを表すクラスです。
 * このクラスはプラグインのコンパイル済みオブジェクトとプラグインの名前を持ちます。
 * また、このクラス内のスクリプトは外部から実行可能です。
 *
 */
public class Plugin implements RunnableScript {

    private final CompiledScript script;
    private final String pluginName;

    /**
     * 指定されたコンパイル済みのスクリプトと名前を使用して新しいオブジェクトを生成します
     *
     * @param script     コンパイル済みのスクリプト
     * @param pluginName プラグインの名前
     */
    public Plugin(CompiledScript script, String pluginName) {
        this.script = script;
        this.pluginName = pluginName;
    }

    /**
     * プラグインの名前を返します
     *
     * @return プラグインの名前
     */
    public String getName() {
        return pluginName;
    }

    /**
     * プラグインを実行します。実行するevalメソッドはScriptExceptionをスローする場合がありますが、
     * このメソッド内でキャッチされ新しい実行時例外UncheckedScriptExceptionとしてスローします。
     *
     * @exception UncheckedScriptException
     */
    @Override
    public void runScript() {
        try {
            script.eval();
        } catch (ScriptException ex) {
            throw new UncheckedScriptException(ex);
        }
    }
}
