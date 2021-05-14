package com.example.coffeebrewapp.UI.SearchCoffee;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.example.coffeebrewapp.Repository.Model.IModel;
import com.example.coffeebrewapp.Repository.Model.Model;
import com.example.coffeebrewapp.Repository.RemoteDataSource.CoffeeProduct.CoffeeProductDAO;
import com.example.coffeebrewapp.Repository.RemoteDataSource.User.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class SearchCoffeeViewModel extends AndroidViewModel {


    private final IModel model = new Model();
    private final UserRepository userRepository;
    private final CoffeeProductDAO coffeeProductDAO;


    public SearchCoffeeViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        coffeeProductDAO = CoffeeProductDAO.getInstance();
    }



    public LiveData<List<CoffeeProduct>> getAllCoffeeProducts() {
       return coffeeProductDAO.getAllCoffeeProducts();
    }



    //User initialization stuff
    public LiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    public void init() {
        String userId = getCurrentUser().getValue().getUid();
        coffeeProductDAO.init(userId);
    }


    //Testing stuff
    public void saveTestProduct(CoffeeProduct product){
        model.saveTestProduct(product);
    }



}
