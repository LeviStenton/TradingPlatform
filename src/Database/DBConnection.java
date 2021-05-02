package Database;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;

public class DBConnection {
    /**
     * Single instance of the database connection
     */
    private  static Connection instance = null;

    /**
     * Constructor initializes the connection
     * @exception FileNotFoundException if the database file is not found
     * @exception SQLException database access error or other errors
     * @exception IOException Signals that an I/O exception of some sort has occurred
     *
     */
    private DBConnection() {

    }

    /**
     * Provides global access to the singleton instance of the UrlSet
     * @return a handle to the singleton instance of the UrlSet
     */
    public static Connection getInstance(){
        return instance;
    }

}
