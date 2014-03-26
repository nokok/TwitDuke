package net.nokok.twitduke.model;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import net.nokok.twitduke.model.listener.ParserStateListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommandParser implements IParser {

    private final Log logger = LogFactory.getLog(CommandParser.class);
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
            logger.info("スクリプトを有効化します");

            isAvailable = true;
            parserStateListener.enabled();
            return;
        }
        if (text.startsWith("TD Disable")) {
            logger.info("スクリプトを無効化します");

            isAvailable = false;
            parserStateListener.disabled();
            return;
        }
        if (isAvailable) {
            run(text);
        } else {
            String message = "スクリプトが有効になっていません";
            logger.error(message);

            resultListener.error(message);
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
            logger.error("スクリプトにエラーが発生しています", e);

            parserStateListener.error();
        }
    }
}
