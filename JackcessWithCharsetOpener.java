package net.ucanaccess.jdbc;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;

import net.ucanaccess.jdbc.JackcessOpenerInterface;

public class JackcessWithCharsetOpener implements JackcessOpenerInterface {
    public Database open(File f, String pwd) throws IOException {
        DatabaseBuilder db = new DatabaseBuilder(f);
        db.setCharset(Charset.forName("windows-1256"));
        try {
            db.setReadOnly(false);
            return db.open();
        } catch (IOException e) {
            db.setReadOnly(true);
            return db.open();
        }
   }
}