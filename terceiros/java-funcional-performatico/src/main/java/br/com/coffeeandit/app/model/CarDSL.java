package br.com.coffeeandit.app.model;

import br.com.coffeeandit.app.enums.CarTypeEnum;
import br.com.coffeeandit.app.fluent.FluentCar;

import javax.money.MonetaryAmount;
import java.time.Year;
import java.util.Objects;

public class CarDSL implements
        FluentCar.CarFabricationName,
        FluentCar.CarFabricationYear,
        FluentCar.CarRentValue,
        FluentCar.CarType,
        FluentCar {

    public CarDSL() {}

    private String model;

    private String name;

    private Year fabricationYear;

    private CarTypeEnum carTypeEnum;

    private MonetaryAmount rentValue;

    @Override
    public CarFabricationName model(String model) {
        this.model = Objects.requireNonNull(model, "model is required");
        return this;
    }

    @Override
    public CarFabricationYear name(String name) {
        this.name = Objects.requireNonNull(name, "name is required");
        return this;
    }

    @Override
    public CarType fabricationYear(Year fabricationYear) {
        Objects.requireNonNull(fabricationYear, "fabricationYear is required");
        if (Year.now().isBefore(fabricationYear)) {
            throw new IllegalArgumentException("you cannot have fabricationYear in the future");
        }
        this.fabricationYear = fabricationYear;
        return this;
    }

    @Override
    public CarRentValue type(CarTypeEnum carTypeEnum) {
        this.carTypeEnum = Objects.requireNonNull(carTypeEnum, "carTypes is required");
        return this;
    }

    @Override
    public Car rentValue(MonetaryAmount rentValue) {
        Objects.requireNonNull(rentValue, "rentValue is required");
        if (rentValue.isNegativeOrZero()) {
            throw new IllegalArgumentException("A car needs to have a positive value for rent.");
        }
        this.rentValue = rentValue;
        return new Car(model, name, fabricationYear, carTypeEnum, rentValue);
    }

}
