package net.nokok.twitduke.model;

public class CommandParser implements IParser {

    private final ParsingResultListener resultListener;

    public CommandParser(ParsingResultListener resultListener) {
        this.resultListener = resultListener;
    }

    @Override
    public void parse(String text) {
        if ((text == null) || text.isEmpty()) {
            resultListener.error();
        }
    }
}
