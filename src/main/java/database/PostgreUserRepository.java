package database;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import context.ApplicationContext;
import model.Car;
import model.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostgreUserRepository {

    private Connection connection;

    public PostgreUserRepository() {}

    private void createConnection() {
        try {
            String url = "jdbc:postgresql://localhost:5432/transaction?currentSchema=public";
            connection = DriverManager.getConnection(url,
                    "postgres",
                    "postgres");
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Driver> getResults() {
        createConnection();
        String query = "SELECT DRIVER.ID, DRIVER.NAME, CARS.MODEL AS MODEL " +
                "FROM DRIVER LEFT JOIN CARS ON DRIVER.CARID = CARS.ID";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            ObjectMapper objectMapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            List<Driver> resultSet = new ArrayList<>();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnsCount = metaData.getColumnCount();
            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<>(columnsCount);
                for (int i = 1; i <= columnsCount; ++i) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                resultSet.add(objectMapper.convertValue(row, Driver.class));
            }
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insertCar(Car car) {
        createConnection();
        String updateCars = "INSERT INTO cars(model) VALUES('%s')";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(String.format(updateCars, car.getModel()))) {
            System.out.printf((updateCars) + "%n", car.getModel());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void insertDriver(Driver driver) {
        try {
            if (driver.getName().isEmpty()) {
                connection.rollback();
                System.out.println("Transaction rolled back!");
                ApplicationContext.rowCount--;
            } else {
                String updateDrivers = "INSERT INTO driver(name, carid) VALUES('%s', %d)";
                System.out.printf((updateDrivers) + "%n", driver.getName(), driver.getCarId());
                PreparedStatement preparedStatement = connection.prepareStatement(String.format(updateDrivers,
                        driver.getName(),
                        driver.getCarId()));
                preparedStatement.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
