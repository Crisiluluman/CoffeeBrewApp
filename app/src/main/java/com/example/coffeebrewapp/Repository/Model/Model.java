package com.example.coffeebrewapp.Repository.Model;

import com.example.coffeebrewapp.Repository.Model.SavedSelectedCoffeeName.SavedSelectedCoffeeName;

public class Model implements IModel{

    private static SavedSelectedCoffeeName savedSelectedCoffeeName = new SavedSelectedCoffeeName();



    // Getting and Setting coffeeName from SearchCoffeeFragment
    @Override
    public String getCoffeeFromSearch() {
        return savedSelectedCoffeeName.getCoffeeFromSearch();
    }

    @Override
    public void setCoffeeFromSearch(String name) {
        savedSelectedCoffeeName.setCoffeeFromSearch(name);
    }
}
