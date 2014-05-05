/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.type;

import javax.script.CompiledScript;
import javax.script.ScriptException;

/**
 * プラグインを表すクラスです。
 * このクラスはプラグインのコンパイル済みオブジェクトとプラグインの名前を持ちます。
 * また、このクラス内のスクリプトは外部から実行可能です。
 *
 * @author noko < nokok.kz at gmail.com >
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
