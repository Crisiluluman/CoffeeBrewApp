package com.example.coffeebrewapp.UI.SearchSelectedCoffee;

import androidx.lifecycle.ViewModel;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.example.coffeebrewapp.Repository.Model.Model;

public class SearchSelectedCoffeeViewModel  extends ViewModel{


    private final Model model = new Model();



    public CoffeeProduct getTestCoffee(int id) {
        return model.getTestCoffee(id);
    }
}
