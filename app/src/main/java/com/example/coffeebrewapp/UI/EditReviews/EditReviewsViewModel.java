package com.example.coffeebrewapp.UI.EditReviews;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.example.coffeebrewapp.Repository.Model.IModel;
import com.example.coffeebrewapp.Repository.Model.Model;
import com.example.coffeebrewapp.Repository.RemoteDataSource.CoffeeProduct.CoffeeProductDAO;
import com.example.coffeebrewapp.Repository.RemoteDataSource.User.UserRepository;

public class EditReviewsViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final CoffeeProductDAO coffeeProductDAO;
    private final IModel model;

    public EditReviewsViewModel(@NonNull Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        coffeeProductDAO = CoffeeProductDAO.getInstance();
        model = new Model();
    }



    public String getCoffeeFromSearch() {
        return model.getCoffeeFromSearch();
    }

    public LiveData<CoffeeProduct> getProductFromName(){
        return coffeeProductDAO.getProductFromName(getCoffeeFromSearch());
    }

    /*
    public CoffeeProduct getProductFromName(String productName)
    {
        for (int i = 0; i < coffeeProductDAO.getProductFromName(productName).getValue().size(); i++) {
            if (coffeeProductDAO.getProductFromName(productName).getValue().get(i).getCoffeeName().equals(productName))
            {
                System.out.println(coffeeProductDAO.getProductFromName(productName).getValue().get(i).getCoffeeName());
                return coffeeProductDAO.getProductFromName(productName).getValue().get(i);
            }
        }
        return null;
    }

     */
}
