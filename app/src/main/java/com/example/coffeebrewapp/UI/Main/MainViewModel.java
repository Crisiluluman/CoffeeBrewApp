package com.example.coffeebrewapp.UI.Main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.coffeebrewapp.Data.ProfileData.ProfileData;
import com.example.coffeebrewapp.Repository.RemoteDataSource.User.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final UserRepository userRepository;


    public MainViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        //CoffeDAO
    }

    public void signOut()
    {
        userRepository.signOut();
    }

    public void init(){
        userRepository.init();
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    public MutableLiveData<ProfileData> getCurrentProfileData() throws NullPointerException{
        return userRepository.getCurrentUserProfileData();

    }


}
