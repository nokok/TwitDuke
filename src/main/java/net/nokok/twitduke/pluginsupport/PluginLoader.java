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
 * プラグインの読み込みを担当するクラスです。
 *
 * @author noko < nokok.kz at gmail.com >
 */
public class PluginLoader {

    private final Compilable compiler = (Compilable) new ScriptEngineManager().getEngineByName("javascript");
    private final String path = new File("plugin").getAbsolutePath();

    /**
     * 指定されたファイル名のプラグインをpluginディレクトリから読み込みます。この名前は拡張子は含みます。
     * 通常、この拡張子は(Javascriptによるプラグインの場合は)".js"です。
     * ファイル名以外のパスは自動で追加するためフルパスを指定するとエラーが発生します。
     * プラグイン存在しないまたは文法エラーなどの理由でプラグインオブジェクトを構築出来ない
     * 場合はOptional.empty()を返します。nullが返ることはありません。
     *
     *
     * @param name プラグイン名
     *
     * @return Optionalでラップされたプラグインオブジェクト
     */
    public Optional<Plugin> readPlugin(String name) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path + File.separator + name)))) {
            Plugin p = new Plugin(compiler.compile(bufferedReader), path);
            return Optional.of(p);
        } catch (IOException | ScriptException e) {
            return Optional.empty();
        }
    }
}
