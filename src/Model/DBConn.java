package Model;

import Model.Exception.JDBCConnectionException;
import View.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class for work with database Connection
 */
public class DBConn {

    static final Logger DBConnLogger = LogManager.getLogger(DBConn.class);
    private static Connection dbConnection = null;
    private static final String path = "database.properties";

    public static Connection getConn() throws JDBCConnectionException {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(path));
        } catch (IOException e) {
            throw new JDBCConnectionException ("Can't load properties file.");
        }
        String url = prop.getProperty("url");
        String driver = prop.getProperty("driver");
        String user = prop.getProperty("user");
        String pass = prop.getProperty("password");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new JDBCConnectionException ("Can't load driver!");
        }
        try {
            dbConnection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new JDBCConnectionException("Can't connect to database.");
        }
        if (dbConnection == null) {
            throw new JDBCConnectionException("Driver type is not correct in URL " + url + ".");
        }
        DBConnLogger.info("Successfully connected.");
        return dbConnection;
    }

    public void closeConnection() throws JDBCConnectionException {
        if (dbConnection != null) {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                throw new JDBCConnectionException("Can't close connection.");
            }
        }
    }

}
