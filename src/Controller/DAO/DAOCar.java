package Controller.DAO;

import Model.Entities.Car;
import Model.Exception.DAOException;
import Model.Exception.JDBCConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOCar extends DAO {

    private static final String getAmountByCarModelSQL = "SELECT COUNT(car_id) FROM car WHERE car_model = ? AND car_state = ?";
    private static final String getCarByCarIdSQL = "SELECT * FROM car WHERE car_id = ?";
    private static final String getCarStateByCarIdSQL = "SELECT car_state FROM car WHERE car_id = ?";
    private static final String updateCarOnStateSQL = "UPDATE car SET car_state = ? WHERE car_id = ?";

    public DAOCar() {
        super();
    }

    public static int getAmountByCarModel(String carModel) throws DAOException {
        int amount = 0;
        try {
            connector.getConn();
            Connection conn = (Connection) connector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(getAmountByCarModelSQL);
            pstmt.setString(1, carModel);
            pstmt.setBoolean(2, true);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(1); // ??
            }
            rs.close();
            pstmt.close();
            //conn.close(); or
            connector.closeConnection();

        } catch (JDBCConnectionException | SQLException e) {
            throw new DAOException("Get amount by carModel exception.");
        }
        return amount;
    }

    public static boolean getCarStateByCarId(int carId) throws DAOException {
        boolean state = true;
        try {
            connector.getConn();
            Connection conn = (Connection) connector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(getCarStateByCarIdSQL);
            pstmt.setInt(1, carId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                state = rs.getBoolean(1); // ??
            }
            rs.close();
            pstmt.close();
            //conn.close(); or
            connector.closeConnection();

        } catch (JDBCConnectionException | SQLException e) {
            throw new DAOException("Get car state by CarId exception.");
        }
        return state;
    }

    public static Car getCarByCarId(int carId) throws DAOException {
        Car car = null;
        try {
            connector.getConn();
            Connection conn = (Connection) connector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(getCarByCarIdSQL);
            pstmt.setInt(1, carId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                car = new Car();
                car.setCarId(carId);
                car.setCarModel(rs.getString(2));
                car.setCarState(rs.getBoolean(3));
            }
            rs.close();
            pstmt.close();
            //conn.close(); or
            connector.closeConnection();

        } catch (JDBCConnectionException | SQLException e) {
            throw new DAOException("Get car by carId exception.");
        }
        return car;
    }

    public static void updateCarOnState(int carId, boolean carState) throws DAOException {
        try {
            connector.getConn();
            Connection conn = (Connection) connector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(updateCarOnStateSQL);
            pstmt.setBoolean(1, carState);
            pstmt.setInt(2, carId);
            pstmt.executeUpdate();
            pstmt.close();
            //conn.close(); or
            connector.closeConnection();

            System.out.println("Car state updated successfully.");

        } catch (JDBCConnectionException | SQLException e) {
            throw new DAOException("Update car on state exception.");
        }
    }

    // add insert, delete, usual update, getAll
    // is conn.commit() required in insert, update? or autocommit?
}
