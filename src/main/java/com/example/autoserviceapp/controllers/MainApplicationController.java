package com.example.autoserviceapp.controllers;

import com.example.autoserviceapp.domain.Car;
import com.example.autoserviceapp.domain.ClientCard;
import com.example.autoserviceapp.domain.Contract;
import com.example.autoserviceapp.domain.validators.CarValidator;
import com.example.autoserviceapp.domain.validators.ValidationException;
import com.example.autoserviceapp.domain.validators.Validator;
import com.example.autoserviceapp.repository.CarRepository;
import com.example.autoserviceapp.repository.ClientCardRepository;
import com.example.autoserviceapp.repository.ContractRepository;
import com.example.autoserviceapp.repository.RepositoryInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Main Application Controller class
 */
public class MainApplicationController implements Initializable {
    private final Validator<Car> carValidator = new CarValidator();
    private RepositoryInterface<Car> carRepository;
    private RepositoryInterface<ClientCard> clientCardRepository;
    private RepositoryInterface<Contract> contractRepository;

    // Car object Fields
    @FXML
    private TextField carIdField = new TextField();
    @FXML
    private TextField carModelField = new TextField();
    @FXML
    private TextField carYearOfPurchaseField = new TextField();
    @FXML
    private TextField carNumberOfKilometersField = new TextField();
    @FXML
    private TextField carInGuaranteeField = new TextField();

    // Car Table
    @FXML
    private TableView<Car> carTable = new TableView<>();
    @FXML
    private TableColumn<Car, Integer> carIdColumn = new TableColumn<>();
    @FXML
    private TableColumn<Car, String> carModelColumn = new TableColumn<>();
    @FXML
    private TableColumn<Car, Integer> carNumberOfKilometersColumn = new TableColumn<>();
    @FXML
    private TableColumn<Car, Integer> carYearOfPurchaseColumn = new TableColumn<>();
    @FXML
    private TableColumn<Car, String> carInGuaranteeColumn = new TableColumn<>();

    // Client Card object Fields
    @FXML
    private TextField clientCardIdField = new TextField();
    @FXML
    private TextField clientCardFirstNameField = new TextField();
    @FXML
    private TextField clientCardLastNameField = new TextField();
    @FXML
    private TextField clientCardCnpField = new TextField();

    // Client Card Table
    @FXML
    private TableView<ClientCard> clientCardTable = new TableView<>();
    @FXML
    private TableColumn<ClientCard, Integer> clientCardIdColumn = new TableColumn<>();
    @FXML
    private TableColumn<ClientCard, String> clientCardFirstNameColumn = new TableColumn<>();
    @FXML
    private TableColumn<ClientCard, String> clientCardLastNameColumn = new TableColumn<>();
    @FXML
    private TableColumn<ClientCard, String> clientCardCnpColumn = new TableColumn<>();

    // Contract object Fields
    @FXML
    private TextField contractIdField = new TextField();
    @FXML
    private TextField contractIdCarField = new TextField();
    @FXML
    private TextField contractIdClientCardField = new TextField();
    @FXML
    private TextField contractSumOfPartsField = new TextField();
    @FXML
    private TextField contractSumOfWorkField = new TextField();

    // Contract Table
    @FXML
    private TableView<Contract> contractTable = new TableView<>();
    @FXML
    private TableColumn<Contract, Integer> contractIdColumn = new TableColumn<>();
    @FXML
    private TableColumn<Contract, Integer> contractIdCarColumn = new TableColumn<>();
    @FXML
    private TableColumn<Contract, Integer> contractIdClientCardColumn = new TableColumn<>();
    @FXML
    private TableColumn<Contract, Integer> contractSumOfPartsColumn = new TableColumn<>();
    @FXML
    private TableColumn<Contract, Integer> contractSumOfWorkColumn = new TableColumn<>();

