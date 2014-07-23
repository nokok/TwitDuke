package net.nokok.twitduke.core.io;

public enum FileType {

    SERIALIZE("serialize"),
    PLAINTEXT("plaintext"),;

    private final String text;

    private FileType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
