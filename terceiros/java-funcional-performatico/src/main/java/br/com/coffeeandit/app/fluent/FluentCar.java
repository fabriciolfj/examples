package br.com.coffeeandit.app.fluent;

import br.com.coffeeandit.app.model.Car;
import br.com.coffeeandit.app.enums.CarTypeEnum;

import javax.money.MonetaryAmount;
import java.time.Year;

public interface FluentCar {

    CarFabricationName model(String name);

    interface CarFabricationName {
        CarFabricationYear name(String name);
    }

    interface CarFabricationYear {
        CarType fabricationYear(Year start);
    }

    interface CarType {
        CarRentValue type(CarTypeEnum carTypeEnum);
    }

    interface CarRentValue {
        Car rentValue(MonetaryAmount salary);
    }

}

