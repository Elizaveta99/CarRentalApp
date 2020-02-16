package Model.Entities;

/**
 * Entity for table Car
 */
public class Car {
    private int carId;
    private String carModel;
    private boolean carState;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public boolean isCarState() {
        return carState;
    }

    public void setCarState(boolean carState) {
        this.carState = carState;
    }
}