    /**
     * Removes in cascade contracts by id car
     * @param idCar Integer
     */
    private void removeContractsByIdCar(int idCar) {
        String url = "jdbc:postgresql://localhost:5432/AutoService";
        String username = "postgres";
        String password = "andrey100f";

        String sql = "DELETE FROM contract WHERE id_car = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCar);

            statement.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Removes in cascade by id client card
     * @param idClientCard Integer
     */
    private void removeContractByIdClientCard(int idClientCard) {
        String url = "jdbc:postgresql://localhost:5432/AutoService";
        String username = "postgres";
        String password = "andrey100f";

        String sql = "DELETE FROM contract WHERE id_client_card = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idClientCard);

            statement.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Search cnp in a list of client cards
     * @param clientCard ClientCard object
     * @return boolean
     */
    private boolean searchCnp(ClientCard clientCard) {
        List<ClientCard> listOfClientCards = this.clientCardRepository.getAllEntities();
        for(ClientCard clientCard1:listOfClientCards) {
            if (clientCard1.getCnp().equals(clientCard.getCnp()) && clientCard1.getId() != clientCard.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validate function for client card
     * @param clientCard ClientCard object
     */
    private void validateClientCard(ClientCard clientCard) {
        String listOfErrors = "";
        if (this.searchCnp(clientCard)) {
            listOfErrors += "ERROR: The CNP already exists!!\n";
        }
        if (!listOfErrors.equals("")) {
            throw new ValidationException(listOfErrors);
        }
    }

    /**
     * Validate function for contract
     * @param contract Contract object
     */
    private void validateContract(Contract contract) {
        String listOfErrors = "";
        if(this.carRepository.getEntityById(contract.getIdCar()) == null) {
            listOfErrors += "ERROR: This car doesn't exists!!!\n";
        }
        if (this.clientCardRepository.getEntityById(contract.getIdClientCard()) == null) {
            listOfErrors += "ERROR: This client card does not exists!!\n";
        }
        if (!listOfErrors.equals("")) {
            throw new ValidationException(listOfErrors);
        }
    }

    /**
     * Applies discount for a car in a contract
     * @param contract Contract object
     */
    private void contractApplyDiscount(Contract contract) {
        if(this.carRepository.getEntityById(contract.getIdCar()).getInGuarantee().equals("true")) {
            contract.setSumOfParts(0);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("This car is in guarantee. Sum of parts will be 0");
            alert.show();
        }
    }

    /**
     * Gets all the cars from the database
     * @return an ObservableList object
     */
    private ObservableList<Car> getListOfCars() {
        List<Car> listOfCars = this.carRepository.getAllEntities();
        return FXCollections.observableArrayList(listOfCars);
    }

    /**
     * Show all the cars from the database
     */
    private void showAllCars() {
        this.carIdField.clear();
        this.carModelField.clear();
        this.carYearOfPurchaseField.clear();
        this.carNumberOfKilometersField.clear();
        this.carInGuaranteeField.clear();

        ObservableList<Car> listOfCars = this.getListOfCars();
        this.carIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.carModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        this.carYearOfPurchaseColumn.setCellValueFactory(new PropertyValueFactory<>("yearOfPurchase"));
        this.carNumberOfKilometersColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfKilometers"));
        this.carInGuaranteeColumn.setCellValueFactory(new PropertyValueFactory<>("inGuarantee"));
        this.carTable.setItems(listOfCars);
    }

    /**
     * Gets all the client cards from the database
     * @return ObservableList object
     */
    private ObservableList<ClientCard> getListOfClientCards() {
        List<ClientCard> listOfClientCards = this.clientCardRepository.getAllEntities();
        return FXCollections.observableArrayList(listOfClientCards);
    }

    /**
     * Shows all the credit cards from the database
     */
    private void showAllCreditCards() {
        this.clientCardIdField.clear();
        this.clientCardFirstNameField.clear();
        this.clientCardLastNameField.clear();
        this.clientCardCnpField.clear();

        ObservableList<ClientCard> listOfClientCards = this.getListOfClientCards();
        this.clientCardIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.clientCardFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.clientCardLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.clientCardCnpColumn.setCellValueFactory(new PropertyValueFactory<>("cnp"));
        this.clientCardTable.setItems(listOfClientCards);
    }

    /**
     * Gets all the contracts from the database
     * @return ObservableList object
     */
    private ObservableList<Contract> getListOfContracts() {
        List<Contract> listOfContracts = this.contractRepository.getAllEntities();
        return FXCollections.observableArrayList(listOfContracts);
    }

    /**
     * Shows all the contracts from the database
     */
    private void showAllContracts() {
        this.contractIdField.clear();
        this.contractIdCarField.clear();
        this.contractIdClientCardField.clear();
        this.contractSumOfPartsField.clear();
        this.contractSumOfWorkField.clear();

        ObservableList<Contract> listOfContracts = this.getListOfContracts();
        this.contractIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.contractIdCarColumn.setCellValueFactory(new PropertyValueFactory<>("idCar"));
        this.contractIdClientCardColumn.setCellValueFactory(new PropertyValueFactory<>("idClientCard"));
        this.contractSumOfPartsColumn.setCellValueFactory(new PropertyValueFactory<>("sumOfParts"));
        this.contractSumOfWorkColumn.setCellValueFactory(new PropertyValueFactory<>("sumOfWork"));
        this.contractTable.setItems(listOfContracts);
    }

    /**
     * Handle function for add car button
     */
    public void handleAddCar() {
        try {
            String id = this.carIdField.getText();
            String model = this.carModelField.getText();
            int yearOfPurchase = Integer.parseInt(this.carYearOfPurchaseField.getText());
            int numberOfKilometers = Integer.parseInt(this.carNumberOfKilometersField.getText());
            String inGuarantee = this.carInGuaranteeField.getText();
            if(!id.equals("")) {
                throw new ValidationException("ERROR: The ID field should mot be completed for adding operation!!!");
            }
            Car car = new Car(model, yearOfPurchase, numberOfKilometers, inGuarantee);
            this.carValidator.validate(car);
            this.carRepository.addEntity(car);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("SUCCESS: Car added successfully!!");
            alert.show();
            this.showAllCars();
        }
        catch (ValidationException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.show();
        }
    }

    /**
     * Handle function for update car button
     */
    public void handleUpdateCar() {
        try {
            int id = Integer.parseInt(this.carIdField.getText());
            String model = this.carModelField.getText();
            int yearOfPurchase = Integer.parseInt(this.carYearOfPurchaseField.getText());
            int numberOfKilometers = Integer.parseInt(this.carNumberOfKilometersField.getText());
            String inGuarantee = this.carInGuaranteeField.getText();
            Car newCar = new Car(model, yearOfPurchase, numberOfKilometers, inGuarantee);
            this.carValidator.validate(newCar);
            this.carRepository.updateEntity(id, newCar);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("SUCCESS: Car updated successfully!!");
            alert.show();
            this.showAllCars();
        }
        catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.show();
        }
    }

    /**
     * Handle function for delete car button
     */
    public void handleRemoveCar() {
        try {
            int id = Integer.parseInt(this.carIdField.getText());
            String model = this.carModelField.getText();
            String yearOfPurchase = this.carYearOfPurchaseField.getText();
            String numberOfKilometers = this.carNumberOfKilometersField.getText();
            String inGuarantee = this.carInGuaranteeField.getText();
            if(!model.equals("") || !yearOfPurchase.equals("") || !numberOfKilometers.equals("") || !inGuarantee.equals("")) {
                throw new ValidationException("ERROR: All fields excepts 'ID' should be null!!");
            }
            this.carRepository.removeEntity(id);
            this.removeContractsByIdCar(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("SUCCESS: Car deleted successfully!!");
            alert.show();
            this.showAllCars();
            this.showAllContracts();
        }
        catch (ValidationException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.show();
        }
    }

    /**
     * Handle function for add client card button
     */
    public void handleAddClientCard() {
        try {
            String id = this.clientCardIdField.getText();
            String firstName = this.clientCardFirstNameField.getText();
            String lastName = this.clientCardLastNameField.getText();
            String cnp = this.clientCardCnpField.getText();
            if(!id.equals("")) {
                throw new ValidationException("ERROR: The ID field should mot be completed for adding operation!!!");
            }
            ClientCard clientCard = new ClientCard(firstName, lastName, cnp);
            this.validateClientCard(clientCard);
            this.clientCardRepository.addEntity(clientCard);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("SUCCESS: Client Card added successfully!!");
            alert.show();
            this.showAllCreditCards();
        }
        catch (ValidationException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.show();
        }
    }

    /**
     * Handle function for update client card button
     */
    public void handleUpdateClientCard() {
        try {
            int id = Integer.parseInt(this.clientCardIdField.getText());
            String firstName = this.clientCardFirstNameField.getText();
            String lastName = this.clientCardLastNameField.getText();
            String cnp = this.clientCardCnpField.getText();
            ClientCard newClientCard = new ClientCard(firstName, lastName, cnp);
            newClientCard.setId(id);
            this.validateClientCard(newClientCard);
            this.clientCardRepository.updateEntity(id, newClientCard);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("SUCCESS: Client Card updated successfully!!");
            alert.show();
            this.showAllCreditCards();
        }
        catch (ValidationException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.show();
        }
    }

    /**
     * Handle function for delete client card button
     */
    public void handleRemoveClientCard() {
        try {
            int id = Integer.parseInt(this.clientCardIdField.getText());
            String firstName = this.clientCardFirstNameField.getText();
            String lastName = this.clientCardLastNameField.getText();
            String cnp = this.clientCardCnpField.getText();
            if(!firstName.equals("") || !lastName.equals("") || !cnp.equals("")) {
                throw new ValidationException("ERROR: All fields excepts 'ID' should be null!!");
            }
            this.clientCardRepository.removeEntity(id);
            this.removeContractByIdClientCard(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("SUCCESS: Client card deleted successfully!!");
            alert.show();
            this.showAllCreditCards();
            this.showAllContracts();
        }
        catch (ValidationException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.show();
        }
    }

    /**
     * Handle function for add contract button
     */
    public void handleAddContract() {
        try {
            String id = this.contractIdField.getText();
            int idCar = Integer.parseInt(this.contractIdCarField.getText());
            int idClientCard = Integer.parseInt(this.contractIdClientCardField.getText());
            int sumOfParts = Integer.parseInt(this.contractSumOfPartsField.getText());
            int sumOfWork = Integer.parseInt(this.contractSumOfWorkField.getText());
            if(!id.equals("")) {
                throw new ValidationException("ERROR: The ID field should mot be completed for adding operation!!!");
            }
            Contract contract = new Contract(idCar, idClientCard, sumOfParts, sumOfWork);
            this.validateContract(contract);
            this.contractApplyDiscount(contract);
            this.contractRepository.addEntity(contract);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("SUCCESS: Contract added successfully!!");
            alert.show();
            this.showAllContracts();
        }
        catch (ValidationException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.show();
        }
    }

    /**
     * Handle function for update contract button
     */
    public void handleUpdateContract () {
        try {
            int id = Integer.parseInt(this.contractIdField.getText());
            int idCar = Integer.parseInt(this.contractIdCarField.getText());
            int idClientCard = Integer.parseInt(this.contractIdClientCardField.getText());
            int sumOfParts = Integer.parseInt(this.contractSumOfPartsField.getText());
            int sumOfWork = Integer.parseInt(this.contractSumOfWorkField.getText());
            Contract newContract = new Contract(idCar, idClientCard, sumOfParts, sumOfWork);
            this.validateContract(newContract);
            this.contractApplyDiscount(newContract);
            this.contractRepository.updateEntity(id, newContract);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("SUCCESS: Contract updated successfully!!");
            alert.show();
            this.showAllContracts();
        }
        catch (ValidationException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.show();
        }
    }

    /**
     * Handle function for delete function button
     */
    public void handleRemoveContract() {
        try {
            int id = Integer.parseInt(this.contractIdField.getText());
            String idCar = this.contractIdCarField.getText();
            String idClientCard = this.contractIdClientCardField.getText();
            String sumOfParts = this.contractSumOfPartsField.getText();
            String sumOfWork = this.contractSumOfWorkField.getText();
            if(!idCar.equals("") || !idClientCard.equals("") || !sumOfParts.equals("") || !sumOfWork.equals("")) {
                throw new ValidationException("ERROR: All fields excepts 'ID' should be null!!");
            }
            this.contractRepository.removeEntity(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("SUCCESS: Contract deleted successfully!!");
            alert.show();
            this.showAllContracts();
        }
        catch (ValidationException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.show();
        }
    }

    /**
     * Handle function for car table
     */
    public void handleCarTable() {
        Car car = this.carTable.getSelectionModel().getSelectedItem();
        this.carIdField.setText(String.valueOf(car.getId()));
        this.carModelField.setText(car.getModel());
        this.carYearOfPurchaseField.setText(String.valueOf(car.getYearOfPurchase()));
        this.carNumberOfKilometersField.setText(String.valueOf(car.getNumberOfKilometers()));
        this.carInGuaranteeField.setText(car.getInGuarantee());
    }

    /**
     * Handle function for client card table
     */
    public void handleClientCardTable() {
        ClientCard clientCard = this.clientCardTable.getSelectionModel().getSelectedItem();
        this.clientCardIdField.setText(String.valueOf(clientCard.getId()));
        this.clientCardFirstNameField.setText(clientCard.getFirstName());
        this.clientCardLastNameField.setText(clientCard.getLastName());
        this.clientCardCnpField.setText(clientCard.getCnp());
    }

    /**
     * Handle function for contract table
     */
    public void handleContractTable() {
        Contract contract = this.contractTable.getSelectionModel().getSelectedItem();
        this.contractIdField.setText(String.valueOf(contract.getId()));
        this.contractIdCarField.setText(String.valueOf(contract.getIdCar()));
        this.contractIdClientCardField.setText(String.valueOf(contract.getIdClientCard()));
        this.contractSumOfPartsField.setText(String.valueOf(contract.getSumOfParts()));
        this.contractSumOfWorkField.setText(String.valueOf(contract.getSumOfWork()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String url = "jdbc:postgresql://localhost:5432/AutoService";
        String username = "postgres";
        String password = "andrey100f";

        this.carRepository = new CarRepository(url, username, password);
        this.clientCardRepository = new ClientCardRepository(url, username, password);
        this.contractRepository = new ContractRepository(url, username, password);

        this.showAllCars();
        this.showAllCreditCards();
        this.showAllContracts();
    }
}