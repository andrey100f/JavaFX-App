package com.example.autoserviceapp.domain.validators;

import com.example.autoserviceapp.domain.Car;

/**
 * CarValidator class
 */
public class CarValidator implements Validator<Car> {
    @Override
    public void validate(Car entity) {
        String listOfErrors = "";
        if (entity.getNumberOfKilometers() <= 0) {
            listOfErrors += "ERROR: The number of kilometers should be greater than 0!!!\n";
        }
        if (entity.getYearOfPurchase() <= 0) {
            listOfErrors += "ERROR: The year of purchase should be greater than 0!!!\n";
        }
        if (!listOfErrors.equals("")) {
            throw new ValidationException(listOfErrors);
        }
    }
}
