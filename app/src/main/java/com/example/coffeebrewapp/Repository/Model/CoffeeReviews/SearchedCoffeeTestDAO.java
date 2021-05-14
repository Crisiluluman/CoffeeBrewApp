package com.example.coffeebrewapp.Repository.Model.CoffeeReviews;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;

import java.util.ArrayList;

//TODO: Delete test class

public class SearchedCoffeeTestDAO {

    private static ArrayList<CoffeeProduct> products = new ArrayList<>();

    public CoffeeProduct getTestCoffee(int id) {
        CoffeeProduct product = products.get(id);
        products.remove(id);
        return product;
    }

    public void saveTestProduct(CoffeeProduct product) {
        products.add(product);
    }
}
