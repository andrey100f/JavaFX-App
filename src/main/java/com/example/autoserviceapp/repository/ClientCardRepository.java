package com.example.autoserviceapp.repository;

import com.example.autoserviceapp.domain.ClientCard;
import com.example.autoserviceapp.domain.validators.ValidationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Client Card RepositoryInterface class
 */
public class ClientCardRepository extends AbstractRepository implements RepositoryInterface<ClientCard> {

    /**
     * Constructor with parameters
     * @param url String
     * @param username String
     * @param password String
     */
    public ClientCardRepository(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public void addEntity(ClientCard entity) {
        String sql = "INSERT INTO client_card (first_name, last_name, cnp) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getCnp());

            statement.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void updateEntity(int id, ClientCard newEntity) {
        if (this.getEntityById(id) == null) {
            throw new ValidationException("ERROR: This client card doesn't exists!!");
        }
        String sql = "UPDATE client_card SET first_name = ?, last_name = ?, cnp = ? WHERE id_client_card = ?";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newEntity.getFirstName());
            statement.setString(2, newEntity.getLastName());
            statement.setString(3, newEntity.getCnp());
            statement.setInt(4, id);

            statement.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void removeEntity(int id) {
        if (this.getEntityById(id) == null) {
            throw new ValidationException("ERROR: This client card doesn't exists!!");
        }
        String sql = "DELETE FROM client_card WHERE id_client_card = ?";
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
    public ClientCard getEntityById(int id) {
        ClientCard clientCard = new ClientCard();
        String sql = "SELECT * FROM client_card WHERE id_client_card = " + id;
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idCreditCard = resultSet.getInt("id_client_card");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String cnp = resultSet.getString("cnp");

                clientCard.setId(idCreditCard);
                clientCard.setFirstName(firstName);
                clientCard.setLastName(lastName);
                clientCard.setCnp(cnp);
            }
            if (clientCard.getId() == 0) {
                return null;
            }
            return clientCard;
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return clientCard;
    }

    @Override
    public List<ClientCard> getAllEntities() {
        List<ClientCard> listOfClientCards = new ArrayList<>();
        String sql = "SELECT * FROM client_card";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idClientCard = resultSet.getInt("id_client_card");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String cnp = resultSet.getString("cnp");

                ClientCard clientCard = new ClientCard(firstName, lastName, cnp);
                clientCard.setId(idClientCard);
                listOfClientCards.add(clientCard);
            }
            return listOfClientCards;
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return listOfClientCards;
    }
}
