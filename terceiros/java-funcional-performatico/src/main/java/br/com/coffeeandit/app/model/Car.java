package br.com.coffeeandit.app.model;

import br.com.coffeeandit.app.enums.CarTypeEnum;
import br.com.coffeeandit.app.fluent.FluentCar;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.Year;
import java.util.Objects;

public class Car {

    @NotBlank
    private String model;

    @NotBlank
    private String name;

    @NotNull
    @PastOrPresent
    private Year fabricationYear;

    @NotNull
    private CarTypeEnum carTypeEnum;

    @NotNull
    @JsonIgnore
    private MonetaryAmount monetaryRentValue;

    @PositiveOrZero
    private long km = 0;

    private String rentValue;

    Car(String model, String name, Year fabricationYear, CarTypeEnum carTypeEnum, MonetaryAmount monetaryRentValue) {
        this.model = model;
        this.name = name;
        this.fabricationYear = fabricationYear;
        this.carTypeEnum = carTypeEnum;
        this.monetaryRentValue = monetaryRentValue;
    }

    @Deprecated
    Car() {
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public Year getFabricationYear() {
        return fabricationYear;
    }

    public CarTypeEnum getCarTypeEnum() {
        return carTypeEnum;
    }

    public MonetaryAmount getMonetaryRentValue() {
        return monetaryRentValue;
    }

    public long getKm() {
        return km;
    }

    public void goal() {
        km++;
    }

    public CarBuilder toBuilder() {
        return new CarBuilder(this);
    }

    public static CarBuilder builder() {
        return new CarBuilder();
    }

    public String getRentValue() {
        return monetaryRentValue.toString();
    }

    public static FluentCar.CarFabricationName model(String model) {
        FluentCar dsl = new CarDSL();
        return dsl.model(model);
    }

    public static class CarBuilder {

        private String model;

        private String name;

        private Year fabricationYear;

        private CarTypeEnum carTypeEnum;

        private MonetaryAmount rentValue;

        private CarBuilder() {
        }

        private CarBuilder(Car car) {
            this.model = car.model;
            this.name = car.name;
            this.fabricationYear = car.fabricationYear;
            this.carTypeEnum = car.carTypeEnum;
            this.rentValue = car.monetaryRentValue;
        }

        public CarBuilder withModel(String model) {
            this.model = Objects.requireNonNull(model, "model is required");
            return this;
        }

        public CarBuilder withName(String name) {
            this.name = Objects.requireNonNull(name, "name is required");
            return this;
        }

        public CarBuilder withFabricationYear(Year fabricationYear) {
            Objects.requireNonNull(fabricationYear, "fabricationYear is required");
            if (Year.now().isBefore(fabricationYear)) {
                throw new IllegalArgumentException("you cannot have fabricationYear in the future");
            }
            this.fabricationYear = fabricationYear;
            return this;
        }

        public CarBuilder withCarType(CarTypeEnum carTypeEnum) {
            this.carTypeEnum = Objects.requireNonNull(carTypeEnum, "cartype is required");
            return this;
        }

        public CarBuilder withRentValue(MonetaryAmount rentValue) {
            Objects.requireNonNull(rentValue, "rentValue is required");
            if (rentValue.isNegativeOrZero()) {
                throw new IllegalArgumentException("A car needs to have a positive value for rent.");
            }
            this.rentValue = rentValue;
            return this;
        }

        public Car build() {
            Objects.requireNonNull(model, "model is required");
            Objects.requireNonNull(name, "name is required");
            Objects.requireNonNull(fabricationYear, "start is required");
            Objects.requireNonNull(carTypeEnum, "carType is required");
            Objects.requireNonNull(rentValue, "rentValue is required");
            return new Car(model, name, fabricationYear, carTypeEnum, rentValue);
        }
    }
}
