package Model;

import Model.Exception.JDBCConnectionException;
import com.sun.jdi.connect.spi.Connection;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Class for work with database Connection
 */
public class DBConn {

    private static Connection dbConnection = null;

    public static void getConn() throws JDBCConnectionException {
        dbConnection = null;
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url = resource.getString("url");
        String driver = resource.getString("driver");
        String user = resource.getString("user");       // такие логин и пароль, адрес?
        String pass = resource.getString("password");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new JDBCConnectionException ("Can't load driver!");
        }
        try {
            dbConnection = (Connection) DriverManager.getConnection(url, user, pass);
            //return dbConnection;
        } catch (SQLException e) {
            throw new JDBCConnectionException("Can't connect to database.");
        }
        if (dbConnection == null) {
            throw new JDBCConnectionException("Driver type is not correct in URL " + url + ".");
        }
        //return dbConnection;
    }

    public void closeConnection() throws JDBCConnectionException {
        if (dbConnection != null) {
            try {
                dbConnection.close();
            } catch (IOException e) {
                throw new JDBCConnectionException("Can't close connection.");
            }
        }
    }

    public Connection getConnection() {
        return dbConnection;
    }

}
