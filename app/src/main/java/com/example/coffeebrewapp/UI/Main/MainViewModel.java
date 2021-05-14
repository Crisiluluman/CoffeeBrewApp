package com.example.coffeebrewapp.UI.Main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.coffeebrewapp.Repository.RemoteDataSource.User.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class MainViewModel extends AndroidViewModel {

    private final UserRepository userRepository;


    public MainViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
    }

    public void signOut()
    {
        userRepository.signOut();
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentUser();
    }
}
