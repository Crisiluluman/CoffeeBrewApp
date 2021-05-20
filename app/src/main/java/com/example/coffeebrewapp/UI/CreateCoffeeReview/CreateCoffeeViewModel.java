package com.example.coffeebrewapp.UI.CreateCoffeeReview;

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

public class CreateCoffeeViewModel  extends AndroidViewModel {

    private IModel model = new Model();
    private final UserRepository userRepository;
    private final CoffeeProductDAO coffeeProductDAO;


    public CreateCoffeeViewModel(@NonNull Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        coffeeProductDAO = CoffeeProductDAO.getInstance();

    }

    public void saveTestProduct(CoffeeProduct product){
       //model.se(product);
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    public void uploadToFirebase(Uri tempUri, String uriExtension,String sCoffeeName)
    {
        coffeeProductDAO.uploadImageToFirebase(tempUri, uriExtension, sCoffeeName);
    }

    public void uploadObjectToFirebase(String sCoffeeName, float rating, String brew, String description)
    {
        String userID = getCurrentUser().getValue().getDisplayName();
        coffeeProductDAO.uploadObjectToFirebase(userID,sCoffeeName,rating,brew,description);

    }


    public void init() {
        String userId = getCurrentUser().getValue().getUid();
        coffeeProductDAO.init(userId);
    }
}
