package Controller.DAO;

import Model.Entities.Request;
import Model.Exception.DAOException;
import Model.Exception.JDBCConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to organize access to Request table and business logic with it
 */
public class DAORequest extends DAO {

    static final Logger DAORequestLogger = LogManager.getLogger(DAOClient.class);
    /**
     * Requests to use in PreparedStatement
     */
    private static final String insertRequestSQL = "INSERT INTO request (id, model, rental_time, id_passport) VALUES(?, ?, ?, ?)";
    private static final String deleteRequestSQL = "DELETE FROM request WHERE id = ?";
    private static final String getAllRequestsByModelSQL = "SELECT * FROM request WHERE model = ?";
    private static final String getAllRequestsSQL = "SELECT * FROM request";

    private static int amountRequests = 0;

    public DAORequest() {
        super();
    }

    /**
     * Inserts new request
     * @param request
     * @throws DAOException
     */
    public static void insertRequest(Request request) throws DAOException {
        try {
            Connection conn = connector.getConn();
            PreparedStatement pstmt = conn.prepareStatement(insertRequestSQL);
            int id = amountRequests + 1;
            pstmt.setInt(1, id);
            pstmt.setString(2, request.getModel());
            pstmt.setInt(3, request.getRentalTime());
            pstmt.setString(4, request.getIdPassport());
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            amountRequests++;

            System.out.println("New request inserted successfully.");
            DAORequestLogger.info("New request inserted successfully.");

        } catch (JDBCConnectionException | SQLException e) {
            throw new DAOException("Insert request exception. " + e.getMessage());
        }
    }

    /**
     * Deletes request
     * @param id
     * @throws DAOException
     */
    public static void deleteRequest(int id) throws DAOException {
        try {
            Connection conn = connector.getConn();
            PreparedStatement pstmt = conn.prepareStatement(deleteRequestSQL);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            amountRequests--;

            System.out.println("Request deleted successfully.");
            DAORequestLogger.info("Request deleted successfully.");

        } catch (JDBCConnectionException | SQLException e) {
            throw new DAOException("Delete request exception. " + e.getMessage());
        }
    }

    /**
     * Returns all requests by given model
     * @param model
     * @retur nall requests by model
     * @throws DAOException
     */
    public static List<Request> getAllRequestsByModel(String model) throws DAOException {
        List<Request> requests = new ArrayList<>();
        try {
            Connection conn = connector.getConn();
            PreparedStatement pstmt = conn.prepareStatement(getAllRequestsByModelSQL);
            pstmt.setString(1, model);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Request request = new Request();
                request.setId(rs.getInt(1));
                request.setModel(model);
                request.setRentalTime(rs.getInt(3));
                request.setIdPassport(rs.getString(4));
                requests.add(request);
            }
            rs.close();
            pstmt.close();
            conn.close();

        } catch (JDBCConnectionException | SQLException e) {
            throw new DAOException("Get all requests by model exception. " + e.getMessage());
        }
        DAORequestLogger.info("Returned all requests by model successfully.");
        return requests;
    }

    /**
     * Returns all requests
     * @return list of requests
     * @throws DAOException
     */
    public static List<Request> getAllRequests() throws DAOException {
        List<Request> requests = new ArrayList<>();
        try {
            Connection conn = connector.getConn();
            PreparedStatement pstmt = conn.prepareStatement(getAllRequestsSQL);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Request request = new Request();
                request.setId(rs.getInt(1));
                request.setModel(rs.getString(2));
                request.setRentalTime(rs.getInt(3));
                request.setIdPassport(rs.getString(4));
                requests.add(request);
            }
            rs.close();
            pstmt.close();
            conn.close();

        } catch (JDBCConnectionException | SQLException e) {
            throw new DAOException("Get all requests exception. " + e.getMessage());
        }
        DAORequestLogger.info("Returned all requests successfully.");
        return requests;
    }

}
