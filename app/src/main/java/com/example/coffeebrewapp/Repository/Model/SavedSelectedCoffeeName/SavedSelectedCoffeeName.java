package com.example.coffeebrewapp.Repository.Model.SavedSelectedCoffeeName;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;

import java.util.ArrayList;


public class SavedSelectedCoffeeName {

    private static ArrayList<CoffeeProduct> products = new ArrayList<>();
    private static String coffeeName;


    public String getCoffeeFromSearch() {
        return coffeeName;
    }


    public void setCoffeeFromSearch(String name) {
        coffeeName = name;
    }
}
