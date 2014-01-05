package net.nokok.twitduke.wrapper;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteWrapper {

    private final String DB_FILE_PATH = new File(new File("").getAbsolutePath(), "conf.db").getAbsolutePath();

    private Statement statement;

    public SQLiteWrapper() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE_PATH);
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
