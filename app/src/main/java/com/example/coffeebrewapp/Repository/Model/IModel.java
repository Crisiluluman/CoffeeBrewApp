package com.example.coffeebrewapp.Repository.Model;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;

public interface IModel {

    //Returns the name of a coffee product that is selected from SearchCoffeeViewModel
    String getCoffeeFromSearch();

    //Sets the name of a coffee product in SearchSelectedViewModel
    void setCoffeeFromSearch(String name);
}
