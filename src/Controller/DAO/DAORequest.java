package Controller.DAO;

import Model.Entities.Request;
import Model.Exception.DAOException;
import Model.Exception.JDBCConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAORequest extends DAO {

    private static final String insertRequestSQL = "INSERT INTO request (id, model, rental_time, id_passport) VALUES(?, ?, ?, ?)";
    private static final String deleteRequestSQL = "DELETE FROM request WHERE id = ?";
    private static final String getAllRequestsByModelSQL = "SELECT * FROM request WHERE model = ?";

    private static int amountRequests = 0;

    public DAORequest() {
        super();
    }

    public static void insertRequest(Request request) throws DAOException {
        try {
            connector.getConn();
            Connection conn = (Connection) connector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(insertRequestSQL);
            int id = amountRequests + 1;
            pstmt.setInt(1, id);
            pstmt.setString(2, request.getModel());
            pstmt.setInt(3, request.getRentalTime());
            pstmt.setString(4, request.getIdPassport());
            pstmt.executeUpdate();
            pstmt.close();
            //conn.close(); or
            connector.closeConnection();
            amountRequests++;

            System.out.println("New request inserted successfully.");

        } catch (JDBCConnectionException | SQLException e) {
            throw new DAOException("Insert request exception.");
        }
    }

    public static void deleteRequest(int id) throws DAOException {
        try {
            connector.getConn();
            Connection conn = (Connection) connector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(deleteRequestSQL);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            //conn.close(); or
            connector.closeConnection();
            amountRequests--;

            System.out.println("Request deleted successfully.");

        } catch (JDBCConnectionException | SQLException e) {
            throw new DAOException("Delete request exception.");
        }
    }

    public static List<Request> getAllRequestsByModel(String model) throws DAOException {
        List<Request> requests = new ArrayList<>();
        try {
            connector.getConn();
            Connection conn = (Connection) connector.getConnection();
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
            //conn.close(); or
            connector.closeConnection();

        } catch (JDBCConnectionException | SQLException e) {
            throw new DAOException("Get all requests exception.");
        }
        return requests;
    }

}
