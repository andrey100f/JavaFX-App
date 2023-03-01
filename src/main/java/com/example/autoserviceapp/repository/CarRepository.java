package com.example.autoserviceapp.repository;

import com.example.autoserviceapp.domain.Car;
import com.example.autoserviceapp.domain.validators.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 * Car repository class
 */
public class CarRepository extends AbstractRepository implements RepositoryInterface<Car> {

    /**
     * Constructor with parameters
     * @param url String
     * @param username String
     * @param password String
     */
    public CarRepository(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public void addEntity(Car entity) {
        String sql = "INSERT INTO car (model, year_of_purchase, number_of_kilometers, in_guarantee) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getModel());
            statement.setInt(2, entity.getYearOfPurchase());
            statement.setInt(3, entity.getNumberOfKilometers());
            statement.setString(4, entity.getInGuarantee());

            statement.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void updateEntity(int id, Car newEntity) {
        if (this.getEntityById(id) == null) {
            throw new ValidationException("ERROR: This car doesn't exists!!");
        }
        String sql = "UPDATE car SET model = ?, year_of_purchase = ?, number_of_kilometers = ?, " +
                "in_guarantee = ?  WHERE id_car = ?";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newEntity.getModel());
            statement.setInt(2, newEntity.getYearOfPurchase());
            statement.setInt(3, newEntity.getNumberOfKilometers());
            statement.setString(4, newEntity.getInGuarantee());
            statement.setInt(5, id);

            statement.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void removeEntity(int id) {
        if (this.getEntityById(id) == null) {
            throw new ValidationException("ERROR: This car doesn't exists!!");
        }
        String sql = "DELETE FROM car WHERE id_car = ?";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public Car getEntityById(int id) {
        String sql = "SELECT * FROM car WHERE id_car = " + id;
        Car car = new Car();
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int carId = resultSet.getInt("id_car");
                String model = resultSet.getString("model");
                int yearOfPurchase = resultSet.getInt("year_of_purchase");
                int numberOfKilometers = resultSet.getInt("number_of_kilometers");
                String inGuarantee = resultSet.getString("in_guarantee");

                car.setId(carId);
                car.setModel(model);
                car.setYearOfPurchase(yearOfPurchase);
                car.setNumberOfKilometers(numberOfKilometers);
                car.setInGuarantee(inGuarantee);
            }
            if(car.getId() == 0) {
                return null;
            }
            return car;
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return car;
    }

    @Override
    public List<Car> getAllEntities() {
        List<Car> listOfCars = new ArrayList<>();
        String sql = "SELECT * FROM car";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()) {
                int id = resultSet.getInt("id_car");
                String model = resultSet.getString("model");
                int yearOfPurchase = resultSet.getInt("year_of_purchase");
                int numberOfKilometers = resultSet.getInt("number_of_kilometers");
                String inGuarantee = resultSet.getString("in_guarantee");

                Car car = new Car(model, yearOfPurchase, numberOfKilometers, inGuarantee);
                car.setId(id);
                listOfCars.add(car);
            }
            return listOfCars;
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return listOfCars;
    }
}
