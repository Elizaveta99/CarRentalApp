package Controller.DAO;

import Model.Entities.Car;
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
 * Class to organize access to Car table and business logic with it
 */
public class DAOCar extends DAO {

    static final Logger DAOCarLogger = LogManager.getLogger(DAOCar.class);
    /**
     * Requests to use in PreparedStatement
     */
    private static final String getAmountByCarModelSQL = "SELECT COUNT(car_id) FROM car WHERE car_model = ? AND car_state = ?";
    private static final String getCarByCarStateAndModelSQL = "SELECT * FROM car WHERE car_state = ? AND car_model = ?";
    private static final String getCarStateByCarIdSQL = "SELECT car_state FROM car WHERE car_id = ?";
    private static final String updateCarOnStateSQL = "UPDATE car SET car_state = ? WHERE car_id = ?";
    private static final String getAllCarsSQL = "SELECT * FROM car";

    public DAOCar() {
        super();
    }

    /**
     * Returns amount of cars of given model
     * @param carModel - model od the car
     * @return amount
     * @throws DAOException
     */
    public static int getAmountByCarModel(String carModel) throws DAOException {
        int amount = 0;
        try {
            Connection conn = connector.getConn();
            PreparedStatement pstmt = conn.prepareStatement(getAmountByCarModelSQL);
            pstmt.setString(1, carModel);
            pstmt.setBoolean(2, true);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            conn.close();

        } catch (JDBCConnectionException  e) {
            throw new DAOException("Get amount by carModel exception. " + e.getMessage());
        } catch (SQLException e) {
            throw new DAOException("Get amount by carModel exception. " + e.getMessage());
        }
        DAOCarLogger.info("Returned amount by carModel successfully.");
        return amount;
    }

    /**
     * Returns car's state by id
     * @param carId = id of given car
     * @return state
     * @throws DAOException
     */
    public static boolean getCarStateByCarId(int carId) throws DAOException {
        boolean state = true;
        try {
            Connection conn = connector.getConn();
            PreparedStatement pstmt = conn.prepareStatement(getCarStateByCarIdSQL);
            pstmt.setInt(1, carId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                state = rs.getBoolean(1);
            }
            rs.close();
            pstmt.close();
            conn.close();

        } catch (JDBCConnectionException e) {
            throw new DAOException("Get car state by CarId exception. " + e.getMessage());
        } catch (SQLException e) {
            throw new DAOException("Get car state by CarId exception. " + e.getMessage());
        }
        DAOCarLogger.info("Returned carState by carId successfully.");
        return state;
    }

    /**
     * Returns car object by car's state and model
     * @param carState - given state
     * @param carModel - given model
     * @return car object
     * @throws DAOException
     */
    public static List<Car> getCarByCarStateAndModel(boolean carState, String carModel) throws DAOException {
        List<Car> cars = new ArrayList<>();
        try {
            Connection conn = connector.getConn();
            PreparedStatement pstmt = conn.prepareStatement(getCarByCarStateAndModelSQL);
            pstmt.setBoolean(1, carState);
            pstmt.setString(2, carModel);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Car car = new Car();
                car.setCarId(rs.getInt(1));
                car.setCarModel(rs.getString(2));
                car.setCarState(rs.getBoolean(3));
                cars.add(car);
            }
            rs.close();
            pstmt.close();
            conn.close();

        } catch (JDBCConnectionException e) {
            throw new DAOException("Get cars by carState and carModel exception. " + e.getMessage());
        } catch (SQLException e) {
            throw new DAOException("Get cars by carState and carModel exception. " + e.getMessage());
        }
        DAOCarLogger.info("Returned car by carState and carModel successfully.");
        return cars;
    }

    /**
     * Updates car's availability
     * @param carId - id
     * @param carState - state
     * @throws DAOException
     */
    public static void updateCarOnState(int carId, boolean carState) throws DAOException {
        try {
            Connection conn = connector.getConn();
            PreparedStatement pstmt = conn.prepareStatement(updateCarOnStateSQL);
            pstmt.setBoolean(1, carState);
            pstmt.setInt(2, carId);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();

            System.out.println("Car state updated successfully.");
            DAOCarLogger.info("Car state updated successfully.");

        } catch (JDBCConnectionException e) {
            throw new DAOException("Update car on state exception. " + e.getMessage());
        } catch (SQLException e) {
            throw new DAOException("Update car on state exception. " + e.getMessage());
        }
    }

    /**
     * Returns list of cars
     * @return list of cars
     * @throws DAOException
     */
    public static List<Car> getAllCars() throws DAOException {
        List<Car> cars = new ArrayList<>();
        try {
            Connection conn = connector.getConn();
            PreparedStatement pstmt = conn.prepareStatement(getAllCarsSQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Car car = new Car();
                car.setCarId(rs.getInt(1));
                car.setCarModel(rs.getString(2));
                car.setCarState(rs.getBoolean(3));
                cars.add(car);
            }
            rs.close();
            pstmt.close();
            conn.close();

        } catch (JDBCConnectionException e) {
            throw new DAOException("Get all cars exception. " + e.getMessage());
        } catch (SQLException e) {
            throw new DAOException("Get all cars exception. " + e.getMessage());
        }
        DAOCarLogger.info("All cars returned successfully.");
        return cars;
    }
}
