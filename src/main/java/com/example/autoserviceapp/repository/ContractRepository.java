package com.example.autoserviceapp.repository;

import com.example.autoserviceapp.domain.Contract;
import com.example.autoserviceapp.domain.validators.ValidationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Contract RepositoryInterface class
 */
public class ContractRepository extends AbstractRepository implements RepositoryInterface<Contract> {

    /**
     * Constructor with parameters
     * @param url String
     * @param username String
     * @param password String
     */
    public ContractRepository(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public void addEntity(Contract entity) {
        String sql = "INSERT INTO contract (id_car, id_client_card, sum_of_parts, sum_of_work) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getIdCar());
            statement.setInt(2, entity.getIdClientCard());
            statement.setInt(3, entity.getSumOfParts());
            statement.setInt(4, entity.getSumOfWork());

            statement.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void updateEntity(int id, Contract newEntity) {
        if (this.getEntityById(id) == null) {
            throw new ValidationException("ERROR: This contract doesn't exists!!");
        }
        String sql = "UPDATE contract SET id_car = ?, id_client_card = ?, sum_of_parts = ?, sum_of_work = ? " +
                "WHERE id_contract = ?";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newEntity.getIdCar());
            statement.setInt(2, newEntity.getIdClientCard());
            statement.setInt(3, newEntity.getSumOfParts());
            statement.setInt(4, newEntity.getSumOfWork());
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
            throw new ValidationException("ERROR: This contract doesn't exists!!");
        }
        String sql = "DELETE FROM contract WHERE id_contract = ?";
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
    public Contract getEntityById(int id) {
        String sql = "SELECT * FROM contract WHERE id_contract = " + id;
        Contract contract = new Contract();
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idContract = resultSet.getInt("id_contract");
                int idCar = resultSet.getInt("id_car");
                int idClientCard = resultSet.getInt("id_client_card");
                int sumOfParts = resultSet.getInt("sum_of_parts");
                int sumOfWork = resultSet.getInt("sum_of_work");

                contract.setId(idContract);
                contract.setIdCar(idCar);
                contract.setIdClientCard(idClientCard);
                contract.setSumOfParts(sumOfParts);
                contract.setSumOfWork(sumOfWork);
            }
            if (contract.getId() == 0) {
                return null;
            }
            return contract;
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return contract;
    }

    @Override
    public List<Contract> getAllEntities() {
        List<Contract> listOfContracts = new ArrayList<>();
        String sql = "SELECT * FROM contract";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idContract = resultSet.getInt("id_contract");
                int idCar = resultSet.getInt("id_car");
                int idClientCard = resultSet.getInt("id_client_card");
                int sumOfParts = resultSet.getInt("sum_of_parts");
                int sunOfWork = resultSet.getInt("sum_of_work");

                Contract contract = new Contract(idCar, idClientCard, sumOfParts, sunOfWork);
                contract.setId(idContract);
                listOfContracts.add(contract);
            }
            return listOfContracts;
         }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return listOfContracts;
    }
}
