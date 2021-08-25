package model;

public class Driver {

    private int id;

    public Driver(String name) {
        this.name = name;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    private final String name;

    public String getName() {
        return name;
    }

    private int carId;

    public int getCarId() {
        return carId;
    }

    private String model;

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }
}

