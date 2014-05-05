/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.pluginsupport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import javax.script.Compilable;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import net.nokok.twitduke.core.type.Plugin;

/**
 *
 * @author noko < nokok.kz at gmail.com >
 */
public class PluginLoader {

    private final Compilable compiler = (Compilable) new ScriptEngineManager().getEngineByName("javascript");
    private final String path = new File("plugin").getAbsolutePath();

    public Optional<Plugin> readPlugin(String name) {
        return Optional.ofNullable(unsafeReadPlugin(name));
    }

    public Plugin unsafeReadPlugin(String name) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path + File.separator + name)))) {
            Plugin p = new Plugin(compiler.compile(bufferedReader), path);
            return p;
        } catch (IOException | ScriptException e) {
            return null;
        }
    }
}
