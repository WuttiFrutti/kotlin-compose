package models;

import javax.inject.Inject;


public class Car {

    private Brand brand;

    @Inject
    public Car(Brand brand) {
        this.brand = brand;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }


}