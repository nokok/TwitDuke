/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.type;

import javax.script.CompiledScript;
import javax.script.ScriptException;

/**
 *
 * @author noko < nokok.kz at gmail.com >
 */
public class Plugin implements RunnableScript {

    private final CompiledScript script;
    private final String pluginName;

    public Plugin(CompiledScript script, String pluginName) {
        this.script = script;
        this.pluginName = pluginName;
    }

    public String getName() {
        return pluginName;
    }

    @Override
    public void runScript() {
        try {
            script.eval();
        } catch (ScriptException ex) {
            throw new UncheckedScriptException(ex);
        }
    }
}
