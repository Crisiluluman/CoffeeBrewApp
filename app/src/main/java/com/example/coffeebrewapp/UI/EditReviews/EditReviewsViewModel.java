package com.example.coffeebrewapp.UI.EditReviews;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.example.coffeebrewapp.Repository.Model.IModel;
import com.example.coffeebrewapp.Repository.Model.Model;
import com.example.coffeebrewapp.Repository.RemoteDataSource.CoffeeProduct.CoffeeProductDAO;
import com.example.coffeebrewapp.Repository.RemoteDataSource.User.UserRepository;
import com.google.firebase.auth.FirebaseUser;

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

    public void init() {
        coffeeProductDAO.init();
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentUser();
    }


    public void uploadToFirebase(Uri tempUri, String uriExtension, String sCoffeeName, String oldCoffeeName)
    {
        coffeeProductDAO.uploadImageToFirebase(tempUri, uriExtension, sCoffeeName, oldCoffeeName);
    }

    public void uploadObjectToFirebase(String sCoffeeName, float rating, String brew, String description, String oldCoffeeName)
    {
        String userID = getCurrentUser().getValue().getDisplayName();
        coffeeProductDAO.uploadObjectToFirebase(userID,sCoffeeName,rating,brew,description,oldCoffeeName);

    }





    public void deleteCoffeProduct(String oldCoffeeName) {
        coffeeProductDAO.deleteCoffeProduct(oldCoffeeName);
    }
}
