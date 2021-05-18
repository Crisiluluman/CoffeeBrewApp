package com.example.coffeebrewapp.UI.SearchSelectedCoffee;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.example.coffeebrewapp.Repository.Model.IModel;
import com.example.coffeebrewapp.Repository.Model.Model;
import com.example.coffeebrewapp.Repository.RemoteDataSource.CoffeeProduct.CoffeeProductDAO;
import com.example.coffeebrewapp.Repository.RemoteDataSource.User.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class SearchSelectedCoffeeViewModel  extends AndroidViewModel {


    private final UserRepository userRepository;
    private final CoffeeProductDAO coffeeProductDAO;
    private final IModel model;

    public SearchSelectedCoffeeViewModel(@NonNull Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        coffeeProductDAO = CoffeeProductDAO.getInstance();
        model = new Model();
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentUser();
    }

/*
    public LiveData<CoffeeProduct> getSpecificProduct(String coffeeName)

    {
        return coffeeProductDAO.getSpecificProduct(coffeeName);
    }
*/

    public CoffeeProduct getProductFromName(String productName)
    {
        for (int i = 0; i < coffeeProductDAO.getProductFromName(productName).getValue().size(); i++) {
            if (coffeeProductDAO.getProductFromName(productName).getValue().get(i).getCoffeeName().equals(productName))
            {
                return coffeeProductDAO.getProductFromName(productName).getValue().get(i);
            }
        }
        System.out.println("TEST THE NULLPOINTER");
        return null;
    }

    public String getCoffeeFromSearch()
    {
        return model.getCoffeeFromSearch();
    }

}
