package net.nokok.twitduke.model;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import net.nokok.twitduke.model.listener.ParserStateListener;

public class CommandParser implements IParser {

    private final ParsingResultListener resultListener;
    private final ParserStateListener   parserStateListener;

    private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

    private boolean isAvailable;

    public CommandParser(ParsingResultListener resultListener, ParserStateListener parserStateListener) {
        this.resultListener = resultListener;
        this.parserStateListener = parserStateListener;
    }

    @Override
    public void parse(String text) {
        if (text.startsWith("TD Enable")) {
            isAvailable = true;
            parserStateListener.enabled();
            return;
        }
        if (text.startsWith("TD Disable")) {
            isAvailable = false;
            parserStateListener.disabled();
            return;
        }
        if (isAvailable) {
            run(text);
        } else {
            resultListener.error("スクリプトが有効になっていません");
        }
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    private void run(String script) {
        try {
            engine.eval(script);
            resultListener.success();
            parserStateListener.ready();
        } catch (ScriptException e) {
            parserStateListener.error();
        }
    }
}
