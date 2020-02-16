package Controller.DAO;

import Model.DBConn;

public class DAO {
    protected static DBConn connector;

    public DAO() { // add DAOExceptions?
        connector = new DBConn();
    }

    public DBConn getConnector() {
        return connector;
    }
}
