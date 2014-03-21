package net.nokok.twitduke.model;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CommandParser implements IParser {

    private final ParsingResultListener resultListener;

    private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

    public CommandParser(ParsingResultListener resultListener) {
        this.resultListener = resultListener;
    }

    @Override
    public void parse(String text) {
        run(text);
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
