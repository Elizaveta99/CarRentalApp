package Controller.DAO;

import Model.Entities.Client;
import Model.Exception.DAOException;
import Model.Exception.JDBCConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to organize access to Client table and business logic with it
 */
public class DAOClient extends DAO {

    static final Logger DAOClientLogger = LogManager.getLogger(DAOClient.class);
    private static final String getClientByIdSQL = "SELECT * FROM client WHERE id_passport = ?";
    private static final String insertClientSQL = "INSERT INTO client (id_passport, name, residence_address, birth_date) VALUES (?, ?, ?, to_date(?, 'DD-MM-YYYY'))";

    public DAOClient() {
        super();
    }

    public static boolean getClientById(String idPassport) throws DAOException {
        boolean clientExists = true;
        try {
            Connection conn = connector.getConn();
            PreparedStatement pstmt = conn.prepareStatement(getClientByIdSQL);
            pstmt.setString(1, idPassport);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                clientExists = false;
            }
            rs.close();
            pstmt.close();
            conn.close();

        } catch (JDBCConnectionException e) {
            throw new DAOException("Get client by idPassport exception. ");
        } catch (SQLException e) {
            throw new DAOException("Get client by idPassport exception. ");
        }
        DAOClientLogger.info("Returned client by id successfully.");
        return clientExists;
    }

    public static void insertClient(Client client) throws DAOException {
        try {
            Connection conn = connector.getConn();
            PreparedStatement pstmt = conn.prepareStatement(insertClientSQL);
            pstmt.setString(1, client.getIdPassport());
            pstmt.setString(2, client.getName());
            pstmt.setString(3, client.getResidenceAddress());
            pstmt.setString(4, client.getBirthDate());
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();

            System.out.println("New client inserted successfully.");
            DAOClientLogger.info("New client inserted successfully.");

        } catch (JDBCConnectionException | SQLException e) {
            throw new DAOException("Insert client  exception. ");
        }
    }

}
