package com.example.coffeebrewapp.UI.UserReviews;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.example.coffeebrewapp.Repository.Model.IModel;
import com.example.coffeebrewapp.Repository.Model.Model;
import com.example.coffeebrewapp.Repository.RemoteDataSource.CoffeeProduct.CoffeeProductDAO;
import com.example.coffeebrewapp.Repository.RemoteDataSource.User.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UserReviewsViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final CoffeeProductDAO dao;
    private IModel model;

    public UserReviewsViewModel(@NonNull Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        dao = CoffeeProductDAO.getInstance();
        model = new Model();
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    public void init(){
        userRepository.init();
    }

    public LiveData<List<CoffeeProduct>> getUserReviews()throws NullPointerException
    {
        return dao.getUserReviews(getCurrentUser().getValue().getDisplayName());
    }


    public void setCoffeeFromSearch(String name) {
        model.setCoffeeFromSearch(name);
    }
}
