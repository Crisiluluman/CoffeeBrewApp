package com.example.coffeebrewapp.Repository.Model;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.example.coffeebrewapp.Repository.Model.CoffeeReviews.SearchedCoffeeTestDAO;

public class Model implements IModel{

    private static SearchedCoffeeTestDAO searchedCoffeeTestDAO = new SearchedCoffeeTestDAO();



    // Testing stuff
    @Override
    public void saveTestProduct(CoffeeProduct product) {
        searchedCoffeeTestDAO.saveTestProduct(product);
    }

    @Override
    public CoffeeProduct getTestCoffee(int id) {
        return searchedCoffeeTestDAO.getTestCoffee(id);
    }
}
