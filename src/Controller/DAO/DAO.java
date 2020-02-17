package Controller.DAO;

import Model.DBConn;

/**
 * Base class for DAO functionality
 */
public class DAO {
    protected static DBConn connector;

    public DAO() {
        connector = new DBConn();
    }

    public DBConn getConnector() {
        return connector;
    }
}
