package context;

import database.PostgreUserRepository;
import model.Car;
import model.Driver;

public class ApplicationContext {

    public static final PostgreUserRepository userRepository = new PostgreUserRepository();
    public static int rowCount = 0;

    public static void insertCar(Car car) {
        rowCount = userRepository.insertCar(car);
    }

    public static void insertDriver(Driver driver) {
        driver.setCarId(rowCount++);
        userRepository.insertDriver(driver);
    }
}
