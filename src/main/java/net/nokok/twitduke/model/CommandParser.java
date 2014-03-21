package net.nokok.twitduke.model;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CommandParser implements IParser {

    private final ParsingResultListener resultListener;

    private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

    private boolean isAvailable;

    public CommandParser(ParsingResultListener resultListener) {
        this.resultListener = resultListener;
    }

    @Override
    public void parse(String text) {
        if (text.startsWith("TD Enable")) {
            isAvailable = true;
            resultListener.success();
            return;
        }
        if (text.startsWith("TD Disable")) {
            isAvailable = false;
            resultListener.success();
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
        } catch (ScriptException e) {
            resultListener.error(e.getMessage());
        }
    }
}
