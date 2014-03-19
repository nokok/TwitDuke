package net.nokok.twitduke.model;

public class CommandParser implements IParser {

    private final ParserListener listener;

    public CommandParser(ParserListener listener) {

        this.listener = listener;
    }

    @Override
    public void parse(String text) {

    }
}
